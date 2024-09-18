package CommonUtilsTest.factories;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Makes an abstract class for testing anything that has size() and empty() functions
 *   Size should increase and decrease linearly with adding and removing
 * NOTE: Does NOT test what element was removed, only that one was
 */
public abstract class SizeEmptyBasicAddRemoveBackTestFactory {
    /**
     * Test basic size and empty functions
     */
    @Test
    void test1sSizeAndEmpty(){
        try {
            assert(isEmpty());
            //1s
            for(long i=0; i<maxElementsInContainer(); i++){
                add(i);
                assertEquals(i+1, getSize());
            }
        } catch (Exception e){
            fail("Failed adding elements: " + e.getMessage());
        }
    }

    /**
     * Test basic size and empty functions, different numbers
     */
    @Test
    void test2sSize(){
        try {
            //2s
            for(long i=0, j=1; i<2*maxElementsInContainer(); i+=2, j++){
                add(i);
                assertEquals(j, getSize());
            }
        } catch (Exception e){
            fail("Failed adding elements: " + e.getMessage());
        }
    }

    /**
     * Test size function with parameterized remove
     */
    @Test
    void testSizeAndRemoveParameterized(){
        if(getTestParameterizedRemove()){
            try {
                int defaultNumInContainer = 100;
                for(long i=0; i<defaultNumInContainer; i++){
                    add(i);
                }
                assertEquals(defaultNumInContainer, getSize());
                //1s
                for(long i=defaultNumInContainer; i<maxElementsInContainer(); i++){
                    //add
                    add(i);
                    assertEquals(defaultNumInContainer+1, getSize());
                    //remove
                    removeParameterized(i);
                    assertEquals(defaultNumInContainer, getSize());
                }
            } catch (Exception e){
                fail("Failed parameterized remove: " + e.getMessage());
            }
        }
    }

    /**
     * Test size and isEmpty functions with parameterized remove
     */
    @Test
    void testSizeAndRemoveParameterizedAndEmpty(){
        if(getTestParameterizedRemove()){
            try {
                assertEquals(0, getSize());
                assertTrue(isEmpty());
                //1s
                for(long i=0; i<maxElementsInContainer(); i++){
                    //add
                    add(i);
                    assertEquals(1, getSize());
                    assertFalse(isEmpty());
                    //remove
                    removeParameterized(i);
                    assertEquals(0, getSize());
                    assertTrue(isEmpty());
                }
            } catch (Exception e){
                fail("Failed parameterized remove: " + e.getMessage());
            }
        }
    }

    /**
     * Test size function with removeBack
     */
    @Test
    void testSizeAndRemoveBack(){
        if(getTestRemoveBack()){
            try {
                int defaultNumInContainer = 100;
                for(long i=0; i<defaultNumInContainer; i++){
                    add(i);
                }
                assertEquals(defaultNumInContainer, getSize());
                //1s
                for(long i=defaultNumInContainer; i<maxElementsInContainer(); i++){
                    //add
                    add(i);
                    assertEquals(defaultNumInContainer+1, getSize());
                    //remove
                    removeBack();
                    assertEquals(defaultNumInContainer, getSize());
                }
            } catch (Exception e){
                fail("Failed remove back: " + e.getMessage());
            }
        }
    }

    /**
     * Test size and isEmpty functions with removeBack
     */
    @Test
    void testSizeAndRemoveBackAndEmpty(){
        if(getTestRemoveBack()){
            try {
                assertEquals(0, getSize());
                assertTrue(isEmpty());
                //1s
                for(long i=0; i<maxElementsInContainer(); i++){
                    //add
                    add(i);
                    assertEquals(1, getSize());
                    assertFalse(isEmpty());
                    //remove
                    removeBack();
                    assertEquals(0, getSize());
                    assertTrue(isEmpty());
                }
            } catch (Exception e){
                fail("Failed remove back: " + e.getMessage());
            }
        }
    }

    /**
     * Factory methods for calling the appropriate add function on the container you are testing.  Requires persistence.
     * @param o object to add
     * @throws Exception if something goes wrong
     */
    protected abstract void add(long o) throws Exception;
    /**
     * Factory methods for calling the appropriate parameterized remove function on the container you are
     *   testing.  Requires persistence.
     * If you don't have one, leave it blank and override getTestParamterizedRemove to return false.
     * @param o object to add
     * @throws Exception if something goes wrong
     */
    protected abstract void removeParameterized(long o) throws Exception;
    /**
     * Factory methods for calling the appropriate remove back function on the container you are
     *   testing.  Requires persistence.
     * If you don't have one, leave it blank and override getTestRemoveBack to return false.
     * @throws Exception if something goes wrong
     */
    protected abstract void removeBack() throws Exception;
    /**
     * Factory methods for calling the appropriate size() function on the container you are testing.  Requires persistence.
     */
    protected abstract long getSize();
    /**
     * Factory methods for calling the appropriate isEmpty back function on the container you are testing.  Requires persistence.
     */
    protected abstract boolean isEmpty();

    /**
     * Gives the subclass the option of whether to run parameterized remove tests (if the type
     *   being tested can't perform that operation, for example)
     * @return whether to run parameterized remove tests
     */
    protected boolean getTestParameterizedRemove() { return true; }

    /**
     * Gives the subclass the option of whether to run remove back tests (if the type
     *   being tested can't perform that operation, for example)
     * @return whether to run remove back tests
     */
    protected boolean getTestRemoveBack() { return true; }

    /**
     * Gives the subclass the option to override the maximum container size based on what is being tested
     *  Default 800mil (don't want test to run too long)
     * @return Max size of container to test
     */
    protected long maxElementsInContainer() { return 20000000L; /*80mil*/ }
}
