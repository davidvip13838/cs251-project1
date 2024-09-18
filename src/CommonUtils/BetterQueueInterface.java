package CommonUtils;

/**
 * Interface for our new BetterQueue object.
 *
 * You are explicitly forbidden from using java.util.Queue and any subclass
 * (including LinkedList, for example) and any other java.util.* library EXCEPT java.util.Objects.
 * Write your own implementation of a Queue.
 *
 * @param <E> Type of object the queue is holding
 */
public interface BetterQueueInterface<E> {
    /**
     * Add an item to the back of the queue
     * @param item item to push
     * @throws NullPointerException if the specified element is null
     */
    void add(E item);

    /**
     * Returns the front of the queue (does not remove it) or <code>null</code> if the queue is empty
     * @return front of the queue or <code>null</code> if the queue is empty
     */
    E peek();

    /**
     * Returns and removes the front of the queue
     * @return the head of the queue, or <code>null</code> if this queue is empty
     */
    E remove();

    /**
     * Returns the number of elements in the queue
     * @return integer representing the number of elements in the queue
     */
    int size();

    /**
     * Returns whether the queue is empty
     * @return true if the queue is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     * @param g graphics object to draw on
     */
    void draw(java.awt.Graphics g);
}
