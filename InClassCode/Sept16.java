final public class Sept16 {

    public static void main(String[] args) {
        Object[] integers = new Integer[1];
        integers = addLastArray(integers, 0, 445);
        integers = addLastArray(integers, 1, 447);

    }

    /**
     * Returns the index of the first occurrence of {@code target} in {@code array},
     * or {@code -1} if not found.
     *
     * <p>
     * Equality is tested with {@link Object#equals(Object)}. This method is
     * {@code null}-safe: if {@code target} is {@code null}, it matches a
     * {@code null}
     * slot in the array.
     * </p>
     *
     * @param <T>    the element type
     * @param array  the array to search (must not be {@code null})
     * @param target the value to find (may be {@code null})
     * @return the zero-based index of {@code target}, or {@code -1} if absent
     */
    public static <T> int indexOf(T[] array, T target) {
        for (int i = 0; i < array.length; i++) {
            try {
                if (array[i].equals(target))
                    return i;
            } catch (NullPointerException e) {
                if (target == null)
                    return i;
            }
        }
        return -1;

    }

    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * Inserts {@code value} at the end of an array
     * of size {@code size}, returning the (possibly new) array.
     *
     * <p>
     * If {@code size == array.length}, this method <em>resizes</em> by allocating
     * a new array (e.g., 2x capacity), copies existing elements, then writes
     * {@code value} at index {@code size}.
     * </p>
     * 
     * <p>
     * <strong>Contract for callers:</strong> After calling this method, your
     * logical size becomes {@code size + 1}. Track that separately from
     * {@code array.length}.
     * </p>
     *
     * @param <T>   element type
     * @param array current array {@code size} elements
     * @param size  logical number of elements currently in {@code array} (0 ≤ size
     *              ≤ array.length)
     * @param value value to insert at the end (destination index size)
     * @return the array to use going forward (may be the same object or a new one)
     */
    public static <T> T[] addLastArray(T[] array, int size, T value) {
        if(size == array.length){
            @SuppressWarnings("unchecked")
            T[] newArray = (T[]) new Object[2*array.length];
            for(int i=0; i<size; i++){
                newArray[i] = array[i];
            }
            array = newArray;
        }
        array[size] = value;
        
        return array;
    }
    // ─────────────────────────────────────────────────────────────────────────────

    /**
     * A minimal singly linked list node.
     *
     * @param <T> element type
     */
    private static final class Node<T> {
        /** payload value */
        public T data;
        /** reference to the next node (or {@code null} for tail) */
        public Node<T> next;

        /** Creates a node with {@code data} and {@code next == null}. */
        public Node(T data) {
            this(data, null);
        }

        /** Creates a node with {@code data} and explicit {@code next}. */
        public Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    /**
     * Inserts {@code value} at the beginning of a singly linked list and
     * returns the new head node.
     *
     * @param <T>   element type
     * @param head  current head node (may be {@code null} for empty list)
     * @param value value to insert as the new first element
     * @return the new head node containing {@code value}
     */
    public static <T> Node<T> addFirst(Node<T> head, T value) {
        Node<T> newNode = new Node<>(value);
        newNode.next = head;
        head = newNode;
        return head;
    }
}
