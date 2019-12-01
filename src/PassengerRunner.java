import java.io.IOException;
import java.net.Socket;

public class PassengerRunner {
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
