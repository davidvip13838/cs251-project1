package Drones;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class ItemRequestManager implements ItemRequestManagerInterface {

    @Override
    public ArrayList<ItemRetrievalTimes> getRetrievalTimes(String filename) {
        ArrayList<ItemRetrievalTimes> retrievalTimes = new ArrayList<>();
        Stack<InterruptedRequest> stack = new Stack<>();

        try {
            // Open the file and initialize the scanner
            Scanner scan = new Scanner(new FileReader(filename));

            // Read the number of requests (N) and the time T to the storage
            int N = scan.nextInt();
            int T = scan.nextInt();

            // Read the times at which requests are made
            int[] requestTimes = new int[N];
            for (int i = 0; i < N; i++) {
                requestTimes[i] = scan.nextInt();
            }

            // Variables to track the current status of the drone
            int currentTime = 0;  // Tracks the current time
            int currentTaskStartTime = 0; // Time when the current task started
            int distanceCovered = 0; // Distance covered when a task is interrupted
            int lastDeliveryTime = 0;  // Time when the last item was delivered

            // Loop through each request
            for (int i = 0; i < N; i++) {
                int requestTime = requestTimes[i];

                // If the request comes in after the current task is completed
                if (requestTime >= currentTime) {
                    currentTime = requestTime + 2 * T;  // Time to retrieve and deliver the current request
                    retrievalTimes.add(new ItemRetrievalTimes(i, currentTime));
                } else {
                    // Interrupt the current task
                    distanceCovered = (requestTime - currentTaskStartTime) / T;  // Calculate how far the drone has traveled
                    stack.push(new InterruptedRequest(i - 1, T - distanceCovered));  // Push the interrupted task to stack

                    // Process the new request
                    currentTime = requestTime + T;  // Time to travel to storage
                    currentTime += T;  // Time to retrieve and deliver the new item
                    retrievalTimes.add(new ItemRetrievalTimes(i, currentTime));

                    // Resume interrupted tasks from the stack
                    while (!stack.isEmpty()) {
                        InterruptedRequest interruptedRequest = stack.pop();
                        currentTime += interruptedRequest.remainingTime * T;  // Resume the interrupted task and complete it
                        retrievalTimes.add(new ItemRetrievalTimes(interruptedRequest.index, currentTime));
                    }
                }

                // Update tracking variables
                currentTaskStartTime = currentTime;
                lastDeliveryTime = currentTime;
            }

            scan.close();  // Close the scanner

        } catch (IOException e) {
            // Handle file input errors
            System.err.println("ATTENTION TAs: Couldn't find test file: \"" + filename + "\":: " + e.getMessage());
            System.exit(1);
        }

        return retrievalTimes;
    }

    // Inner class to store the interrupted request information
    private static class InterruptedRequest {
        int index;
        int remainingTime;  // Time to complete the retrieval after the interruption

        InterruptedRequest(int index, int remainingTime) {
            this.index = index;
            this.remainingTime = remainingTime;
        }
    }
}
