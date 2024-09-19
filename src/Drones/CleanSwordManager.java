package Drones;
import CommonUtils.BetterQueue;

import java.io.*;
import java.util.ArrayList;

/**
 * Manages everything regarding the cleaning of swords in our game.
 * Will be integrated with the other drone classes.
 *
 * You may only use java.util.List, java.util.ArrayList, and java.io.* from
 * the standard library.  Any other containers used must be ones you created.
 */
public class CleanSwordManager implements CleanSwordManagerInterface {
    /**
     * Gets the cleaning times per the specifications.
     *
     * @param filename file to read input from
     * @return the list of times requests were filled and times it took to fill them, as per the specifications
     */
    @Override
    public ArrayList<CleanSwordTimes> getCleaningTimes(String filename) {
        ArrayList<CleanSwordTimes> ans = new ArrayList<CleanSwordTimes>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            // Read the first line which contains n, m, and T
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]); // number of swords being cleaned
            int M = Integer.parseInt(firstLine[1]); // number of requests
            int T = Integer.parseInt(firstLine[2]); // time steps required to clean each sword


            // Read the next n lines to get the status of each sword
            int[] swordCleaningTimes = new int[N];
            for (int i = 0; i < N; i++) {
                swordCleaningTimes[i] = Integer.parseInt(br.readLine());
            }


            // Read the remaining m lines to get the requests
            int[] requestTimes = new int[M];
            for (int i = 0; i < M; i++) {
                requestTimes[i] = Integer.parseInt(br.readLine());
            }


            // After reading input, you can now implement any logic you need based on the sword statuses and requests
            // Always close the BufferedReader when done

            // intializing queues
            ArrayList<Integer> swords = new ArrayList<>();
            BetterQueue<Integer> request_sitting = new BetterQueue<Integer>();
            BetterQueue<Integer> request_running = new BetterQueue<Integer>();

            // add all existing swords
            for (int i = 0; i < N; i++) {
                swords.add(swordCleaningTimes[i]);
            }

            //add all request to requests sitting
            for (int i = 0; i < M; i++) {
                request_sitting.add(requestTimes[i]);
            }

            int time  = 0;
            while (!request_running.isEmpty() || !request_sitting.isEmpty()) {
                // add sitting request to running if its their time
                while (request_sitting.size() != 0 && request_sitting.peek() == time) {
                    request_running.add(request_sitting.remove());
                    swords.add(T);
                }
                // check if sword is done, if not decrement 1
                while (swords.get(0) == 0) {
                    if (request_running.isEmpty())  {
                        // find next non zero sword and decrement
                        for(int i = 0; i < swords.size(); i++) {
                            if (swords.get(i) != 0) {
                                swords.set(i, swords.get(i) - 1);
                                break;
                            }
                        }
                        break;
                    }
                    else {
                        ans.add(new CleanSwordTimes(time, time - request_running.remove()));
                        swords.remove(0);
                    }
                }
                if (swords.get(0) != 0) {
                    swords.set(0,swords.get(0) - 1);
                }



                // if sword done and running request is not empty then add to answer
                // increment time
                time = time + 1;
            }
        } catch (IOException e) {
            //This should never happen... uh oh o.o
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }
        return ans;
    }
}
