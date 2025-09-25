public final class Sept25 {
    /**
     * Deletes a node from a singly linked list given only a reference to that node.
     *
     * @param <T>  element type
     * @param node the node to delete (must not be the last node)
     *
     * @throws IllegalArgumentException if {@code node} is null or the last node
     */
    public static <T> void deleteGivenNode(Node<T> node) {
        if (node == null || node.next == null) {
            throw new IllegalArgumentException("incorrect node");
        }
        node.data = node.next.data;
        node.next = node.next.next;

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