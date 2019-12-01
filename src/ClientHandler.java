import java.io.IOException;
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

public class ClientHandler {
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
