package application.workshop.api.dao;

import java.util.List;

import javax.ejb.Local;

import application.workshop.model.Customer;

@Local
public interface CustomerDAO {
	Customer findCustomer(Long id);
	List<Customer> findAllCustomers();
	Customer mergeCustomer(Customer customer);
	Customer persistCustomer(Customer customer);
	void removeCustomer(Customer customer);
}