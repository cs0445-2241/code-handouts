import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
   A class that implements the ADT list by using a resizable array and 
   gives it an iterator. 
   
   @author Frank M. Carrano
   @author Timothy M. Henry
   @version 4.0
*/
public class ArrayListWithIterator<T> implements ListWithIteratorInterface<T>
{
	private T[] list;   // Array of list entries; ignore list[0]
	private int numberOfEntries;
   private boolean initialized = false;
	private static final int DEFAULT_CAPACITY = 25;
	private static final int MAX_CAPACITY = 10000;
   
	public ArrayListWithIterator()
	{
		this(DEFAULT_CAPACITY);
	} // end default constructor
   
	public ArrayListWithIterator(int initialCapacity)
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
	} // end add
   
	public void add(int newPosition, T newEntry)
	{
		checkInitialization();
      if ((newPosition >= 1) && (newPosition <= numberOfEntries + 1))
      {
         if (newPosition <= numberOfEntries)
            makeRoom(newPosition);
         list[newPosition] = newEntry;
         numberOfEntries++;
         ensureCapacity();
      }
      else
         throw new IndexOutOfBoundsException("Given position of add's new entry is out of bounds.");
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
         throw new IndexOutOfBoundsException("Illegal position given to remove operation.");
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
         throw new IndexOutOfBoundsException("Illegal position given to replace operation.");
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
         throw new IndexOutOfBoundsException("Illegal position given to getEntry operation.");
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
   
   public Iterator<T> iterator()
   {
      return new IteratorForArrayList();
   } // end iterator
   
   public Iterator<T> getIterator()
   {
      return iterator();
   } // end getIterator
   
   // Doubles the capacity of the array list if it is full.
   // Precondition: checkInitialization has been called.
   private void ensureCapacity()
   {
      int capacity = list.length - 1;
      if (numberOfEntries >= capacity)
      {
         int newCapacity = 2 * capacity;
         checkCapacity(newCapacity);
         list = Arrays.copyOf(list, newCapacity + 1);
      } // end if
   } // end ensureCapacity
   
   // Prepares a space for a new entry at newPosition.
   // Precondition: checkInitialization has been called.
   // Precondition: 1 <= newPosition <= numberOfEntries + 1;
	//               numberOfEntries is list's length before addition. */
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
   // Precondition: checkInitialization has been called.
	// Precondition: 1 <= givenPosition < numberOfEntries;
	//               numberOfEntries is list's length before removal. */
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

   // The array of list entries uses indexes that begin at 1 and
   // correspond to the position of the entry within the list.
   private class IteratorForArrayList implements Iterator<T>
   {
      private int     nextIndex;     // Index of next entry in the iteration
      private boolean wasNextCalled; // Needed by remove
     
      private IteratorForArrayList()
      {
         nextIndex = 1;              // Iteration begins at list's first entry
         wasNextCalled = false;
      } // end default constructor
     
      public boolean hasNext()
      {
         return nextIndex <= numberOfEntries;
      } // end hasNext
      
      public T next()
      {
         if (hasNext())
         {
            wasNextCalled = true;
            T nextEntry = list[nextIndex];
            nextIndex++;             // Advance iterator

            return nextEntry;
         }
         else
            throw new NoSuchElementException("Illegal call to next(); " +
                                             "iterator is after end of list.");
      } // end next
      
      public void remove()
      {
         if (wasNextCalled)
         {
            // nextIndex was incremented by the call to next, so it is
            // 1 larger than the position number of the entry to be removed
            ArrayListWithIterator.this.remove(nextIndex - 1);
            nextIndex--;             // Index of next entry in iteration
            wasNextCalled = false;   // Reset flag
         }
         else
            throw new IllegalStateException("Illegal call to remove(); " +
                                            "next() was not called.");
      } // end remove		
   } // end IteratorForArrayList
} // end ArrayListWithIterator

