package CommonUtils;

import java.util.Arrays;
import java.util.EmptyStackException;

public class BetterStack<E> implements BetterStackInterface<E> {

    private final int INIT_CAPACITY = 8;
    private final int INCREASE_FACTOR = 2;
    private final int CONSTANT_INCREMENT = 1 << 5; // 32
    private final double DECREASE_FACTOR = 0.5;

    private E[] stack; // The array to store the elements
    private int size;  // Tracks the number of elements in the stack

    @SuppressWarnings("unchecked")
    public BetterStack() {
        this.stack = (E[]) new Object[INIT_CAPACITY];
        this.size = 0;
    }

    @Override
    public void push(E item) throws OutOfMemoryError {
        if (size == stack.length) {
            resizeStack(true); // Increase capacity
        }
        stack[size++] = item;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        E item = stack[--size];
        stack[size] = null; // Avoid memory leak

        if (size < stack.length * DECREASE_FACTOR && stack.length > INIT_CAPACITY) {
            resizeStack(false); // Decrease capacity
        }

        return item;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return stack[size - 1];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    // Helper function to resize the stack
    private void resizeStack(boolean increase) {
        int newCapacity;
        if (increase) {
            long expandedCapacity = (long) stack.length * INCREASE_FACTOR;
            newCapacity = expandedCapacity > Integer.MAX_VALUE ? stack.length + CONSTANT_INCREMENT : (int) expandedCapacity;
            if (newCapacity <= stack.length) {
                throw new OutOfMemoryError("Stack cannot grow any larger.");
            }
        } else {
            newCapacity = Math.max((int) (stack.length * DECREASE_FACTOR), INIT_CAPACITY);
        }
        stack = Arrays.copyOf(stack, newCapacity);
    }

    @Override
    public void draw(java.awt.Graphics g) {
        // DO NOT MODIFY NOR IMPLEMENT THIS FUNCTION
        if (g != null) g.getColor();
    }
}
