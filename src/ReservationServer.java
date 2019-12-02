import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The server can handle multiple clients simultaneously. It will track and record ticket sales by writing
 * to reservations.txt. A sample reservations file can be found in the Starter Code folder.
 * Your program will need to be able to create and write to a file in a similar format.
 * Example: Client 1 and Client 2 are both connected to the server and booking a Southwest flight.
 * If Client 1 books the last Southwest ticket, Client 2 should no longer be able to book it.
 *
 * @author Stephan Zapodeanu
 * @version 11/16/19
 */

public class ReservationServer {
    final private static int port = 1111;
    private static Delta delta = new Delta();
    private static Southwest southwest = new Southwest();
    private static Alaska alaska = new Alaska();
    private int deltaCounter = 0;
    private int southWestCounter = 0;
    private int alaskaCounter = 0;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started. Waiting connection to Port 1111");
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
