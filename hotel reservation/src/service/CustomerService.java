package service;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import model.Customer;


/**
 * customer service class
 *
 * @author Nisha Pathara
 */
public class CustomerService {

    Map<String,Customer> mapOfCustomer = new HashMap<String, Customer>();

    public void addCustomer(String email,String firstName,String lastName){
        Customer customer = new Customer(firstName, lastName, email);
        mapOfCustomer.put(email,customer);
    }

    public Customer getCustomer(String customerEmail){
        return mapOfCustomer.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        return mapOfCustomer.values();
    }

    //reference to the object of the class
    private static CustomerService customerInstance = new CustomerService() ;

    //private constructor that restricts creating objects outside of the class//
    private CustomerService(){
    }
    //access the object
    public static CustomerService getCustomerInstance(){
        return customerInstance;
    }

}
