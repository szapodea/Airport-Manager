import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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
    private static int port = 1111;
    private Socket socket;
    Thread thread;


    public ReservationServer(Socket socket) throws IOException {
        this.socket = socket;
    }

    public void serveClients() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Server Started. Waiting connection to Port 1111");
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.flush();
                    PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
                    ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    break;
                }
                System.out.println("connected");
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread clientHandlerThread = new Thread(clientHandler);
                clientHandlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
