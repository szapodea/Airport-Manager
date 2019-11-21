import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

/**
 * Connects to the Server and allows the user to book tickets using a GUI.
 * See the Sample Output Video for examples of how it should appear.
 *
 * @author Luke Bainbridge
 * @version 11/16/19
 */

public class ReservationClient {
    /**
     * Main method to start the client
     * @param args Arguments for the client
     */
    public static void main(String[] args) {
        BufferedWriter socketWriter;
        BufferedReader socketReader;
        String hostname;
        int port;
        Socket socket;

        //get hostname
        hostname = getHostname();

        //get port
        port = getPort();

        //Connect to the server and display the GUI
        try {
            //server connection
//            socket = new Socket(hostname, port);
//            socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            //load image
            BufferedImage logo = ImageIO.read(new File("logo.png"));

            //GUI
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    JFrame frame = new JFrame("Purdue University Flight Registration System");
                    welcomeSetup(frame, logo);
                }
            });
        } catch (IOException e) {
            if (e.getLocalizedMessage().equals(hostname)) {
                JOptionPane.showMessageDialog(null, "Could not connect to the server.",
                        "Connection error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, e.getLocalizedMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Displays a JOptionPane and gets input for a hostname
     * @return Hostname from user input
     */
    private static String getHostname() {
        String hostname = JOptionPane.showInputDialog(null, "What is the hostname " +
                "you'd like to connect to?", "Hostname?", JOptionPane.PLAIN_MESSAGE);
        if (hostname == null) {
            System.exit(0);
        }
        return hostname;
    }

    /**
     * Displays a JOptionPane and gets input for a port
     * @return Port from user input
     */
    private static int getPort() {
        int port = 0;
        try {
            String portstr = JOptionPane.showInputDialog(null, "What is the port " +
                    "you'd like to connect to?", "Port?", JOptionPane.PLAIN_MESSAGE);
            if (portstr == null) {
                System.exit(0);
            }
            port = Integer.parseInt(portstr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "The port must be a positive integer.",
                    "Invalid port number", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return port;
    }

    /**
     * Initializes the frame with welcome text, a logo, and buttons
     * @param frame The frame to initialize
     * @param logo BufferedImage logo to display
     */
    private static void welcomeSetup(JFrame frame, BufferedImage logo) {
        //welcome text
        JPanel topText = new JPanel();
        topText.add(new JLabel("Welcome to the Purdue University " +
                "Airline Reservation Management System!"), BorderLayout.CENTER);
        frame.add(topText, BorderLayout.NORTH);

        //logo
        JLabel logoLabel = new JLabel(new ImageIcon(logo));
        frame.add(new JLabel(new ImageIcon(logo)), BorderLayout.CENTER);

        //buttons
        JPanel buttons = new JPanel();
        //Exit button
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(actionEvent -> {
            frame.dispose();
        });
        buttons.add(exitButton);
        //Book flight button
        JButton bookFlightButton = new JButton("Book a flight");
        bookFlightButton.addActionListener(actionEvent -> {
            bookFlight(frame);
        });
        buttons.add(bookFlightButton);
        frame.add(buttons, BorderLayout.SOUTH);

        //frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void bookFlight(JFrame frame) {

    }
}
