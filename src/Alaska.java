import java.util.ArrayList;

/**
 * A class that implements Airline.
 *
 * @author Luke Bainbridge
 * @version 11/17/19
 */

public class Alaska implements Airline {
    private ArrayList<Passenger> passengers;

    /**
     * Constructs a Alaska airline object, initializes passenger ArrayList
     */
    public Alaska() {
        passengers = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Alaska Airlines";
    }

    @Override
    public char getTerminal() {
        return 'A';
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
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }
}
