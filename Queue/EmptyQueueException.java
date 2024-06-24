/**
 * A class of runtime exceptions thrown by methods to
 * indicate that a queue is empty.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public class EmptyQueueException extends RuntimeException {
   public EmptyQueueException() {
      this(null);
   } // end default constructor

   public EmptyQueueException(String message) {
      super(message);
   } // end constructor
} // end EmptyQueueException