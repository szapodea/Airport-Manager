import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

/**
 * Connects to the Server and allows the user to book tickets using a GUI.
 * See the Sample Output Video for examples of how it should appear.
 *
 * Client requests data from the server in the following way:
 * 1. expects server to write out airlines in the order Delta, Alaska, Southwest
 * 2. client writes the airline that was selected with exactly 1 additional passenger
 * 3. client will write an airlines name (the same airline that was written in 2) to notify the server that it needs
 * updated data on that airline (this is for the refresh button)
 * 4. expects server to write out the airline object of the name that was sent with updated information
 *
 * @author Luke Bainbridge, Stephan Zapodeanu
 * @version December 3rd, 2019
 */

public class ReservationClient {
    private static Airline selectedAirline;
    private static Gate gate;
    private static BoardingPass boardingPass;
    private static Passenger passenger;

    /**
     * Main method to start the client
     * @param args Arguments for the client
     */
    public static void main(String[] args) {
        //variables for the server
        ObjectOutputStream socketWriter;
        ObjectInputStream socketReader;
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
            socket = new Socket(hostname, port);
            socketWriter = new ObjectOutputStream(socket.getOutputStream());
            socketWriter.flush();
            socketReader = new ObjectInputStream(socket.getInputStream());

            //get the airlines from the server
            Delta delta = (Delta) socketReader.readObject();
            Alaska alaska = (Alaska) socketReader.readObject();
            Southwest southwest = (Southwest) socketReader.readObject();

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
                    bottomPanel.setLayout(new BorderLayout());
                    JPanel buttonPanel = new JPanel();

                    //JLabels
                    JPanel comboBoxJPanel = new JPanel();
                    comboBoxJPanel.setLayout(new BorderLayout());
                    comboBoxJPanel.add(new JLabel("                                                   " +
                            "                     "), BorderLayout.EAST); //padding east
                    comboBoxJPanel.add(new JLabel("                                                   " +
                            "                     "), BorderLayout.WEST); //padding west
                    JLabel topLabel = new JLabel("<html>Welcome to the Purdue University " +
                            "Airline Reservation Management System!</html>");
                    selectedAirline = delta; //set selected airline to delta by default
                    JLabel middleLabel = new JLabel(selectedAirline.getDescription(), SwingConstants.CENTER);
                    JLabel logoLabel = new JLabel(new ImageIcon(logo));
                    JLabel passengerInfo = new JLabel();

                    //set fonts
                    topLabel.setFont(new Font("Arial", Font.BOLD, 16));
                    middleLabel.setFont(new Font("Arial", Font.PLAIN, 14));

                    //JButtons
                    JButton exitButton = new JButton("Exit");
                    JButton bookFlightButton = new JButton("Book a flight");
                    JButton goToChooseFlightButton = new JButton();
                    JButton chooseFlightButton = new JButton("Choose this flight");
                    String[] flights = {"Delta", "Southwest", "Alaska"};
                    JComboBox<String> chooseFlightComboBox = new JComboBox<>(flights);
                    comboBoxJPanel.add(chooseFlightComboBox, BorderLayout.CENTER);
                    JButton confirmFlightButton = new JButton("Yes, I want this flight");
                    JButton submitButton = new JButton("Next");
                    JButton yesConfirmInfoButton = new JButton("Yes");
                    JButton refreshButton = new JButton("Refresh Flight Status");

                    //JTextFields
                    JTextArea firstName = new JTextArea(1, 50);
                    JTextArea lastName = new JTextArea(1, 50 );
                    JTextArea age = new JTextArea(1, 50);

                    //add starting items to the panels
                    topPanel.add(topLabel, BorderLayout.CENTER);
                    middlePanel.add(logoLabel);
                    bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
                    buttonPanel.add(exitButton);
                    buttonPanel.add(bookFlightButton);


                    //shows passenger info on backslash press
                    KeyAdapter backslashListener = new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                            if (e.getKeyCode() == KeyEvent.VK_BACK_SLASH) {
                                JFrame info = new JFrame();
                                //top text
                                info.add(new JLabel(selectedAirline.getName() + ". Capacity: " +
                                                selectedAirline.getPassengers().size() + "/"
                                                + selectedAirline.getCapacity() + " Passengers"),
                                        BorderLayout.NORTH);
                                //passenger limited info
                                JPanel passengerText = new JPanel();
                                passengerText.add(new JLabel(selectedAirline.getPassengersLimited()),
                                        BorderLayout.CENTER);
                                info.add(new JScrollPane(passengerText), BorderLayout.CENTER);
                                //exit button
                                JPanel exitPanel = new JPanel();
                                JButton exit = new JButton("Exit");
                                exit.addActionListener(actionEvent -> {
                                    info.dispose();
                                });
                                exitPanel.add(exit);
                                info.add(exitPanel, BorderLayout.SOUTH);
                                //add listener for ESC pressed
                                info.addKeyListener(new KeyAdapter() {
                                    @Override
                                    public void keyPressed(KeyEvent e) {
                                        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                                            info.dispose();
                                        }
                                    }
                                });

                                //frame setup
                                info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                info.setFocusable(true);
                                info.setResizable(false);
                                info.setSize(350, 200);
                                info.setLocationRelativeTo(null);
                                info.setVisible(true);
                            }
                        }
                    };

                    //removes the frame if the exit button is pressed
                    exitButton.addActionListener(actionEvent -> {
                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "Thank for using Purdue University Airline " +
                                "Management System!", "Thank you!", JOptionPane.PLAIN_MESSAGE);
                    });

                    //Confirms that the user wants to book a flight
                    bookFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        middlePanel.remove(logoLabel);
                        buttonPanel.remove(bookFlightButton);

                        //add confirm book flight text
                        topLabel.setText("<html>Do you want to book a flight today?</html>");

                        //add a confirmation button
                        goToChooseFlightButton.setText("Yes, I want to book a flight.");
                        buttonPanel.add(goToChooseFlightButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    //prompts the user to choose a flight
                    goToChooseFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        buttonPanel.remove(goToChooseFlightButton);

                        //these items are for if the user comes to this screen from the confirm screen
                        middlePanel.removeAll();
                        buttonPanel.remove(confirmFlightButton);

                        //add listener for backslash
                        frame.addKeyListener(backslashListener);

                        //add choose flight text
                        topLabel.setText("<html>Choose a flight from the drop down menu.</html>");

                        //add flights
                        middlePanel.add(comboBoxJPanel, BorderLayout.NORTH);
                        middlePanel.add(middleLabel, BorderLayout.CENTER);

                        //add choose flight button
                        buttonPanel.add(chooseFlightButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    //changes the description for the chosen flight
                    chooseFlightComboBox.addActionListener(actionEvent -> {
                        switch (Objects.requireNonNull((String) chooseFlightComboBox.getSelectedItem())) {
                            case "Delta":
                                selectedAirline = delta;
                                break;
                            case "Southwest":
                                selectedAirline = southwest;
                                break;
                            case "Alaska":
                                selectedAirline = alaska;
                                break;
                        }
                        frame.requestFocus();
                        middleLabel.setText(selectedAirline.getDescription());
                        frame.repaint();
                    });

                    //confirms the user wants to book the flight/tells the user they cannot book a flight
                    chooseFlightButton.addActionListener(actionEvent -> {
                        //get updated data on the passengers
                        try {
                            //request data from the server
                            socketWriter.writeObject(selectedAirline);
                            socketWriter.flush();
                            //receive data from the server
                            selectedAirline = (Airline) socketReader.readObject();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                        if (selectedAirline.getCapacity() <= selectedAirline.getPassengerCount()) {
                            JOptionPane.showMessageDialog(null, "That airline is full.",
                                    "Max capacity", JOptionPane.ERROR_MESSAGE);
                        } else {
                            //increment the passenger count
                            selectedAirline.incrementPassengerCount();

                            //remove some frame items
                            middlePanel.removeAll();
                            buttonPanel.remove(chooseFlightButton);

                            //set the top text
                            topLabel.setText("<html>Are you sure you want to book a flight on <br/>" +
                                    selectedAirline.getName() + "?</html>");

                            //add confirmation buttons
                            goToChooseFlightButton.setText("No, I want a different flight.");
                            buttonPanel.add(goToChooseFlightButton);
                            buttonPanel.add(confirmFlightButton);
                        }
                    });

                    //prompts the user to input their information
                    confirmFlightButton.addActionListener(actionEvent -> {
                        //remove some frame items
                        frame.removeKeyListener(backslashListener);
                        buttonPanel.remove(goToChooseFlightButton);
                        buttonPanel.remove(confirmFlightButton);
                        middlePanel.removeAll();

                        //change top text
                        topLabel.setText("<html>Please input your information below.</html>");

                        middlePanel.setLayout(new FlowLayout());

                        //add fields to input
                        middlePanel.add(new JLabel("<html>What is your first name?</html>"));
                        middlePanel.add(firstName);
                        middlePanel.add(new JLabel("<html><br/>What is your last name?</html>"));
                        middlePanel.add(lastName);
                        middlePanel.add(new JLabel("<html><br/>What is your age?</html>"));
                        middlePanel.add(age);

                        //add the submit button
                        buttonPanel.add(submitButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    //asks the user if their information is correct
                    submitButton.addActionListener(actionEvent -> {
                        try {
                            //confirm the age is a positive integer
                            if (Integer.parseInt(age.getText()) < 0) {
                                throw new NumberFormatException();
                            }

                            //confirm first and last name have no special chars
                            boolean validNames = true;
                            for (int i = 0; i < firstName.getText().length(); i++) {
                                if (!(Character.isAlphabetic(firstName.getText().charAt(i)) ||
                                        firstName.getText().charAt(i) == '-')) {
                                    validNames = false;
                                }
                            }
                            for (int i = 0; i < lastName.getText().length(); i++) {
                                if (!(Character.isAlphabetic(lastName.getText().charAt(i)) ||
                                        lastName.getText().charAt(i) == '-')) {
                                    validNames = false;
                                }
                            }

                            if (validNames) {
                                JFrame confirm = new JFrame("Confirm Info");

                                //text to display to confirm their information
                                JLabel confirmText = new JLabel("<html>Are all the details you entered correct?" +
                                        "<br/>The passenger's name is " + firstName.getText() + " "
                                        + lastName.getText() + " and " +
                                        "their age is " + age.getText() + ".<br/>" +
                                        "If all the information shown is correct, select the Yes<br/>" +
                                        "button below, otherwise, select the No button.");

                                //Yes/No buttons
                                JPanel confirmButtons = new JPanel();
                                JButton noConfirmButton = new JButton("No");
                                confirmButtons.add(noConfirmButton);
                                confirmButtons.add(yesConfirmInfoButton);

                                //remove this frame on button press
                                noConfirmButton.addActionListener(actionEvent1 -> {
                                    confirm.dispose();
                                });
                                yesConfirmInfoButton.addActionListener(actionEvent1 -> {
                                    confirm.dispose();
                                });

                                //setup the frame
                                confirm.add(confirmText, BorderLayout.CENTER);
                                confirm.add(confirmButtons, BorderLayout.SOUTH);
                                confirm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                confirm.setResizable(false);
                                confirm.setSize(500, 200);
                                confirm.setLocationRelativeTo(null);
                                confirm.setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Please use only" +
                                        " letters or dashes for the name.",
                                        "Invalid age", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Please input a positive" +
                                    " integer for the age.", "Invalid age", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    //books the flight and shows flight info
                    yesConfirmInfoButton.addActionListener(actionEvent -> {
                        //create the passenger, boarding pass, and gate
                        passenger = new Passenger(firstName.getText(), lastName.getText(),
                                Integer.parseInt(age.getText()));
                        gate = new Gate(selectedAirline.getTerminal());
                        boardingPass = new BoardingPass(passenger, gate, selectedAirline);
                        selectedAirline.addPassenger(passenger);
                        try {
                            //tell the server to be ready to receive an airline
                            socketWriter.writeObject("stop");
                            socketWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //remove some frame items
                        middlePanel.removeAll();
                        buttonPanel.remove(submitButton);

                        //set middlePanel's layout
                        middlePanel.setLayout(new BorderLayout());

                        try {
                            //send the airline
                            socketWriter.writeObject(selectedAirline);
                            socketWriter.flush();
                            socketWriter.writeObject(passenger);
                            socketWriter.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //set the top text
                        topLabel.setText("<html>Flight data displaying for " + selectedAirline.getName() +
                                "<br/>Enjoy your flight!<br/>" +
                                "Flight is now boarding at " + gate.getGate());

                        //show passenger limited information
                        passengerInfo.setText("<html>Capacity: " + selectedAirline.getPassengers().size() + "/" +
                                selectedAirline.getCapacity() + " Passengers<br/>" +
                                selectedAirline.getPassengersLimited().substring(6)); //substring to remove html tag
                        //adds the scrollable panel
                        middlePanel.add(passengerInfo, BorderLayout.CENTER);
                        frame.add(new JScrollPane(middlePanel), BorderLayout.CENTER);

                        //show boarding pass
                        bottomPanel.add(new JLabel(boardingPass.printInfo()), BorderLayout.NORTH);

                        //add refresh button
                        buttonPanel.add(refreshButton);

                        //refresh the frame
                        frame.repaint();
                    });

                    refreshButton.addActionListener(actionEvent -> {
                        try {
                            //request data from the server
                            socketWriter.writeObject(selectedAirline);
                            socketWriter.flush();
                            //receive data from the server
                            selectedAirline = (Airline) socketReader.readObject();
                            //create new text
                            passengerInfo.setText("<html>Capacity: " + selectedAirline.getPassengers().size() + "/" +
                                    selectedAirline.getCapacity() + " Passengers<br/>" +
                                    selectedAirline.getPassengersLimited().substring(6)); //substring to remove html tag
                            //refresh the frame
                            frame.repaint();
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
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
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
