package CommonUtilsTest;

import CommonUtils.BetterStack;
import CommonUtilsTest.factories.IntTestFactory;
import CommonUtilsTest.factories.SizeEmptyBasicAddRemoveBackTestFactory;
import CommonUtilsTest.factories.IntTestFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import javax.swing.*;
import java.util.EmptyStackException;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests BetterStack extensively
 */
@Timeout(value = 700, unit = MILLISECONDS)
public class BetterStackTest {
    /**
     * Very basic check.  Failing this should fail all other tests.
     */
    @Test
    void sanityCheck(){
        BetterStack<Integer> stack = new BetterStack<>();
        stack.push(0); stack.push(3); stack.push(2); stack.push(1);
        assertEquals(1, stack.pop());
        assertEquals(2, stack.pop());
        assertEquals(3, stack.pop());
        assertEquals(0, stack.pop());
    }

    /**
     * Tests standard usage of BetterStack
     */
    @Nested
    class StandardTests {
        /**
         * Test push, pop
         */
        @Nested
        class PushPopInt extends IntTestFactory {
            /**
             * Factory method for calling the appropriate function you want to test for signed ints validity
             *
             * @param num signed int to test
             * @return the result of a getField on the respective object
             * @throws Exception if something goes wrong
             */
            @Override
            protected int setGetField(int num) throws Exception {
                BetterStack<Integer> stack = null;
                try {//constructor shouldn't fail
                    stack = new BetterStack<>();
                } catch (Exception e){
                    fail("Failed to construct with error: " + e.getMessage());
                }
                stack.push(num);
                return stack.pop();
            }
        }

        /**
         * Test push, peek
         */
        @Nested
        class PushPeekInt extends IntTestFactory {
            /**
             * Factory method for calling the appropriate function you want to test for signed ints validity
             *
             * @param num signed int to test
             * @return the result of a getField on the respective object
             * @throws Exception if something goes wrong
             */
            @Override
            protected int setGetField(int num) throws Exception {
                BetterStack<Integer> stack = null;
                try {//constructor shouldn't fail
                    stack = new BetterStack<>();
                } catch (Exception e){
                    fail("Failed to construct with error: " + e.getMessage());
                }
                stack.push(num);
                return stack.peek();
            }
        }

        /**
         * Test size, empty, push, pop
         */
        @Nested
        @Timeout(value = 6000, unit = MILLISECONDS)
        class SizeEmptyAddRemoveBack extends SizeEmptyBasicAddRemoveBackTestFactory {
            BetterStack<Long> stack = new BetterStack<>();

            /**
             * Factory methods for calling the appropriate add function on the container you are testing.  Requires persistence.
             *
             * @param o object to add
             * @throws Exception if something goes wrong
             */
            @Override
            protected void add(long o) throws Exception { stack.push(o); }

            /**
             * Factory methods for calling the appropriate parameterized remove function on the container you are
             * testing.  Requires persistence.
             * If you don't have one, leave it blank and override getTestParamterizedRemove to return false.
             *
             * @param o object to add
             * @throws Exception if something goes wrong
             */
            @Override
            protected void removeParameterized(long o) throws Exception { /*nothing here on purpose, see below function*/ }
            /**
             * Gives the subclass the option of whether to run parameterized remove tests (if the type
             *   being tested can't perform that operation, for example)
             * @return whether to run parameterized remove tests
             */
            @Override
            protected boolean getTestParameterizedRemove() { return false; }

            /**
             * Factory methods for calling the appropriate remove back function on the container you are testing.  Requires persistence.
             *
             * @throws Exception if something goes wrong
             */
            @Override
            protected void removeBack() throws Exception { stack.pop(); }

            /**
             * Factory methods for calling the appropriate size() function on the container you are testing.  Requires persistence.
             */
            @Override
            protected long getSize() { return stack.size(); }

            /**
             * Factory methods for calling the appropriate isEmpty back function on the container you are testing.  Requires persistence.
             */
            @Override
            protected boolean isEmpty() { return stack.isEmpty(); }
        }

        /**
         * Tests ordered add/remove with FILO (aka stack)
         */
        @Test
        void testFILO_OrderedAddRemove(){
            final int MAX_NUM_TO_ADD = 1000000;
            BetterStack<Integer> stack = new BetterStack<>();
            for(int i=0; i<MAX_NUM_TO_ADD; i++){
                stack.push(i);
            }
            for(int i=MAX_NUM_TO_ADD-1; i>=0; i--){
                assertEquals(i, stack.pop());
            }
            assertEquals(0, stack.size());
            assertTrue(stack.isEmpty());
        }
    }

    /**
     * Tests exceptions from using BetterStack wrong
     */
    @Nested
    class ExceptionTests {
        /**
         * Does what it says, generates a "used" stack
         * @return "used" but empty stack
         */
        private BetterStack<Long> addRemoveManyElements() {
            BetterStack<Long> stack = new BetterStack<>();
            long i=0;
            for(; i<1000000; i++){//1mil
                stack.push(i);
            }
            assertEquals(i, stack.size());
            for(; i>0; i--){
                stack.pop();
            }
            assertEquals(0, stack.size());
            return stack;
        }
        /**
         * Test pop exception throws
         */
        @Nested
        class ExceptPop {
            /**
             * Tests pop exception on unused stack
             */
            @Test
            void testEmptyStackException(){
                BetterStack<Long> stack = new BetterStack<>();
                assertThrows(EmptyStackException.class, stack::pop);
            }

            /**
             * Tests pop exception on "used" stack
             */
            @Test
            void testEmptyStackExceptionAfterAddRemove(){
                BetterStack<Long> stack = addRemoveManyElements();
                assertThrows(EmptyStackException.class, stack::pop);
            }
        }

        /**
         * Test peek exception throws
         */
        @Nested
        class ExceptPeek {
            /**
             * Test peek exception on unused stack
             */
            @Test
            void testEmptyStackException(){
                BetterStack<Long> stack = new BetterStack<>();
                assertThrows(EmptyStackException.class, stack::peek);
            }

            /**
             * Tests peek exception on "used" stack
             */
            @Test
            void testEmptyStackExceptionAfterAddRemove(){
                BetterStack<Long> stack = addRemoveManyElements();
                assertThrows(EmptyStackException.class, stack::peek);
            }
        }
    }
}
