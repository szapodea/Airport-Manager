import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
                    //JFrame and JPanels
                    JFrame frame = new JFrame("Purdue University Flight Registration System");
                    JPanel topPanel = new JPanel();
                    JPanel middlePanel = new JPanel();
                    middlePanel.setLayout(new BorderLayout());
                    JPanel bottomPanel = new JPanel();

                    //JLabels
                    JPanel comboBoxJPanel = new JPanel();
                    comboBoxJPanel.setLayout(new BorderLayout());
                    comboBoxJPanel.add(new JLabel("                                                   " +
                            "                     "), BorderLayout.EAST); //padding east
                    comboBoxJPanel.add(new JLabel("                                                   " +
                            "                     "), BorderLayout.WEST); //padding west
                    JLabel topLabel = new JLabel("<html>Welcome to the Purdue University " +
                            "Airline Reservation Management System!</html>");
                    Airline airline = new Delta();
                    JLabel middleLabel = new JLabel(airline.getDescription(), SwingConstants.CENTER);
                    JLabel logoLabel = new JLabel(new ImageIcon(logo));

                    //set fonts
                    topLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    middleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    //JButtons
                    JButton exitButton = new JButton("Exit");
                    JButton bookFlightButton = new JButton("Book a flight");;
                    JButton confirmBookFlightButton = new JButton("Yes, I want to book a flight.");
                    JButton chooseFlightButton = new JButton("Choose this flight");
                    String[] flights = {"Delta", "Southwest", "Alaska"};
                    JComboBox<String> chooseFlightComboBox = new JComboBox<>(flights);
                    comboBoxJPanel.add(chooseFlightComboBox, BorderLayout.CENTER);

                    //add starting items to the panels
                    topPanel.add(topLabel, BorderLayout.CENTER);
                    middlePanel.add(logoLabel);
                    bottomPanel.add(exitButton);
                    bottomPanel.add(bookFlightButton);

                    //key listener for backslash
                    KeyAdapter backslashListener = new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {

                            }
                        }
                    };

                    //removes the frame if the exit button is pressed
                    exitButton.addActionListener(actionEvent -> {
                        frame.dispose();
                    });

                    //Confirms that the user wants to book a flight
                    bookFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        middlePanel.remove(logoLabel);
                        bottomPanel.remove(bookFlightButton);

                        //add confirm book flight text
                        topLabel.setText("<html>Do you want to book a flight today?</html>");

                        //add a confirmation button
                        bottomPanel.add(confirmBookFlightButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    //prompts the user to choose a flight
                    confirmBookFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        bottomPanel.remove(confirmBookFlightButton);

                        //add listener for backslash
                        frame.addKeyListener(backslashListener);

                        //add choose flight text
                        topLabel.setText("<html>Choose a flight from the drop down menu.</html>");

                        //add flights
                        middlePanel.add(comboBoxJPanel, BorderLayout.NORTH);
                        middlePanel.add(middleLabel, BorderLayout.CENTER);

                        //add choose flight button
                        bottomPanel.add(chooseFlightButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    //changes the description for the chosen flight
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

                    //
                    chooseFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        frame.removeKeyListener(backslashListener);
                    });

                    //frame setup
                    frame.setFocusable(true);
                    frame.setLayout(new BorderLayout());
                    frame.add(topPanel, BorderLayout.NORTH);
                    frame.add(middlePanel, BorderLayout.CENTER);
                    frame.add(bottomPanel, BorderLayout.SOUTH);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    frame.setSize(700, 400);
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
