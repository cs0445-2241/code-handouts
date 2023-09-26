import java.util.Arrays;
/**
   A class that implements the ADT list by using a resizable array.
   Entries in a list have positions that begin with 1.
   Duplicate entries are allowed.
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class AList<T> implements ListInterface<T>
{
	private T[] list;   // Array of list entries; ignore list[0]
	private int numberOfEntries;
   private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;

	public AList()
	{
		this(DEFAULT_CAPACITY);
	} // end default constructor

	public AList(int initialCapacity)
	{
      // Is initialCapacity too small?
      if (initialCapacity < DEFAULT_CAPACITY)
         initialCapacity = DEFAULT_CAPACITY;
      else // Is initialCapacity too big?
         checkCapacity(initialCapacity);
      
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] tempList = (T[])new Object[initialCapacity + 1];
      list = tempList;
      numberOfEntries = 0;
      initialized = true;
	} // end constructor

	public void add(T newEntry)
	{
      add(numberOfEntries + 1, newEntry);
/*
		checkInitialization();
      list[numberOfEntries + 1] = newEntry;
      numberOfEntries++;
      ensureCapacity(); 
*/
	} // end add
   
   // Precondition: The array list has room for another entry.
	public void add(int newPosition, T newEntry)
	{
		checkInitialization();
      if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1))
      {
         if (newPosition <= numberOfEntries)
            makeRoom(newPosition);
         list[newPosition] = newEntry;
         numberOfEntries++;
         ensureCapacity(); // Ensure enough room for next add
      }
      else
         throw new IndexOutOfBoundsException(
                   "Given position of add's new entry is out of bounds.");
	} // end add

	public T remove(int givenPosition)
	{
		checkInitialization();
      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
         assert !isEmpty();
         T result = list[givenPosition]; // Get entry to be removed

         // Move subsequent entries towards entry to be removed,
         // unless it is last in list
         if (givenPosition < numberOfEntries)
            removeGap(givenPosition);
         
         numberOfEntries--;
         return result;
      }
      else
         throw new IndexOutOfBoundsException(
                   "Illegal position given to remove operation.");
 	} // end remove

	public void clear()
	{
		checkInitialization();

      // Clear entries but retain array; no need to create a new array
		for (int index = 1; index <= numberOfEntries; index++) // Loop is part of Q4
			list[index] = null;

		numberOfEntries = 0;
	} // end clear

	public T replace(int givenPosition, T newEntry)
	{
		checkInitialization();
      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
      	assert !isEmpty();
         T originalEntry = list[givenPosition];
         list[givenPosition] = newEntry;
         return originalEntry;
      }
      else
         throw new IndexOutOfBoundsException(
                   "Illegal position given to replace operation.");
   } // end replace

	public T getEntry(int givenPosition)
	{
		checkInitialization();
      if ((givenPosition >= 1) && (givenPosition <= numberOfEntries))
      {
			assert !isEmpty();
         return list[givenPosition];
      }
      else
         throw new IndexOutOfBoundsException(
                   "Illegal position given to getEntry operation.");
	} // end getEntry

   public T[] toArray()
   {
		checkInitialization();
      
      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] result = (T[])new Object[numberOfEntries]; // Unchecked cast
      for (int index = 0; index < numberOfEntries; index++)
      {
         result[index] = list[index + 1];
      } // end for
      
      return result;
   } // end toArray
   
	public boolean contains(T anEntry)
	{
		checkInitialization();
		boolean found = false;
      int index = 1;
		while (!found && (index <= numberOfEntries))
		{
			if (anEntry.equals(list[index]))
				found = true;
         index++;
      } // end while

		return found;
	} // end contains

	public int getLength()
	{
		return numberOfEntries;
	} // end getLength

	public boolean isEmpty()
	{
		return numberOfEntries == 0;
	} // end isEmpty

   // Doubles the capacity of the array list if it is full.
   // Precondition: checkInitialization has been called.
   private void ensureCapacity()
   {
      int capacity = list.length - 1;
      if (numberOfEntries >= capacity)
      {
         int newCapacity = 2 * capacity;
         checkCapacity(newCapacity); // Is capacity too big?
         list = Arrays.copyOf(list, newCapacity + 1);
      } // end if
   } // end ensureCapacity

   // Makes room for a new entry at newPosition.
   // Precondition: 1 <= newPosition <= numberOfEntries + 1;
	//               numberOfEntries is list's length before addition;
   //               checkInitialization has been called.
	private void makeRoom(int newPosition)
	{
	   assert (newPosition >= 1) && (newPosition <= numberOfEntries + 1);
	  
	   int newIndex = newPosition;
	   int lastIndex = numberOfEntries;

      // Move each entry to next higher index, starting at end of
      // list and continuing until the entry at newIndex is moved
      for (int index = lastIndex; index >= newIndex; index--)
	      list[index + 1] = list[index];
	}  // end makeRoom

	// Shifts entries that are beyond the entry to be removed to the 
	// next lower position.
	// Precondition: 1 <= givenPosition < numberOfEntries;
	//               numberOfEntries is list's length before removal;
   //               checkInitialization has been called.
	private void removeGap(int givenPosition)
	{
      assert (givenPosition >= 1) && (givenPosition < numberOfEntries);

      int removedIndex = givenPosition;
      int lastIndex = numberOfEntries;

      for (int index = removedIndex; index < lastIndex; index++)
         list[index] = list[index + 1];
	} // end removeGap

   // Throws an exception if this object is not initialized.
   private void checkInitialization()
   {
      if (!initialized)
         throw new SecurityException ("AList object is not initialized properly.");
   } // end checkInitialization
   
   // Throws an exception if the client requests a capacity that is too large.
   private void checkCapacity(int capacity)
   {
      if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a list " +
                                         "whose capacity exceeds " +
                                         "allowed maximum.");
   } // end checkCapacity
} // end AList

