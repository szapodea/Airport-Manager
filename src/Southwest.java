import java.util.ArrayList;

/**
 * A class that implements Airline.
 *
 * @author Luke Bainbridge
 * @version 11/16/19
 */

public class Southwest implements Airline {
    private ArrayList<Passenger> passengers;

    /**
     * Constructs a Southwest airline object, initializes passenger ArrayList
     */
    Southwest() {
        passengers = new ArrayList<>();
    }

    @Override
    public String getName() {
        return "Southwest Airlines";
    }

    @Override
    public char getTerminal() {
        return 'B';
    }

    @Override
    public String getDescription() {
        return "Southwest Airlines is proud to offer flights to Purdue University.\n" +
                "We are happy to offer free in flight wifi, as well as our amazing snacks.\n" +
                "In addition, we offer flights for much cheaper than other airlines, " +
                "and offer two free checked bags.\n" +
                "We hope you choose Southwest for your next flight.";
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
