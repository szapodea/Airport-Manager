import java.io.Serializable;
import java.util.ArrayList;

/**
 * An interface that extends Serializable.
 *
 * @author Luke Bainbridge
 * @version 11/17/19
 */

public interface Airline extends Serializable {
    /**
     * Gets the name of the airline
     * @return The airline's name
     */
    String getName();

    /**
     * Gets the terminal of the airline
     * @return The terminal letter
     */
    char getTerminal();

    /**
     * Gets a description of the airline
     * @return The airline's description
     */
    String getDescription();

    /**
     * Gets the max capacity of the airline
     * @return The airline's max capacity
     */
    int getCapacity();

    /**
     * Gets an array of the passengers limited information that are on the flight
     * @return The passengers names in the format "last initial. first, age"
     */
    default String getPassengersLimited() {
        StringBuilder passengersLimited = new StringBuilder("<html>");
        for (int i = 0; i < getPassengers().size(); i++) {
            passengersLimited.append(getPassengers().get(i).getLastName().charAt(0)).append(". ").
                    append(getPassengers().get(i).getFirstName()).append(", ").
                    append(getPassengers().get(i).getAge()).append("<br/>");
        }
        passengersLimited.append("</html>");
        return passengersLimited.toString();
    }

    /**
     * Gets an array list of the passengers are on the flight
     * @return An array list of the passengers
     */
    ArrayList<Passenger> getPassengers();

    /**
     * Adds a passenger to the passenger array
     * @param passenger Passenger to add the the passenger array list
     */
    void addPassenger(Passenger passenger);
}
