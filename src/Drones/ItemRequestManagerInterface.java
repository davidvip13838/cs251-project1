package Drones;

import java.util.ArrayList;

/**
 * Interface for the item retrieval manager.
 * The implementing class should follow the specifications listed in the project description ("Story 2").
 *
 * You may only use java.util.List, java.util.ArrayList, java.io.* and java.util.Scanner
 * from the standard library.  Any other containers used must be ones you created.
 */
public interface ItemRequestManagerInterface {
    /**
     * Class used to store and retrieve answers to Story 2 of Project 1
     */
    class ItemRetrievalTimes {
        int index;
        long timeFilled;
        //trivial constructor
        public ItemRetrievalTimes(int index, long timeFilled) { this.index = index; this.timeFilled = timeFilled; }

        //default Intellij-generated equals function
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ItemRetrievalTimes that = (ItemRetrievalTimes) o;
            return index == that.index && timeFilled == that.timeFilled;
        }

        /**
         * To string method for debugging
         * @return string version of object
         */
        @Override
        public String toString() {
            return "ItemRetrievalTimes{" + "index=" + index + ", timeFilled=" + timeFilled + '}';
        }

        //getters
        public int getIndex() { return index; }
        public long getTimeFilled() { return timeFilled; }
        //setters
        public void setTimeFilled(long timeFilled) { this.timeFilled = timeFilled; }
        // no setter for index because there should be no need to change it after construction of the object
    }

    /**
     * Get the retrieval times as per the specifications
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and index of the original request, per the specifications
     */
    ArrayList<ItemRetrievalTimes> getRetrievalTimes(String filename);
}
