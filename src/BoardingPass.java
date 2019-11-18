/**
 * The Boarding Pass is created for a specific passenger, airline, and gate. When printed,
 * it should contain the airline’s name, passenger’s first and last names, the passenger’s age, and the gate.
 *
 * @author Stephan Zapodeanu
 * @version 11/16/19
 */

public class BoardingPass {
    private Passenger passenger;
    private Gate gate;
    private Airline airline;

    public BoardingPass(Passenger passenger, Gate gate, Airline airline) {
        this.passenger = passenger;
        this.gate = gate;
        this.airline = airline;
    }

    public void printInfo() {
        System.out.println("--------------------------------------------------------------------");
        System.out.printf("BOARDING PASS for FLIGHT 18000 WITH %s \n", airline.getName());
        System.out.printf("PASSENGER FIRST NAME: %s \n", passenger.getFirstName());
        System.out.printf("PASSENGER LAST NAME: %s \n", passenger.getLastName());
        System.out.printf("PASSENGER AGE: %d \n", passenger.getAge());
        System.out.printf("You can now begin boarding at gate %s \n", gate.getGate());
        System.out.println("--------------------------------------------------------------------");

    }
}
