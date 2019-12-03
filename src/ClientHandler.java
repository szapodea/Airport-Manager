
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Used for the server
 *
 * Sources of Help:
 * JASPER DAY
 *
 * @author Stephan Zapodeanu, Luke Bainbridge
 * @version December 3rd, 2019
 */

public class ClientHandler implements Runnable {

    private BufferedWriter bufferedWriter;
    //private BufferedReader bufferedReader;
    private Scanner scanner;
    private File file;
    private ObjectInputStream socketReader;
    private ObjectOutputStream socketWriter;
    private Delta delta;
    private Southwest southwest;
    private Alaska alaska;
    private ArrayList<String> list;

    public ClientHandler(Socket socket, Delta delta, Southwest southwest, Alaska alaska) throws NullPointerException {
        try {
            file = new File("reservations.txt");
            bufferedWriter = new BufferedWriter(new FileWriter(file, true));
            //bufferedReader = new BufferedReader((new FileReader(file)));
            scanner = new Scanner(file);
            socketWriter = new ObjectOutputStream(socket.getOutputStream());
            socketWriter.flush();
            socketReader = new ObjectInputStream(socket.getInputStream());
            this.delta = delta;
            this.southwest = southwest;
            this.alaska = alaska;
            list = new ArrayList<>();
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
            if (delta.getPassengers().size() == 0 && alaska.getPassengers().size() == 0 &&
                    southwest.getPassengers().size() == 0 && file.length() != 0) {
                readFile(file);
            }

            socketWriter.writeObject(delta);
            socketWriter.flush();
            socketWriter.writeObject(alaska);
            socketWriter.flush();
            socketWriter.writeObject(southwest);
            socketWriter.flush();

            Object checkAirline;
            //send deltata to the client for info about a full flight
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
                list.clear();
                String temp = "";
                while (scanner.hasNextLine()) {
                    temp = scanner.nextLine();
                    list.add(temp);
                }
                scanner.close();
                if (!list.contains("Delta Passenger List")) {
                    startFile(delta);
                }
                delta.addPassenger(newPassenger);
                delta.incrementPassengerCount();
                for (Passenger passenger : delta.getPassengers()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(passenger.getLastName().charAt(0) + ". " + passenger.getFirstName() +
                            ", " + passenger.getAge());
                    bufferedWriter.newLine();
                    bufferedWriter.write("-------------------DELTA");
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                scanner = new Scanner(file);
                list.clear();
                while (scanner.hasNextLine()) {
                    list.add(scanner.nextLine());
                }
                list.add(list.indexOf("DELTA") + 1,
                        delta.getPassengers().size() + "/" + delta.getCapacity());
                bufferedWriter.close();
                bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                for (String string : list) {
                    bufferedWriter.write(string);
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            } else if (airline instanceof Alaska) {
                list.clear();
                String temp = "";
                while (scanner.hasNextLine()) {
                    temp = scanner.nextLine();
                    list.add(temp);
                }
                scanner.close();
                if (!list.contains("Alaska Passenger List")) {
                    startFile(alaska);
                }
                alaska.addPassenger(newPassenger);
                alaska.incrementPassengerCount();
                for (Passenger passenger : alaska.getPassengers()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(passenger.getLastName().charAt(0) + ". " + passenger.getFirstName() +
                            ", " + passenger.getAge());
                    bufferedWriter.newLine();
                    bufferedWriter.write("-------------------ALASKA");
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                scanner = new Scanner(file);
                list.clear();
                while (scanner.hasNextLine()) {
                    list.add(scanner.nextLine());
                }
                list.add(list.indexOf("ALASKA") + 1,
                        alaska.getPassengers().size() + "/" + alaska.getCapacity());
                bufferedWriter.close();
                bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                for (String string : list) {
                    bufferedWriter.write(string);
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            } else if (airline instanceof Southwest) {
                list.clear();
                String temp = "";
                while (scanner.hasNextLine()) {
                    temp = scanner.nextLine();
                    list.add(temp);
                }
                scanner.close();
                if (!list.contains("Southwest Passenger List")) {
                    startFile(southwest);
                }
                southwest.addPassenger(newPassenger);
                southwest.incrementPassengerCount();
                for (Passenger passenger : southwest.getPassengers()) {
                    bufferedWriter.newLine();
                    bufferedWriter.write(passenger.getLastName().charAt(0) + ". " + passenger.getFirstName() +
                            ", " + passenger.getAge());
                    bufferedWriter.newLine();
                    bufferedWriter.write("-------------------SOUTHWEST");
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                scanner = new Scanner(file);
                list.clear();
                while (scanner.hasNextLine()) {
                    list.add(scanner.nextLine());
                }
                list.add(list.indexOf("SOUTHWEST") + 1,
                        southwest.getPassengers().size() + "/" + southwest.getCapacity());
                bufferedWriter.close();
                bufferedWriter = new BufferedWriter(new FileWriter(file, false));
                for (String string : list) {
                    bufferedWriter.write(string);
                    bufferedWriter.newLine();
                }
                bufferedWriter.newLine();
            }
            list.clear();
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }
            for (int i = 0; i < list.size() ; i++) {
                if (list.get(i).equals("EOF")) {
                    list.remove(i);
                }
            }
            bufferedWriter.write("EOF");
            bufferedWriter.flush();
            scanner.close();
            bufferedWriter.close();
            printFile(delta, southwest, alaska);

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

    public void readFile(File fileTemp) {
        if (fileTemp.length() == 0) {

        } else {
            list.clear();
            while (scanner.hasNextLine()) {
                list.add(scanner.nextLine());
            }

            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).contains("Delta Passenger List") && !list.get(i + 1).isEmpty()) {
                    String lastName = list.get(i + 1).substring(0, 1);
                    String firstName = list.get(i + 1).substring(3, list.get(i + 1).indexOf(","));
                    int age = Integer.parseInt(list.get(i + 1).substring(list.get(i + 1).indexOf(",") + 2));

                    Passenger passenger = new Passenger(firstName, lastName, age);
                    delta.addPassenger(passenger);
                }
                if (list.get(i).contains("-------------------DELTA") && !list.get(i + 1).isEmpty()) {
                    String lastName = list.get(i + 1).substring(0, 1);
                    String firstName = list.get(i + 1).substring(3, list.get(i + 1).indexOf(","));
                    int age = Integer.parseInt(list.get(i + 1).substring(list.get(i + 1).indexOf(",") + 2));

                    Passenger passenger = new Passenger(firstName, lastName, age);
                    delta.addPassenger(passenger);
                }
                if (list.get(i).contains("Southwest Passenger List") && !list.get(i + 1).isEmpty()) {
                    String lastName = list.get(i + 1).substring(0, 1);
                    String firstName = list.get(i + 1).substring(3, list.get(i + 1).indexOf(","));
                    int age = Integer.parseInt(list.get(i + 1).substring(list.get(i + 1).indexOf(",") + 2));

                    Passenger passenger = new Passenger(firstName, lastName, age);
                    southwest.addPassenger(passenger);
                }
                if (list.get(i).contains("-------------------SOUTHWEST") && !list.get(i + 1).isEmpty()) {
                    String lastName = list.get(i + 1).substring(0, 1);
                    String firstName = list.get(i + 1).substring(3, list.get(i + 1).indexOf(","));
                    int age = Integer.parseInt(list.get(i + 1).substring(list.get(i + 1).indexOf(",") + 2));

                    Passenger passenger = new Passenger(firstName, lastName, age);
                    southwest.addPassenger(passenger);
                }
                if (list.get(i).contains("Alaska Passenger List") && !list.get(i + 1).isEmpty()) {
                    String lastName = list.get(i + 1).substring(0, 1);
                    String firstName = list.get(i + 1).substring(3, list.get(i + 1).indexOf(","));
                    int age = Integer.parseInt(list.get(i + 1).substring(list.get(i + 1).indexOf(",") + 2));

                    Passenger passenger = new Passenger(firstName, lastName, age);
                    alaska.addPassenger(passenger);
                }
                if (list.get(i).contains("-------------------ALASKA") && !list.get(i + 1).isEmpty()) {
                    String lastName = list.get(i + 1).substring(0, 1);
                    String firstName = list.get(i + 1).substring(3, list.get(i + 1).indexOf(","));
                    int age = Integer.parseInt(list.get(i + 1).substring(list.get(i + 1).indexOf(",") + 2));

                    Passenger passenger = new Passenger(firstName, lastName, age);
                    alaska.addPassenger(passenger);
                }
            }
        }
    }
    /**
     * sets the categories of the new file, so passengers can then be filed underneath them when a ticket is bought.
     */
    public void startFile(Airline airline) {
        try {
            if (airline instanceof Delta) {
                bufferedWriter.write("DELTA");
                bufferedWriter.newLine();
                bufferedWriter.write(delta.getPassengers().size() + "/" + delta.getCapacity());
                bufferedWriter.newLine();
                bufferedWriter.write("Delta Passenger List");
                bufferedWriter.newLine();
            } else if (airline instanceof Alaska) {
                bufferedWriter.write("ALASKA");
                bufferedWriter.newLine();
                bufferedWriter.write(alaska.getPassengers().size() + "/" + alaska.getCapacity());
                bufferedWriter.newLine();
                bufferedWriter.write("Alaska Passenger List");
                bufferedWriter.newLine();
            } else if (airline instanceof Southwest) {
                bufferedWriter.write("SOUTHWEST");
                bufferedWriter.newLine();
                bufferedWriter.write(southwest.getPassengers().size() + "/" + southwest.getCapacity());
                bufferedWriter.newLine();
                bufferedWriter.write("Southwest Passenger List");
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printFile(Delta delta1, Southwest southwest1, Alaska alaska1) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file, false));
            startFile(delta1);
            for (int i = 0; i < delta1.getPassengers().size(); i++) {
                bufferedWriter.write(delta1.getPassengers().get(i).toString());
                bufferedWriter.newLine();
                bufferedWriter.write("-------------------DELTA");
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            startFile(southwest1);
            for (int i = 0; i < southwest1.getPassengers().size(); i++) {
                bufferedWriter.write(southwest1.getPassengers().get(i).toString());
                bufferedWriter.newLine();
                bufferedWriter.write("-------------------SOUTHWEST");
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            startFile(alaska1);
            for (int i = 0; i < alaska1.getPassengers().size(); i++) {
                bufferedWriter.write(alaska1.getPassengers().get(i).toString());
                bufferedWriter.newLine();
                bufferedWriter.write("-------------------ALASKA");
                bufferedWriter.newLine();
            }
            bufferedWriter.newLine();
            bufferedWriter.newLine();
            bufferedWriter.write("EOF");
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
