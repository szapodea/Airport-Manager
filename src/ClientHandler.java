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

    private Socket socket;
    private ObjectInputStream socketReader;
    private ObjectOutputStream socketWriter;
    private Delta delta;
    private Southwest southwest;
    private Alaska alaska;

    public ClientHandler(Socket socket, Delta delta, Southwest southwest, Alaska alaska) throws NullPointerException {
        this.socket = socket;
        try {
            socketReader = new ObjectInputStream(socket.getInputStream());
            socketWriter = new ObjectOutputStream(socket.getOutputStream());
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
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("reservations.txt"));
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            socketWriter.writeObject(delta);
            socketWriter.writeObject(alaska);
            socketWriter.writeObject(southwest);

            Airline airline;

            airline = (Airline) socketReader.readObject();

            if (airline instanceof Delta) {
                delta = (Delta) airline;
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
                alaska = (Alaska) airline;
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
                southwest = (Southwest) airline;
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

            //objectInputStream.close();
            //objectOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
