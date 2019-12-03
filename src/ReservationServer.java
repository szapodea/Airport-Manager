import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * The server can handle multiple clients simultaneously. It will track and record ticket sales by writing
 * to reservations.txt. A sample reservations file can be found in the Starter Code folder.
 * Your program will need to be able to create and write to a file in a similar format.
 * Example: Client 1 and Client 2 are both connected to the server and booking a Southwest flight.
 * If Client 1 books the last Southwest ticket, Client 2 should no longer be able to book it.
 *
 * @author Stephan Zapodeanu, Luke Bainbridge
 * @version December 3rd, 2019
 */

public class ReservationServer {
    final private static int PORT = 1111;
    private static Delta delta = new Delta();
    private static Southwest southwest = new Southwest();
    private static Alaska alaska = new Alaska();
    private static BufferedReader bufferedReader;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server Started. Waiting connection to Port 1111");

            bufferedReader = new BufferedReader(new FileReader("reservations.txt"));
            ArrayList<String> reservationsList = new ArrayList<>();
            String temp = "";
            while ((temp = bufferedReader.readLine()) != null) {
                reservationsList.add(temp);
            }
            for (int i = 0; i < reservationsList.size(); i++) {
                if (reservationsList.get(i).equals("Delta passenger list")) {
                    i++;
                    while (!reservationsList.get(i).isEmpty()) {
                        String lastInitial = reservationsList.get(i).substring(0, 0);
                        String firstName = reservationsList.get(i).substring(2, reservationsList.get(i).indexOf(","));
                        int age = Integer.parseInt( reservationsList.get(i)
                                .substring(reservationsList.get(i).indexOf(",") + 1));
                        delta.addPassenger(new Passenger(firstName, lastInitial, age));
                        i += 2;
                    }
                } else if ((reservationsList.get(i).equals("Southwest passenger list"))) {
                    i++;
                    while (!reservationsList.get(i).isEmpty()) {
                        String lastInitial = reservationsList.get(i).substring(0, 0);
                        String firstName = reservationsList.get(i).substring(2, reservationsList.get(i).indexOf(","));
                        int age = Integer.parseInt( reservationsList.get(i)
                                .substring(reservationsList.get(i).indexOf(",") + 1));
                        southwest.addPassenger(new Passenger(firstName, lastInitial, age));
                        i += 2;
                    }
                } else if (reservationsList.get(i).equals("Alaska passenger list")) {
                    i++;
                    while (!reservationsList.get(i).isEmpty()) {
                        String lastInitial = reservationsList.get(i).substring(0, 0);
                        String firstName = reservationsList.get(i).substring(2, reservationsList.get(i).indexOf(","));
                        int age = Integer.parseInt( reservationsList.get(i)
                                .substring(reservationsList.get(i).indexOf(",") + 1));
                        alaska.addPassenger(new Passenger(firstName, lastInitial, age));
                        i += 2;
                    }
                }

            }

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    System.out.println("connected");
                    ClientHandler clientHandler = new ClientHandler(socket, delta, southwest, alaska);
                    Thread clientHandlerThread = new Thread(clientHandler);
                    clientHandlerThread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
