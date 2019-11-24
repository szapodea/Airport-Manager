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
        return "<html><center>Delta Airlines is proud to be one of " +
                "the five premier Airlines at Purdue University.<br/>" +
                "We are offer extremely exceptional services, with free limited WiFi for all customers.<br/>" +
                "Passengers who use T-Mobile as a cell phone carrier get addition benefits.<br/>" +
                "We are also happy to offer power outlets in each seat for passenger use.<br/>" +
                "We hope you choose to fly Delta as your next Airline.</center></html>";
    }

    @Override
    public int getCapacity() {
        return 200;
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
