/** A class of bags whose entries are stored in a fixed-size array.
 * @author Sherif Khattab
 *
 */

//The ArrayBag class is a generic class with type parameter T.
/**
 * @author Sherif Khattab
 *
 * @param <T>
 */
public class ArrayBag<T> implements BagInterface<T> {
	private final T[] bag;//The array reference is immutable. This doesn't prevent changing its entries though.
	// If we need to make the array resizeable, perhaps we want to remove final from here.
	private int size;//the number of elements actually stored in the array; This can be less than bag.length.
	private boolean initialized = false;


	/**
	 * 
	 */
	public ArrayBag() {
		this(DEFAULT_CAPACITY);//Call ArrayBag(int).
	}

	/** Create an ArrayBag with a maximum capacity of capacity
	 * @param capacity The maximum number of elements in the ArrayBag
	 */
	public ArrayBag(int capacity) {
		if(capacity > MAX_CAPACITY) {
			throw new IllegalStateException("An attempt to create a bag with capacity > maximum capacity"
					+ " was blocked.");
		} else {
			//Java doesn't allow the creation of arrays of type parameters. The commented line below is illegal.
			// array = new T[capacity];
			@SuppressWarnings("unchecked")
			T[] temp = (T[]) new Object[capacity];//Object is the upper bound of the type parameter T
			bag = temp;
			size = 0;	
			initialized = true;
		}
	}
	/** Gets the current number of entries in this bag.
	 * @return The integer number of entries currently in the bag. 
	 */

	public int getCurrentSize() {
		return size;
	}

	/** Sees whether this bag is empty.
	 * @return True if the bag is empty, or false if not. 
	 */
	public boolean isEmpty() {
		return size==0;
	}

	/** Adds a new entry to this bag.
	 * @param newEntry  The object to be added as a new entry.
	 * @return True if the addition is successful, or false if not.
	 */
	public boolean add(T newEntry) {
		checkInitialization();
		boolean result = false;
		if(size >= bag.length) {
			result = false;
		} else {
			bag[size] = newEntry;
			size ++;
			result = true;
		}
		return result;
	}

	/** Removes one unspecified entry from this bag, if possible.
	 * @return  Either the removed entry, if the removal was successful, or null. 
	 */
	public T remove() {
		checkInitialization();
		T result = null;
		result = removeEntry(size - 1);
		return result;
	}

	/** Removes one occurrence of a given entry from this bag, if possible.
	 * @param anEntry  The entry to be removed.
	 * @return  True if the removal was successful, or false if not.
	 */
	public boolean remove(T anEntry) {
		checkInitialization();
		boolean result = false;
		int index = getIndexOf(anEntry);
		if(index != -1) {
			result = anEntry.equals(removeEntry(index)); //Better than merely checking removeEntry(index) != null.
		}
		return result;
	}

	/** Removes all entries from this bag. 
	 */
	public void clear() {
		//The line below is not correct because bag is final.
		//bag = null;
		while(!isEmpty()) {
			remove();
		}
	}

	/** Counts the number of times a given entry appears in this bag.
	 * @param anEntry  The entry to be counted.
	 * @return The number of times anEntry appears in the bag. 
	 */
	public int getFrequencyOf(T anEntry) {
		checkInitialization();
		int count = 0;
		for(T entry : bag) {
			if(anEntry.equals(entry)) { //This is safer than entry.equals(anEntry) as the bag may contain nulls.
				count++;				
			}
		}
		return count;
	}

	/** Tests whether this bag contains a given entry.
	 * @param anEntry  The entry to locate.
	 * @return  True if the bag contains anEntry, or false if not. 
	 */
	public boolean contains(T anEntry) {
		checkInitialization();
		//		boolean found = false;
		//		for(T entry : bag) {
		//			if(entry.equals(anEntry)) {
		//				found = true;				
		//			}
		//		}
		//		return found;
		return (getIndexOf(anEntry) != -1);
	}

	/** Retrieves all entries that are in this bag.
	 * @return  A newly allocated array of all the entries in the bag.
	 *          Note: If the bag is empty, the returned array is empty. 
	 */
	public Object[] toArray() {
		checkInitialization();
		//The below line is too dangerous! You give the client a reference to your underlying implementation.
		//result = bag;
		@SuppressWarnings("unchecked")
		T[] result = (T[]) new Object[size];	
		for(int i=0; i<size; i++) {
			result[i] = bag[i]; //This is shallow copying (just copying references to bag entries).
		}
		return result;
		//Alternatively, we can use:
		//return Arrays.copyOf(bag, size);
	}

	//You don't want to make this method public. It exposes the underlying array implementation.
	/** A helper method to find the index of an object inside the array bag
	 * @param anEntry The entry to get its index inside the bag
	 * @return the integer index of anEntry inside bag, or -1 if anEntry is not found.
	 * Precondition: checkInitialization() has been called.
	 */
	private int getIndexOf(T anEntry) {
		int index = -1;
		for(int i=0; i<size; i++) {
			if(anEntry.equals(bag[i])) { //Safer than bag[i].equals(anEntry).
				index = i;
				break;
			}
		}
		return index;		
	}
	
	private void checkInitialization() {
		if(!initialized) {
			throw new SecurityException("The ArrayBag object has not been properly initialized.");
		}
	}
	
	/** Removes and returns an entry at the given index.
	 * @param index The index of the entry to be removed.
	 * @return The removed object or null if no such entry exists.
	 * Precondition: 0 <= index <= size.
	 * Precondition: checkInitialization() has been called.
	 */
	private T removeEntry(int index) {
		T result = null;
		if((index >= 0) && (index < size)){
			result = bag[index];
			// We can shift the array elements one position up.
			// To get the for loop correct, you may check its first and last iteration.
			//For example, i<size causes an ArrayOutOfBoundException because in the last iteration
			// bag[size] will be indexed.
			//for(int i=index; i<size-1; i++) {
			//	bag[i] = bag[i+1];
			//}
			
			//Or, more efficiently, we can place the last entry at index.
			bag[index] = bag[size-1];
            bag[size - 1] = null;
			size--;
		}
		return result;
	}

}
