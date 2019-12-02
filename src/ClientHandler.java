import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
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

    public ClientHandler(Socket socket) throws NullPointerException {
        this.socket = socket;
    }

    /**
     * Where to implement the method
     */

    @Override
    public void run() {

        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            //objectInputStream.close();
            //objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        ReservationServer server;
        Socket socket = new Socket();
        try {
            server = new ReservationServer(socket);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.serveClients();
    }


}
