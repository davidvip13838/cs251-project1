package CommonUtilsTest.factories;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Makes an abstract class for testing anything that sets and gets signed integers
 */
public abstract class IntTestFactory {
    /**
     * Test valid signed ints
     * @param num signed int to test
     */
    @ParameterizedTest(name = "Valid num = {0}")
    @ValueSource(ints = {0, 1, 2147483647, -2147483648, 65535})
    void testValidNums(int num){
        try{
            assertEquals(num, this.setGetField(num));
        } catch(Exception e){
            fail();
        }
    }

    /**
     * Factory method for calling the appropriate function you want to test for signed ints validity
     * @param num signed int to test
     * @return the result of a getField on the respective object
     * @throws Exception if something goes wrong
     */
    protected abstract int setGetField(int num) throws Exception;
}
