package CommonUtils;

import java.awt.*;

/**
 * @implNote implement a queue using a circular array with initial capacity 8.
 *
 * Implement BetterQueueInterface and add a constructor
 *
 * You are explicitly forbidden from using java.util.Queue and any subclass
 * (including LinkedList, for example) and any other java.util.* library EXCEPT java.util.Objects.
 * Write your own implementation of a Queue.
 *
 * @param <E> thse type of object this queue will be holding
 */
public class BetterQueue<E> implements BetterQueueInterface<E> {

    private final int INIT_CAPACITY = 8;

    private final int INCREASE_FACTOR = 2;
    private final int CONSTANT_INCREMENT = 32;

    private final double DECREASE_FACTOR = 0.5;

    private E[] queue;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs an empty queue
     */
    @SuppressWarnings("unchecked")
    public BetterQueue(){
        this.queue = (E[]) new Object[INIT_CAPACITY];
        this.front = 0;
        this.back  = -1;
        this.size = 0;
    }

    /**
     * Add an item to the back of the queue
     *
     * @param item item to push
     * @throws NullPointerException if the specified element is null
     */
    @Override
    public void add(E item) {
        if (item == null) {
            throw new NullPointerException("The specified item is null");
        }

        // Check if we need to grow the array
        if (size == queue.length) {
            resize(queue.length * INCREASE_FACTOR);
        }

        // Add element to the queue and adjust the back pointer
        back = (back + 1) % queue.length;
        queue[back] = item;
        size++;
    }

    /**
     * Returns the front of the queue (does not remove it) or <code>null</code> if the queue is empty
     *
     * @return front of the queue or <code>null</code> if the queue is empty
     */
    @Override
    public E peek() {
        if (isEmpty()) {
            return null;
        }
        return queue[front];
    }

    /**
     * Returns and removes the front of the queue
     *
     * @return the head of the queue, or <code>null</code> if this queue is empty
     */
    @Override
    public E remove() {
        if (isEmpty()) {
            return null;
        }

        E item = queue[front];
        queue[front] = null;
        front = (front + 1) % queue.length;
        size--;

        // Check if we need to shrink the array
        if (size < queue.length * DECREASE_FACTOR && queue.length > INIT_CAPACITY) {
            resize(Math.max(INIT_CAPACITY, (int) (queue.length * DECREASE_FACTOR)));
        }

        return item;
    }

    /**
     * Returns the number of elements in the queue
     *
     * @return integer representing the number of elements in the queue
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns whether the queue is empty
     *
     * @return true if the queue is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Resizes the queue to the specified new capacity
     */
    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newQueue = (E[]) new Object[newCapacity];
        for (int i = 0; i < size; i++) {
            newQueue[i] = queue[(front + i) % queue.length];
        }
        queue = newQueue;
        front = 0;
        back = size - 1;
    }

    /**
     * DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
     *
     * @param g graphics object to draw on
     */
    @Override
    public void draw(Graphics g) {
        // DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if(g != null) g.getColor();
        // todo GRAPHICS DEVELOPER:: draw the queue how we discussed
        // 251 STUDENTS:: YOU ARE NOT THE GRAPHICS DEVELOPER!
    }
}
