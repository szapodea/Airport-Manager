import javafx.util.Pair;

import java.io.*;
import java.net.Socket;

/**
 * Used for the server
 *
 * Sources of Help:
 * JASPER DAY
 *
 * @author Stephan Zapodeanu
 * @version November 8th, 2019
 */

public class ClientHandler implements Runnable {

    private BufferedWriter bufferedWriter;

    private ObjectInputStream socketReader;
    private ObjectOutputStream socketWriter;
    private Delta delta;
    private Southwest southwest;
    private Alaska alaska;

    public ClientHandler(Socket socket, Delta delta, Southwest southwest, Alaska alaska) throws NullPointerException {
        try {

            bufferedWriter = new BufferedWriter(new FileWriter("reservations.txt", true));
            socketWriter = new ObjectOutputStream(socket.getOutputStream());
            socketWriter.flush();
            socketReader = new ObjectInputStream(socket.getInputStream());
            this.delta = delta;
            this.southwest = southwest;
            this.alaska = alaska;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Where to implement the method
     */
    @Override
    public void run() {
        try {
            socketWriter.writeObject(delta);
            socketWriter.flush();
            socketWriter.writeObject(alaska);
            socketWriter.flush();
            socketWriter.writeObject(southwest);
            socketWriter.flush();

            Object checkAirline;
            //send data to the client for info about a full flight
            while (true) {
                checkAirline = socketReader.readObject();
                if (checkAirline instanceof String) {
                    break;
                } else if (checkAirline instanceof Delta) {
                    socketWriter.writeObject(delta);
                    socketWriter.flush();
                } else if (checkAirline instanceof Alaska) {
                    socketWriter.writeObject(alaska);
                    socketWriter.flush();
                } else if (checkAirline instanceof Southwest) {
                    socketWriter.writeObject(southwest);
                    socketWriter.flush();
                }
            }

            Airline airline = (Airline) socketReader.readObject();
            Passenger newPassenger = (Passenger) socketReader.readObject();

            if (airline instanceof Delta) {
                delta.addPassenger(newPassenger);
                delta.incrementPassengerCount();
                bufferedWriter.write("Delta");
                bufferedWriter.newLine();
                bufferedWriter.write(delta.getPassengers().size() + "/" + delta.getCapacity());
                bufferedWriter.newLine();
                bufferedWriter.write("Delta Passenger List");
                for (Passenger passenger : delta.getPassengers()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(passenger.getLastName().charAt(0) + ". " + passenger.getFirstName() +
                            ", " + passenger.getAge());
                    bufferedWriter.newLine();
                    bufferedWriter.write("-------------------DELTA");
                }
                bufferedWriter.newLine();
            } else if (airline instanceof Alaska) {
                alaska.addPassenger(newPassenger);
                alaska.incrementPassengerCount();
                bufferedWriter.write("Alaska");
                bufferedWriter.newLine();
                bufferedWriter.write(alaska.getPassengers().size() + "/" + alaska.getCapacity());
                bufferedWriter.newLine();
                bufferedWriter.write("Alaska Passenger List");
                for (Passenger passenger : alaska.getPassengers()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(passenger.getLastName().charAt(0) + ". " + passenger.getFirstName() +
                            ", " + passenger.getAge());
                    bufferedWriter.newLine();
                    bufferedWriter.write("-------------------Alaska");
                }
                bufferedWriter.newLine();
            } else if (airline instanceof Southwest) {
                southwest.addPassenger(newPassenger);
                southwest.incrementPassengerCount();
                bufferedWriter.write("Southwest");
                bufferedWriter.newLine();
                bufferedWriter.write(southwest.getPassengers().size() + "/" + southwest.getCapacity());
                bufferedWriter.newLine();
                bufferedWriter.write("Southwest Passenger List");
                for (Passenger passenger : southwest.getPassengers()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(passenger.getLastName().charAt(0) + ". " + passenger.getFirstName() +
                            ", " + passenger.getAge());
                    bufferedWriter.newLine();
                    bufferedWriter.write("-------------------Southwest");
                }
                bufferedWriter.newLine();
            }
            bufferedWriter.write("EOF");
            bufferedWriter.close();

            while (true) {
                Airline refreshAirline = (Airline) socketReader.readObject();

                if (refreshAirline instanceof Delta) {
                    socketWriter.writeObject(delta);
                    socketWriter.flush();
                } else if (refreshAirline instanceof Alaska) {
                    socketWriter.writeObject(alaska);
                    socketWriter.flush();
                } else if (refreshAirline instanceof Southwest) {
                    socketWriter.writeObject(southwest);
                    socketWriter.flush();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
