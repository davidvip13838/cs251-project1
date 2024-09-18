package DronesTest;

import Drones.CleanSwordManager;
import Drones.CleanSwordManagerInterface.CleanSwordTimes;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.params.provider.Arguments.arguments;

/**
 * Tests CleanSwordManager extensively
 */
@Timeout(value = 9000, unit = TimeUnit.MILLISECONDS)
public class CleanSwordManagerTest {
    final static String prefix = "./test/DronesTest/cleanSwordFiles/";
    final static File[] folderPaths = {new File(prefix + "sample"),
            new File(prefix + "manual")};
    final static String inputSuffix = "in", ansSuffix = "out";

    /**
     * All done in one instance of the manager
     */
    CleanSwordManager manager = new CleanSwordManager();

    /**
     * Provides a list of test files and their names for the parameterized test below.
     *
     * @return List of valid test input files and their names
     */
    static Stream<Arguments> testFileProvider() {
        ArrayList<Arguments> args = new ArrayList<>();
        //for all folders provided
        for (final File path : folderPaths) {
            //for each file in each folder
            for (final File entry : Objects.requireNonNull(path.listFiles())) {
                String inputFile = entry.getPath();
                //if not an input file, skip
                if (!(inputFile.substring(inputFile.length() - inputSuffix.length()).equalsIgnoreCase(inputSuffix))) {
                    continue;
                }
                args.add(arguments(Named.of(entry.getName(), entry)));
            }
        }

        return args.stream();
    }

    /**
     * Runs all input files
     */
    @DisplayName("File-based tests for Story 1")
    @ParameterizedTest(name = "{index}: {0}")
    @MethodSource("testFileProvider")
    void runFiles(File file) {
        String inputFile = file.getPath();

        //guaranteed to have a valid input file
        String ansFile = inputFile.substring(0, inputFile.length() - inputSuffix.length()) + ansSuffix;

        //run test
        ArrayList<CleanSwordTimes> ans = null;
        try {
            ans = manager.getCleaningTimes(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Error calling manager.getCleaningTimes(\"" + file.getName() + "\": " + e.getMessage());
        }

        //compare to answer
        //read in answer file
        BufferedReader bf = null;
        try {
            bf = new BufferedReader(new FileReader(ansFile));
        } catch (FileNotFoundException e) {
            fail("GRADER ERROR:: ANSWER FILE NOT FOUND:: \"" + file.getName() + "\"");
        }

        ArrayList<CleanSwordTimes> trueAns = new ArrayList<>();
        bf.lines().forEach((line) -> {
            //written out explicitly for clarity for future readers of this code
            String[] parsed = line.split(" ");
            long timeFilled = Long.parseLong(parsed[0]), delayToFilled = Long.parseLong(parsed[1]);
            trueAns.add(new CleanSwordTimes(timeFilled, delayToFilled));
        });

        //compare
        compareAnswer(trueAns, ans, "Test case: " + inputFile);
    }

    /**
     * Compares answer and prints detailed information if wrong
     *
     * @param trueAns  correct answer
     * @param ans      actual answer
     * @param testName name of test, to print if failed
     */
    private static void compareAnswer(ArrayList<CleanSwordTimes> trueAns, ArrayList<CleanSwordTimes> ans, String testName) {
        TestUtils.compareArraysWithEqual(trueAns, ans, testName);
    }
}
