import java.util.ArrayList;

/**
 * A class that implements Airline.
 *
 * @author Luke Bainbridge, Stephan Zapodeanu
 * @version 11/16/19
 */

public class Alaska implements Airline {
    private ArrayList<String> passengers;

    /**
     * Constructs a Alaska airline object, initializes passenger ArrayList
     */
    Alaska() {
        passengers = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Alaska Airlines";
    }

    @Override
    public String getDescription() {
        return "Alaska Airlines is proud to serve the strong and " +
                "knowledgeable Boilermakers from Purdue University.\n" +
                "We primarily fly westward, and often have stops in Alaska and California.\n" +
                "We have first class amenities, even in coach class.\n" +
                "We provide fun snacks, such as pretzels and goldfish.\n" +
                "We also have comfortable seats, and free WiFi.\n" +
                "We hope you choose Alaska Airlines for your next itinerary.";
    }

    @Override
    public ArrayList getPassengers() {
        return passengers;
    }

    @Override
    public void addPassenger(String passenger) {
        passengers.add(passenger);
    }
}
