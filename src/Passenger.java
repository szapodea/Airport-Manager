import java.io.Serializable;

/**
 * A Passenger needs a first name, last name, and age.
 * Note: A passenger does not need a boarding pass when they are initially created,
 * but they should have one when they buy a ticket.
 *
 * @author Stephan Zapodeanu
 * @version 11/16/19
 */

public class Passenger implements Serializable {
    private String firstName;
    private String lastName;
    private int age;

    public Passenger(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
