import java.io.Serializable;
import java.util.ArrayList;

/**
 * An interface that extends Serializable.
 *
 * @author Luke Bainbridge, Stephan Zapodeanu
 * @version 11/16/19
 */

public interface Airline extends Serializable {
    /**
     * Gets the name of the airline
     * @return The airline's name
     */
    String getName();

    /**
     * Gets a description of the airline
     * @return The airline's description
     */
    String getDescription();

    /**
     * Gets an array of the passengers that are on the flight
     * @return The passengers names in the format "last initial. first, age"
     */
    ArrayList getPassengers();

    /**
     * Adds a passenger to the passenger array
     * @param passenger String of a passenger in the format "last initial. first, age"
     */
    void addPassenger(String passenger);
}
