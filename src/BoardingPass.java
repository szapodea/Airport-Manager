/**
 * The Boarding Pass is created for a specific passenger, airline, and gate. When printed,
 * it should contain the airline’s name, passenger’s first and last names, the passenger’s age, and the gate.
 *
 * @author Stephan Zapodeanu, Luke Bainbridge
 * @version December 3rd, 2019
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

    public String printInfo() {
        return ("<html>--------------------------------------------------------------------") +
        String.format("<br/>BOARDING PASS FOR FLIGHT 18000 WITH %s <br/>", airline.getName()) +
        String.format("PASSENGER FIRST NAME: %s <br/>", passenger.getFirstName()) +
        String.format("PASSENGER LAST NAME: %s <br/>", passenger.getLastName()) +
        String.format("PASSENGER AGE: %d <br/>", passenger.getAge()) +
        String.format("You can now begin boarding at gate %s <br/>", gate.getGate()) +
            ("--------------------------------------------------------------------</html>");

    }
}
