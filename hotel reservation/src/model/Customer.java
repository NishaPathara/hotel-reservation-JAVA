package model;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Customer class with first name,last name,and email
 *
 * @author NishaPathara
 */
public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String emailRegex = "^(.+)@(.+).(.+)$";
    private final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName,String lastName,String email){
        this.firstName=firstName;
        this.lastName=lastName;
        if (!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid Email!");
        }
        this.email=email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return "First Name: " + firstName + "\nLast Name: " + lastName + "\nEmail id: " + email;
    }
    @Override
    public boolean equals(Object obj) {

        if (obj == this) return true;
        if (!(obj instanceof Customer)) {
            return false;
        }
        Customer customer = (Customer) obj;
        return Objects.equals(firstName, customer.firstName) &&
                Objects.equals(lastName, customer.lastName) &&
                Objects.equals(email, customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
