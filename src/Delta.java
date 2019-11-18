import java.util.ArrayList;

/**
 * A class that implements Airline.
 *
 * @author Luke Bainbridge
 * @version 11/17/19
 */

public class Delta implements Airline {
    private ArrayList<Passenger> passengers;

    /**
     * Constructs a Delta airline object, initializes passenger ArrayList
     */
    public Delta() {
        passengers = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Delta Airlines";
    }

    @Override
    public char getTerminal() {
        return 'C';
    }

    @Override
    public String getDescription() {
        return "Delta Airlines is proud to be one of the five premier Airlines at Purdue University.\n" +
                "We are offer extremely exceptional services, with free limited WiFi for all customers.\n" +
                "Passengers who use T-Mobile as a cell phone carrier get addition benefits.\n" +
                "We are also happy to offer power outlets in each seat for passenger use.\n" +
                "We hope you choose to fly Delta as your next Airline.";
    }

    @Override
    public ArrayList getPassengers() {
        return passengers;
    }

    @Override
    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }
}
