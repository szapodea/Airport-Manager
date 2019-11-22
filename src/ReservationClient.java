import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

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
        //variables for the server
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
                    //variables for the GUI
                    JFrame frame = new JFrame("Purdue University Flight Registration System");
                    JPanel topPanel = new JPanel();
                    JPanel middlePanel = new JPanel();
                    JPanel bottomPanel = new JPanel();

                    JLabel topLabel = new JLabel("<html><b>Welcome to the Purdue University " +
                            "Airline Reservation Management System!</b></html>"); //initialize with welcome text
                    Airline airline = new Delta();
                    JLabel middleLabel = new JLabel(airline.getDescription());
                    JLabel logoLabel = new JLabel(new ImageIcon(logo));

                    JButton exitButton = new JButton("Exit");
                    JButton bookFlightButton = new JButton("Book a flight");;
                    JButton confirmBookFlightButton = new JButton("Yes, I want to book a flight.");
                    JButton chooseFlightButton = new JButton("Choose this flight");
                    String[] flights = {"Delta", "Southwest", "Alaska"};
                    JComboBox<String> chooseFlightComboBox = new JComboBox<>(flights);

                    //add starting items to the panels
                    topPanel.add(topLabel, BorderLayout.CENTER);
                    middlePanel.add(logoLabel);
                    bottomPanel.add(exitButton);
                    bottomPanel.add(bookFlightButton);

                    //exit button action listener
                    exitButton.addActionListener(actionEvent -> {
                        frame.dispose();
                    });;

                    //book flight button action listener
                    bookFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        middlePanel.remove(logoLabel);
                        bottomPanel.remove(bookFlightButton);

                        //confirm book flight text
                        topLabel.setText("<html><b>Do you want to book a flight today?</b></html>");

                        //add a confirmation button
                        bottomPanel.add(confirmBookFlightButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    //confirm book flight action listener
                    confirmBookFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        bottomPanel.remove(confirmBookFlightButton);

                        //choose flight text
                        topLabel.setText("<html><b>Choose a flight from the drop down menu.</b></html>");

                        //add flights
                        middlePanel.add(chooseFlightComboBox, BorderLayout.CENTER);
                        middlePanel.add(middleLabel, BorderLayout.CENTER);

                        //refresh the frame
                        frame.repaint();
                    });

                    chooseFlightComboBox.addActionListener(actionEvent -> {
                        Airline selectedAirline = new Delta();
                        switch (Objects.requireNonNull((String) chooseFlightComboBox.getSelectedItem())) {
                            case "Delta":
                                selectedAirline = new Delta();
                                break;
                            case "Southwest":
                                selectedAirline = new Southwest();
                                break;
                            case "Alaska":
                                selectedAirline = new Alaska();
                                break;
                        }
                        middleLabel.setText(selectedAirline.getDescription());
                        frame.repaint();
                    });

                    //frame setup
                    frame.add(topPanel, BorderLayout.NORTH);
                    frame.add(middlePanel, BorderLayout.CENTER);
                    frame.add(bottomPanel, BorderLayout.SOUTH);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(600, 400);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
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
}
