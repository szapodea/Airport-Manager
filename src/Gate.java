import java.io.Serializable;
import java.util.Random;

/**
 * A class that implements Serializable. Use it to create Gates for flights in the form of [terminal][gate]
 * where terminal is a letter A,B,C and gate is a random number between 1 - 18, inclusive.
 * Note: Gates are associated with flights after the first passenger buys a ticket, and can
 * be deleted after the flight is full.
 *
 * @author Luke Bainbridge
 * @version 11/17/19
 */

public class Gate implements Serializable {
    private int number;
    private char terminal;

    /**
     * Constructs a gate with a random number and a specified terminal
     * @param terminal Char of specified terminal
     */
    public Gate(char terminal) {
        Random r = new Random();
        number = r.nextInt(18);
        this.terminal = terminal;
    }

    /**
     * Gives a String of the gate in the format [terminal][gate]
     * @return String of the gate
     */
    public String getGate() {
        return String.format("%s%d", terminal, number);
    }
}
