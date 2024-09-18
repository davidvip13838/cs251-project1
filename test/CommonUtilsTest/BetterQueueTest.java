package CommonUtilsTest;


import CommonUtils.BetterQueue;
import CommonUtilsTest.factories.SizeEmptyBasicAddRemoveBackTestFactory;
import CommonUtilsTest.factories.IntTestFactory;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests BetterQueue extensively
 */
@Timeout(value = 700, unit = TimeUnit.MILLISECONDS)
public class BetterQueueTest {
    /**
     * Very basic check.  Failing this should fail all other tests.
     */
    @Test
    void sanityCheck(){
        BetterQueue<Integer> q = new BetterQueue<>();
        q.add(0); q.add(3); q.add(2); q.add(1);
        assertEquals(0, q.remove());
        assertEquals(3, q.remove());
        assertEquals(2, q.remove());
        assertEquals(1, q.remove());
    }

    /**
     * Couple of tests to enforce behavior regarding empty lists, etc
     */
    @Nested
    class NullTests {
        /**
         * Test null pointer exception required on add
         */
        @Test
        void NullPointerAddTest(){
            BetterQueue<Integer> q = new BetterQueue<>();
            assertThrows(NullPointerException.class, () -> q.add(null));
        }


        /**
         * Tests clear and that remove and peek return null when empty
         * @param num number of elements to test with
         */
        @ParameterizedTest(name = "Num elements = {0}")
        @ValueSource(ints = {0, 1, 65535, 1000000})
        void testRemoveAndNullReturns(int num){
            BetterQueue<Integer> q = new BetterQueue<>();
            assertNull(q.remove());
            assertNull(q.peek());
            for(int i=0; i<num; i++){
                q.add(i);
                assertEquals(i+1, q.size());
            }
            for(int i=0; i<num; i++){
                q.remove();//ignore return type
            }
            assertEquals(0, q.size());
            assertNull(q.remove());
            assertNull(q.peek());
        }

    }

    /**
     * Tests standard usage of BetterQueue
     */
    @Nested
    class StandardTests {
        /**
         * Tests add, remove with large data set
         */
        @Nested
        class AddRemoveInt extends IntTestFactory {
            /**
             * Factory method for calling the appropriate function you want to test for signed ints validity
             *
             * @param num signed int to test
             * @return the result of a getField on the respective object
             * @throws Exception if something goes wrong
             */
            @Override
            protected int setGetField(int num) throws Exception {
                BetterQueue<Integer> q = null;
                try {
                    q = new BetterQueue<>();
                } catch (Exception e){
                    fail("Failed to construct with error: " + e.getMessage());
                }
                q.add(num);
                return q.remove();
            }
        }

        /**
         * Tests add, peek with large data set
         */
        @Nested
        class AddPeekInt extends IntTestFactory {
            /**
             * Factory method for calling the appropriate function you want to test for signed ints validity
             *
             * @param num signed int to test
             * @return the result of a getField on the respective object
             * @throws Exception if something goes wrong
             */
            @Override
            protected int setGetField(int num) throws Exception {
                BetterQueue<Integer> q = null;
                try {
                    q = new BetterQueue<>();
                } catch (Exception e){
                    fail("Failed to construct with error: " + e.getMessage());
                }
                q.add(num);
                return q.peek();
            }
        }

        /**
         * Tests size, empty, add, remove
         */
        @Nested
        @Timeout(value = 9000, unit = TimeUnit.MILLISECONDS)
        class SizeEmptyAddRemoveTestFactory extends SizeEmptyBasicAddRemoveBackTestFactory {
            BetterQueue<Long> q = new BetterQueue<>();

            /**
             * Factory methods for calling the appropriate add function on the container you are testing.  Requires persistence.
             *
             * @param o object to add
             * @throws Exception if something goes wrong
             */
            @Override
            protected void add(long o) throws Exception { q.add(o); }

            /**
             * Factory methods for calling the appropriate parameterized remove function on the container you are
             * testing.  Requires persistence.
             * If you don't have one, leave it blank and override getTestParamterizedRemove to return false.
             *
             * @param o object to add
             * @throws Exception if something goes wrong
             */
            @Override
            protected void removeParameterized(long o) throws Exception { /*nothing here on purpose, see below function*/}
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
            protected void removeBack() throws Exception { q.remove(); }

            /**
             * Factory methods for calling the appropriate size() function on the container you are testing.  Requires persistence.
             */
            @Override
            protected long getSize() { return q.size(); }

            /**
             * Factory methods for calling the appropriate isEmpty back function on the container you are testing.  Requires persistence.
             */
            @Override
            protected boolean isEmpty() { return q.isEmpty(); }
        }


        /**
         * Tests ordered add/remove with FIFO (aka queue)
         */
        @Test
        void testFIFO_OrderedAddRemove(){
            final int MAX_NUM_TO_ADD = 1000000;
            BetterQueue<Integer> q = new BetterQueue<>();
            for(int i=0; i<MAX_NUM_TO_ADD; i++){
                q.add(i);
            }
            for(int i=0; i<MAX_NUM_TO_ADD; i++){
                assertEquals(i, q.remove());
            }
            assertEquals(0, q.size());
            assertTrue(q.isEmpty());
        }

        /**
         * Assumes initial capacity is 8, plays around with the "circular" part
         */
        @Nested
        class VerifyCircularQueueFunctionality{
            /**
             * Simple helper function to simplify code
             * @param q queue to remove from
             * @param expected expected remove value
             * @param sizeBefore expected initial size
             * @param emptyAfter if the queue should be empty after
             */
            private void checkRemove(BetterQueue<Integer> q, Integer expected, Integer sizeBefore, boolean emptyAfter){
                //check preconditions
                assertEquals(sizeBefore, q.size());
                assertFalse(q.isEmpty());
                assertEquals(expected, q.peek());
                //do remove
                AtomicReference<Integer> temp = new AtomicReference<>();
                assertDoesNotThrow(() -> temp.set(q.remove()), "Failed to remove");
                assertEquals(expected, temp.get());
                //check postconditions
                if(emptyAfter){
                    assertEquals(0, q.size());
                    assertTrue(q.isEmpty());
                } else {
                    assertEquals(sizeBefore-1, q.size());
                    assertFalse(q.isEmpty());
                }
            }

            /**
             * Helper function to assert facts about a queue before and after adding an element
             * @param q queue to insert into
             * @param toInsert element to insert
             * @param sizeBefore expected size before the add
             * @param currPeek current first element, disregarded if sizeBefore == 0
             */
            private void checkAdd(BetterQueue<Integer> q, Integer toInsert, Integer sizeBefore, Integer currPeek){
                //check preconditions
                assertEquals(sizeBefore, q.size());
                assertEquals((sizeBefore == 0), q.isEmpty());
                if(sizeBefore != 0){
                    assertEquals(currPeek, q.peek());
                }
                //do add
                assertDoesNotThrow(() -> q.add(toInsert), "Failed to add");
                //check postconditions
                assertEquals(sizeBefore+1, q.size());
                assertFalse(q.isEmpty());
                if(sizeBefore == 0){
                    assertEquals(toInsert, q.peek());
                }
            }

            /**
             * alias for checkAdd with -1 current peek
             * @param q queue to insert into
             * @param toInsert element to insert
             */
            private void checkAddToEmpty(BetterQueue<Integer> q, Integer toInsert) {
                checkAdd(q, toInsert, 0, -1);
            }

            /**
             * Ending params: 9 elements, [7, 15]
             * @param q queue to do things to
             */
            private void doInitialSequence(BetterQueue<Integer> q){
                //combo of adds and removes::
                //  add 4, remove 2, add 1, remove 3, empty,
                checkAddToEmpty(q, 0);
                checkAdd(q, 1, 1, 0);
                checkAdd(q, 2, 2, 0);
                checkAdd(q, 3, 3, 0);
                checkRemove(q, 0, 4, false);
                checkRemove(q, 1, 3, false);
                checkAdd(q, 4, 2, 2);
                checkRemove(q, 2, 3, false);
                checkRemove(q, 3, 2, false);
                checkRemove(q, 4, 1, true);

                //  add 3, remove 2, last=0 first=end, not empty
                checkAddToEmpty(q, 5);
                checkAdd(q, 6, 1, 5);
                checkAdd(q, 7, 2, 5);
                checkRemove(q, 5, 3, false);
                checkRemove(q, 6, 2, false);

                //  add 7, full=8, peek correct
                for(int i=0; i<7; i++){
                    checkAdd(q, 8+i, 1+i, 7);
                }

                //  add 1, check post-resize size and peek
                checkAdd(q, 15, 8, 7);
            }

            /**
             * Series of adds/removes to test validity of circular array implementation (targeted test)
             */
            @Test
            void testResize(){
                BetterQueue<Integer> q = new BetterQueue<>();
                doInitialSequence(q);
                //  remove everything (9 elements)
                for(int i=0; i<9; i++){
                    checkRemove(q, 7+i, 9-i, (i==8));
                }
            }

            /**
             * Series of adds/removes to test validity of circular array implementation (targeted test)
             */
            @Test
            void testWriteOverOldSlots(){
                BetterQueue<Integer> q = new BetterQueue<>();
                doInitialSequence(q);

                //do it all again but instead of removing everything::
                //  add 6, remove 8, add 9
                for(int i=0; i<6; i++){
                    checkAdd(q, 16+i, 9+i, 7);
                }
                for(int i=0; i<8; i++){
                    checkRemove(q, 7+i, 15-i, false);
                }
                for(int i=0; i<9; i++){
                    checkAdd(q, 22+i, 7+i, 15);
                }
                //  remove everything
                for(int i=0; i<16; i++){
                    checkRemove(q, 15+i, 16-i, (i==15));
                }
            }
        }
    }

    
    @Test
    void drawVisualizeDummyTestForCodeCoverage(){
        BetterQueue<Integer> queue = new BetterQueue<>();
        JPanel panel = new JPanel();
        queue.draw(panel.getGraphics());
    }
}
