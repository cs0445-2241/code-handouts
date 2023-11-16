/**
   A class that implements the ADT dictionary by using hashing and
   linear probing to resolve collisions.
   The dictionary is unsorted and has distinct search keys.
   Notes: Uses probe for add, remove, and getValue, as described in the
          answer to Self-Test Question 4 in Chapter 19.
          Uses linear probing, but includes code for quadratic probing.
          Has a display method for illustration and testing.
  
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class HashedDictionary<K, V> implements DictionaryInterface<K, V>
{
   // The dictionary:
	private int numberOfEntries;
	private static final int DEFAULT_CAPACITY = 5;     // Must be prime
	private static final int MAX_CAPACITY = 10000;
   
   // The hash table:
	private TableEntry<K, V>[] hashTable;
   private int tableSize;                             // Must be prime
   private static final int MAX_SIZE = 2 * MAX_CAPACITY;
   private boolean initialized = false;
	private static final double MAX_LOAD_FACTOR = 0.5; // Fraction of hash table that can be filled
	
	public HashedDictionary()
	{
		this(DEFAULT_CAPACITY); // Call next constructor
	} // end default constructor
   
	public HashedDictionary(int initialCapacity)
	{
      checkCapacity(initialCapacity);      
		numberOfEntries = 0;    // Dictionary is empty
      
      // Set up hash table:
		// Initial size of hash table is same as initialCapacity if it is prime;
		// otherwise increase it until it is prime size
		int tableSize = getNextPrime(initialCapacity);
      checkSize(tableSize);
		
		// The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      TableEntry<K, V>[] temp = (TableEntry<K, V>[])new TableEntry[tableSize];
      hashTable = temp;

      initialized = true;
	} // end constructor
	
// -------------------------
// We've added this method to display the hash table for illustration and testing
// -------------------------
 	public void displayHashTable()
 	{
      checkInitialization();
		for (int index = 0; index < hashTable.length; index++)
		{
    	if (hashTable[index] == null)
    		System.out.println("null ");
     	else if (hashTable[index].isRemoved())
    		System.out.println("removed state");
     	else
    		System.out.println(hashTable[index].getKey() + " " + hashTable[index].getValue());
		} // end for
      System.out.println();
   } // end displayHashTable
// -------------------------
	
	public V add(K key, V value)
	{
      checkInitialization();
      if ((key == null) || (value == null))
         throw new IllegalArgumentException("Cannot add null to a dictionary.");
      else
      {
         V oldValue;                // Value to return

         int index = getHashIndex(key);
         index = probe(index, key); // Check for and resolve collision

         // Assertion: index is within legal range for hashTable
         assert (index >= 0) && (index < hashTable.length);

         if ( (hashTable[index] == null) || hashTable[index].isRemoved())
         { // Key not found, so insert new entry
            hashTable[index] = new TableEntry<>(key, value);
            numberOfEntries++;
            oldValue = null;
         }
         else
         { // Key found; get old value for return and then replace it
            oldValue = hashTable[index].getValue();
            hashTable[index].setValue(value);
         } // end if
         
         // Ensure that hash table is large enough for another add
         if (isHashTableTooFull())
            enlargeHashTable();
         
         return oldValue;
      } // end if
   } // end add

	public V remove(K key)
	{
      checkInitialization();
      V removedValue = null;
   	
		int index = getHashIndex(key);
		index = probe(index, key);

		if ((hashTable[index] != null) && hashTable[index].isIn())
		{
			// Key found; flag entry as removed and return its value
			removedValue = hashTable[index].getValue();
			hashTable[index].setToRemoved();
			numberOfEntries--;
		} // end if
		// Else not found; result is null
		
		return removedValue;
   } // end remove

   public V getValue(K key)
   {
      checkInitialization();
      V result = null;
      
      int index = getHashIndex(key);
      index = probe(index, key);

      if ((hashTable[index] != null) && hashTable[index].isIn())
         result = hashTable[index].getValue(); // Key found; get value
      // Else not found; result is null
      
      return result;
   } // end getValue

	public boolean contains(K key)
   {
   	return getValue(key) != null; 
   } // end contains

   public boolean isEmpty()
   {
      return numberOfEntries == 0;
   } // end isEmpty

   public int getSize()
   {
      return numberOfEntries;
   } // end getSize

	public final void clear()
	{ 
      checkInitialization();
		for (int index = 0; index < hashTable.length; index++)
			hashTable[index] = null;

      numberOfEntries = 0;
///      locationsUsed = 0;
   } // end clear

	// public Iterator<K> getKeyIterator()
	// { 
	// 	return new KeyIterator();
	// } // end getKeyIterator
	
	// public Iterator<V> getValueIterator()
	// {	
	// 	return new ValueIterator();
	// } // end getValueIterator
   
	private int getHashIndex(K key)
	{
		int hashIndex = key.hashCode() % hashTable.length;
		
		if (hashIndex < 0)
		{
			hashIndex = hashIndex + hashTable.length;
		} // end if

		return hashIndex;
	} // end getHashIndex
	
   // Precondition: checkInitialization has been called.
	private int probe(int index, K key)
	{
      boolean found = false;
      int removedStateIndex = -1; // Index of first location in removed state
//  	int increment = 1;          // For quadratic probing **********
      
      while ( !found && (hashTable[index] != null) )
      {
         if (hashTable[index].isIn())
         {
            if (key.equals(hashTable[index].getKey()))
               found = true; // Key found
            else             // Follow probe sequence
               index = (index + 1) % hashTable.length;         // Linear probing
 //				index = (index + increment) % hashTable.length; // Quadratic probing **********
 //				increment = increment + 2;                      // Odd values for quadratic probing **********
         }
         else // Skip entries that were removed
         {
            // Save index of first location in removed state
            if (removedStateIndex == -1)
               removedStateIndex = index;
            
            index = (index + 1) % hashTable.length;            // Linear probing
 //			index = (index + increment) % hashTable.length;    // Quadratic probing **********
 //			increment = increment + 2;                         // Odd values for quadratic probing **********
         } // end if
      } // end while
      // Assertion: Either key or null is found at hashTable[index]
      
      if (found || (removedStateIndex == -1) )
         return index;                                      // Index of either key or null
      else
         return removedStateIndex;                          // Index of an available location
	} // end probe
	
   // Increases the size of the hash table to a prime >= twice its old size.
   // In doing so, this method must rehash the table entries.
   // Precondition: checkInitialization has been called.
	private void enlargeHashTable()
	{
      TableEntry<K, V>[] oldTable = hashTable;
      int oldSize = hashTable.length;
      int newSize = getNextPrime(oldSize + oldSize);
      checkSize(newSize);
      
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      TableEntry<K, V>[] tempTable = (TableEntry<K, V>[])new TableEntry[newSize]; // Increase size of array
      hashTable = tempTable;
      numberOfEntries = 0; // Reset number of dictionary entries, since
                           // it will be incremented by add during rehash

      // Rehash dictionary entries from old array to the new and bigger array;
      // skip both null locations and removed entries
      for (int index = 0; index < oldSize; index++)
      {
         if ( (oldTable[index] != null) && oldTable[index].isIn() )
            add(oldTable[index].getKey(), oldTable[index].getValue());
      } // end for
	} // end enlargeHashTable

   // Returns true if lambda > MAX_LOAD_FACTOR for hash table;
   // otherwise returns false. 
   private boolean isHashTableTooFull()
   {
      return numberOfEntries > MAX_LOAD_FACTOR * hashTable.length;
   } // end isHashTableTooFull

   // Returns a prime integer that is >= the given integer.
	private int getNextPrime(int integer)
	{
		// if even, add 1 to make odd
   	if (integer % 2 == 0)
      {
        integer++;
		} // end if
		
		// test odd integers
      while (!isPrime(integer))
      {
         integer = integer + 2;
      } // end while

		return integer;
	} // end getNextPrime
	
   // Returns true if the given intege is prime.
	private boolean isPrime(int integer)
	{
		boolean result;
		boolean done = false;
		
		// 1 and even numbers are not prime
		if ( (integer == 1) || (integer % 2 == 0) )
	  {
			result = false; 
		}
		
		// 2 and 3 are prime
		else if ( (integer == 2) || (integer == 3) )
		{
			result = true;
		}
		
		else // integer is odd and >= 5
		{
			assert (integer % 2 != 0) && (integer >= 5);
			
			// a prime is odd and not divisible by every odd integer up to its square root
			result = true; // assume prime
			for (int divisor = 3; !done && (divisor * divisor <= integer); divisor = divisor + 2)
			{
		   	if (integer % divisor == 0)
	      {
					result = false; // divisible; not prime
					done = true;
				} // end if
			} // end for
		} // end if
	   	
		return result;
	} // end isPrime

   // Throws an exception if this object is not initialized.
   private void checkInitialization()
   {
      if (!initialized)
         throw new SecurityException ("HashedDictionary object is not initialized properly.");
   } // end checkInitialization
   
   // Ensures that the client requests a capacity
   // that is not too small or too large.
   private void checkCapacity(int capacity)
   {
      if (capacity < DEFAULT_CAPACITY)
         capacity = DEFAULT_CAPACITY;
      else if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a dictionary " +
                                         "whose capacity is larger than " +
                                         MAX_CAPACITY);
   } // end checkCapacity
   
   // Throws an exception if the hash table becomes too large.
   private void checkSize(int size)
   {
      if (tableSize > MAX_SIZE) 
         throw new IllegalStateException("Dictionary has become too large.");
   } // end checkSize
   
	// // private class KeyIterator implements Iterator<K>
	// // {
   // //    private int currentIndex; // Current position in hash table
   // //    private int numberLeft;   // Number of entries left in iteration

   // //    private KeyIterator() 
   // //    {
   // //       currentIndex = 0;
   // //       numberLeft = numberOfEntries;
   // //    } // end default constructor

   // //    public boolean hasNext() 
   // //    {
   // //       return numberLeft > 0;
   // //    } // end hasNext

   // //    public K next()
   // //    {
   // //       K result = null;

   // //       if (hasNext())
   // //       {
   // //          // Skip table locations that do not contain a current entry
   // //          while ( (hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved() )
   // //          {
   // //             currentIndex++;
   // //          } // end while

   // //          result = hashTable[currentIndex].getKey();
   // //          numberLeft--;
   // //          currentIndex++;
   // //       }
   // //       else
   // //          throw new NoSuchElementException();

   // //       return result;
   // //    } // end next

   // //    public void remove()
   // //    {
   // //       throw new UnsupportedOperationException();
   // //    } // end remove
	// // } // end KeyIterator
	
	// // private class ValueIterator implements Iterator<V>
	// // {
	// // 	private int currentIndex;
	// // 	private int numberLeft; 
		
	// // 	private ValueIterator() 
	// // 	{
	// // 		currentIndex = 0;
	// // 		numberLeft = numberOfEntries;
	// // 	} // end default constructor
		
	// // 	public boolean hasNext() 
	// // 	{
	// // 		return numberLeft > 0; 
	// // 	} // end hasNext
		
	// // 	public V next()
	// // 	{
	// // 		V result = null;
			
	// // 		if (hasNext())
	// // 		{
   // //          // Skip table locations that do not contain a current entry
	// //          while ( (hashTable[currentIndex] == null) || hashTable[currentIndex].isRemoved() )
	// // 			{
	// // 				currentIndex++;
	// // 			} // end while
				
	// // 			result = hashTable[currentIndex].getValue();
	// // 			numberLeft--;
	// // 			currentIndex++;
	// // 		}
	// // 		else
	// // 			throw new NoSuchElementException();
		
	// // 		return result;
	// // 	} // end next
		
	// 	public void remove()
	// 	{
	// 		throw new UnsupportedOperationException();
	// 	} // end remove
	// } // end ValueIterator

	private static class TableEntry<S, T>
	{
		private S key;
		private T value;
		private States state;                  // Flags whether this entry is in the hash table
 		private enum States {CURRENT, REMOVED} // Possible values of state
     
		private TableEntry(S searchKey, T dataValue)
		{
         key = searchKey;
         value = dataValue;
         state = States.CURRENT;
		} // end constructor
		
		private S getKey()
		{
			return key;
		} // end getKey
		
		private T getValue()
		{
			return value;
		} // end getValue
		
		private void setValue(T newValue)
		{
			value = newValue;
		} // end setValue
      
      // Returns true if this entry is currently in the hash table.
		private boolean isIn()
		{
			return state == States.CURRENT;
		} // end isIn
		
      // Returns true if this entry has been removed from the hash table.
		private boolean isRemoved()
		{
			return state == States.REMOVED;
		} // end isRemoved
      
      // Sets the state of this entry to removed.
		private void setToRemoved()
		{
			key = null;
			value = null;
         state = States.REMOVED; // Entry not in use, ie deleted from table
		} // end setToRemoved
		
      // Sets the state of this entry to current.
		private void setToIn()     // Not used
		{
         state = States.CURRENT; // Entry in use
		} // end setToIn
	} // end TableEntry
} // end HashedDictionary