import java.util.ArrayList;

/**
 * A class that implements Airline.
 *
 * @author Luke Bainbridge, Stephan Zapodeanu,
 * @version December 3rd, 2019
 */

public class Alaska implements Airline {
    private ArrayList<Passenger> passengers;
    private int passengerCount;

    /**
     * Constructs a Alaska airline object, initializes passenger ArrayList
     */
    public Alaska() {
        passengers = new ArrayList<>();
        passengerCount = 0;
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
        return "<html><center>Alaska Airlines is proud to serve the strong and " +
                "knowledgeable Boilermakers from Purdue University.<br/>" +
                "We primarily fly westward, and often have stops in Alaska and California.<br/>" +
                "We have first class amenities, even in coach class.<br/>" +
                "We provide fun snacks, such as pretzels and goldfish.<br/>" +
                "We also have comfortable seats, and free WiFi.<br/>" +
                "We hope you choose Alaska Airlines for your next itinerary.</center></html>";
    }

    @Override
    public int getCapacity() {
        return 100;
    }

    @Override
    public ArrayList<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengers.add(0, passenger);
    }

    @Override
    public void incrementPassengerCount() {
        passengerCount++;
    }

    @Override
    public int getPassengerCount() {
        return passengerCount;
    }
}
