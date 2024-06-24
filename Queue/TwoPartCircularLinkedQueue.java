/**
 * A class that implements the ADT queue by using a
 * two-part circular chain of nodes.
 * 
 * @author Frank M. Carrano
 * @author Timothy M. Henry
 * @version 4.0
 */
public final class TwoPartCircularLinkedQueue<T> implements QueueInterface<T> {
	private Node queueNode; // References first node in queue
	private Node freeNode; // References node after back of queue

	public TwoPartCircularLinkedQueue() {
		freeNode = new Node(null, null);
		freeNode.setNextNode(freeNode);
		queueNode = freeNode;
	} // end default constructor

	public void enqueue(T newEntry) {
		freeNode.setData(newEntry);

		if (isChainFull()) {
			// Allocate a new node and insert it after the node that
			// freeNode references
			Node newNode = new Node(null, freeNode.getNextNode());
			freeNode.setNextNode(newNode);
		} // end if

		freeNode = freeNode.getNextNode();
	} // end enqueue

	public T getFront() {
		if (isEmpty())
			throw new EmptyQueueException();
		else
			return queueNode.getData();
	} // end getFront

	public T dequeue() {
		T front = getFront(); // Might throw EmptyQueueException
		assert !isEmpty();

		queueNode.setData(null);
		queueNode = queueNode.getNextNode();

		return front;
	} // end dequeue

	public boolean isEmpty() {
		return queueNode == freeNode;
	} // end isEmpty

	public void clear() {
		while (!isEmpty())
			dequeue();
	} // end clear

	private boolean isChainFull() {
		return queueNode == freeNode.getNextNode();
	} // end isChainFull

	private class Node {
		private T data; // Queue entry
		private Node next; // Link to next node

		private Node(T dataPortion) {
			data = dataPortion;
			next = null;
		} // end constructor

		private Node(T dataPortion, Node linkPortion) {
			data = dataPortion;
			next = linkPortion;
		} // end constructor

		private T getData() {
			return data;
		} // end getData

		private void setData(T newData) {
			data = newData;
		} // end setData

		private Node getNextNode() {
			return next;
		} // end getNextNode

		private void setNextNode(Node nextNode) {
			next = nextNode;
		} // end setNextNode
	} // end Node
} // end TwoPartCircularLinkedQueue