package service;
import model.Customer;
import java.util.Collection;
import java.util.HashMap;


public class CustomerService
{
    private static final CustomerService CService = new CustomerService();
    private CustomerService() {}
    private final HashMap<String,Customer> map = new HashMap<>();
    public void addCustomer(String email, String firstName, String lastName)
    {
        if(map.containsKey(email))
        {
            throw new IllegalArgumentException("Given Email already exists!!");
        }
        Customer customer = new Customer(firstName,lastName,email);
        map.put(email,customer);

    }

    public Customer getCustomer(String customerEmail)
    {
      return map.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers()
    {
        return map.values();
    }

    public static CustomerService getCustomerObj()
    {
        return CService;
    }


}
