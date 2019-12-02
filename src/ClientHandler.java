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
    ObjectInputStream socketReader;
    ObjectOutputStream socketWriter;
    Delta delta;
    Southwest southwest;
    Alaska alaska;

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
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            socketWriter.writeObject(delta);
            socketWriter.writeObject(alaska);
            socketWriter.writeObject(southwest);

            Airline airline;

            airline = (Airline) socketReader.readObject();
            //objectInputStream.close();
            //objectOutputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
