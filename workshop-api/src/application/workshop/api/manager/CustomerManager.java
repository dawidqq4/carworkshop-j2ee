package application.workshop.api.manager;

import java.util.List;

import javax.ejb.Remote;

import application.workshop.api.exception.CustomerNotFoundException;
import application.workshop.model.Customer;

@Remote
public interface CustomerManager {
	List<Customer> findAllCustomers();
	Customer findCustomer(Long id) throws CustomerNotFoundException;
	Customer mergeCustomer(Customer customer);
	Customer persistCustomer(Customer customer);
	void removeCustomer(Customer customer);
}
