package Drones;

import java.util.ArrayList;

/**
 * Interface for the sword cleaning manager.
 * The implementing class should follow the specifications listed in the project description ("Story 1").
 *
 * You may only use java.util.List, java.util.ArrayList, and java.io.* from the standard library.
 * Any other containers used must be ones you created.
 */
public interface CleanSwordManagerInterface {
    /**
     * Class used to store and retrieve answers to Story 1 of Project 1
     */
    class CleanSwordTimes  {
        long timeFilled, timeToFulfill;
        //trivial constructor
        public CleanSwordTimes(long timeFilled, long timeToFulfill){
            this.timeFilled = timeFilled;
            this.timeToFulfill = timeToFulfill;
        }

        //default Intellij-generated equals function
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CleanSwordTimes that = (CleanSwordTimes) o;
            return timeFilled == that.timeFilled && timeToFulfill == that.timeToFulfill;
        }

        /**
         * To string method for debugging
         * @return string version of object
         */
        @Override
        public String toString() {
            return "CleanSwordTimes{" + "timeClean=" + timeFilled + ", timeToFulfill=" + timeToFulfill + '}';
        }
    }

    /**
     * Get the cleaning times as per the specifications.
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and times it took to fill them, as per the specifications
     */
    ArrayList<CleanSwordTimes> getCleaningTimes(String filename);
}
