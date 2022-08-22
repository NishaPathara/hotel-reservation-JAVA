package model;

/**
 * driver for testing model classes
 * @author Nisha Pathara
 */
public class Tester {
    public static void main(String[] args){
        Customer customer1=new Customer("Nisha","Pathara","nyshaa.89@gmail.com");
        System.out.println(customer1);
        Customer customer2=new Customer("Nisha","Pathara","nyshaa.89");
        System.out.println(customer2);
    }
}
