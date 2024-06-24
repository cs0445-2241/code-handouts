/**
 * A class that implements the ADT queue by using an expandable
 * circular array with one unused location after the back of the queue.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.1
 */
public final class ArrayQueue<T> implements QueueInterface<T> {
   private T[] queue; // Circular array of queue entries and one unused location
   private int frontIndex; // Index of front entry
   private int backIndex; // Index of back entry
   private boolean initialized = false;
   private static final int DEFAULT_CAPACITY = 3;
   private static final int MAX_CAPACITY = 10000;

   public ArrayQueue() {
      this(DEFAULT_CAPACITY);
   } // end default constructor

   public ArrayQueue(int initialCapacity) {
      checkCapacity(initialCapacity);

      // The cast is safe because the new array contains null entries
      @SuppressWarnings("unchecked")
      T[] tempQueue = (T[]) new Object[initialCapacity + 1];
      queue = tempQueue;
      frontIndex = 0;
      backIndex = initialCapacity;
      initialized = true;
   } // end constructor

   public void enqueue(T newEntry) {
      System.out.println("enqueue(" + newEntry + ")"); // ***TESTING
      checkInitialization();
      ensureCapacity();
      backIndex = (backIndex + 1) % queue.length; // Index of location after current back of queue
      queue[backIndex] = newEntry;
      System.out.println("queue[" + backIndex + "] = " + newEntry); // ***TESTING
   } // end enqueue

   public T getFront() {
      checkInitialization();
      if (isEmpty())
         throw new EmptyQueueException();
      else
         return queue[frontIndex];
   } // end getFront

   public T dequeue() {
      checkInitialization();
      if (isEmpty())
         throw new EmptyQueueException();
      else {
         T front = queue[frontIndex];
         queue[frontIndex] = null;
         frontIndex = (frontIndex + 1) % queue.length; // Index of new front of queue
         return front;
      } // end if
   } // end dequeue

   public boolean isEmpty() {
      return frontIndex == ((backIndex + 1) % queue.length);
   } // end isEmpty

   // Question 3, Chapter 11
   public void clear() {
      checkInitialization();
      if (!isEmpty()) { // deallocates only the used portion
         for (int index = frontIndex; index != backIndex; index = (index + 1) % queue.length) {
            queue[index] = null;
         } // end for

         queue[backIndex] = null;
      } // end if

      frontIndex = 0;
      backIndex = queue.length - 1;
   } // end clear

   /*
    * // Question 4, Chapter 11
    * public void clear()
    * {
    * while (!isEmpty())
    * {
    * dequeue();
    * } // end while
    * } // end clear
    */

   // Throws an exception if this object is not initialized.
   private void checkInitialization() {
      if (!initialized)
         throw new SecurityException("ArrayQueue object is not initialized properly.");
   } // end checkInitialization

   // Throws an exception if the client requests a capacity that is too large.
   private void checkCapacity(int capacity) {
      if (capacity > MAX_CAPACITY)
         throw new IllegalStateException("Attempt to create a queue " +
               "whose capacity exceeds " +
               "allowed maximum.");
   } // end checkCapacity

   // Doubles the size of the array queue if it is full.
   // Precondition: checkInitialization has been called.
   private void ensureCapacity() {
      if (frontIndex == ((backIndex + 2) % queue.length)) // If array is full,
      { // double size of array
         System.out.println("Doubling Array Size"); // ***TESTING
         T[] oldQueue = queue;
         int oldSize = oldQueue.length;
         int newSize = 2 * oldSize;
         checkCapacity(newSize - 1); // Queue capacity is 1 fewer than array length

         // The cast is safe because the new array contains null entries
         @SuppressWarnings("unchecked")
         T[] tempQueue = (T[]) new Object[newSize];
         queue = tempQueue;

         // Number of queue entries = oldSize - 1; index of last entry = oldSize - 2
         for (int index = 0; index < oldSize - 1; index++) {
            queue[index] = oldQueue[frontIndex];
            System.out.println("queue[" + index + "] = " + oldQueue[frontIndex]); // ***TESTING
            frontIndex = (frontIndex + 1) % oldSize;
         } // end for

         frontIndex = 0;
         backIndex = oldSize - 2;
         System.out.println("End ensureCapacity(): newSize = " + newSize); // ***TESTING
      } // end if
   } // end ensureCapacity
} // end ArrayQueue