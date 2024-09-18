package DronesTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

/**
 * Stores a few useful functions not provided in the testing APIs used.
 */
public class TestUtils {
    /**
     * Compares two arrays.  Requires implementation of .equals and .toString on the object for it to make sense
     * @param trueAns correct answer
     * @param ans calculated answer
     * @param testName test name for error reporting
     */
    public static void compareArraysWithEqual(List<?> trueAns, List<?> ans, String testName) {
        String failurePrefix = "Test case \"" + testName + "\":: ";
        if(trueAns.size() != ans.size()){
            fail(failurePrefix + "Incorrect size, expected " + trueAns.size() + ", actual is " + ans.size());
        }

        for(int i=0; i<trueAns.size(); i++){
            if(! trueAns.get(i).equals(ans.get(i))){
                fail(failurePrefix + "\nIncorrect element at line " + (i+1)
                        + ",\n expected \"" + trueAns.get(i).toString() + "\",\n actual is \""
                        + ans.get(i) + "\"");
            }
        }
    }
}
