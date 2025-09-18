public final class Sept18 {
    /**
     * Removes the first occurrence of {@code target} from a singly-linked bag. *
     *
     * @param <T>    element type
     * @param head   the head node of the bag (may be {@code null})
     * @param target the value to remove (must not be {@code null})
     * @return the new head of the bag (may be unchanged if {@code target} not
     *         found)
     */
    public static <T> Node<T> remove(Node<T> head, T target) {
        // traverse the chain searching for target
        Node<T> currNode = head;
        while (currNode != null) {
            if (target.equals(currNode.data)) {
                // if found, replace its data with head's data
                currNode.data = head.data;
                // delete head
                head = head.next;
                // return the new head
                return head;
            }
            currNode = currNode.next;
        }
        // if not found, return the old head
        return head;
    }

    /** Minimal singly linked node. */
    public static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

}