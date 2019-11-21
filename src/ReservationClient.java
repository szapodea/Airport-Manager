import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

/**
 * Connects to the Server and allows the user to book tickets using a GUI.
 * See the Sample Output Video for examples of how it should appear.
 *
 * @author Luke Bainbridge
 * @version 11/16/19
 */

public class ReservationClient implements Runnable {
    /**
     * Main method to start the client
     * @param args Arguments for the client
     */
    public static void main(String[] args) {
        String hostname;
        int port;
        Socket socket;

        //get hostname
        hostname = JOptionPane.showInputDialog(null, "What is the hostname " +
                "you'd like to connect to?", "Hostname?", JOptionPane.PLAIN_MESSAGE);

        //get port
        try {
            port = Integer.parseInt(JOptionPane.showInputDialog(null, "What is the port " +
                    "you'd like to connect to?", "Hostname?", JOptionPane.PLAIN_MESSAGE));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The port must be a positive integer.",
                    "Invalid port number", JOptionPane.ERROR_MESSAGE);
            return;
        }

        //set socket
        try {
            socket = new Socket(hostname, port);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Can't connect to the server.",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    /**
     * A thread to run each client
     */
    public void run() {

    }
}
