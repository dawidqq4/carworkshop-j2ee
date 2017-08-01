package application.workshop.ejb.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import application.workshop.api.dao.CustomerDAO;
import application.workshop.api.exception.CustomerNotFoundException;
import application.workshop.api.manager.CustomerManager;
import application.workshop.model.Customer;

/**
 * Session Bean implementation class DefaultCustomerManager
 */
@Stateless
public class DefaultCustomerManager implements CustomerManager {

	@EJB
	private CustomerDAO customerDAO;

	@Override
	public void removeCustomer(Customer customer) {
		customerDAO.removeCustomer(customer);
	}

	@Override
	public Customer persistCustomer(Customer customer) {
		return customerDAO.persistCustomer(customer);
	}

	@Override
	public Customer mergeCustomer(Customer customer) {
		return customerDAO.mergeCustomer(customer);
	}

	@Override
	public Customer findCustomer(Long id) throws CustomerNotFoundException {
		Customer customer = customerDAO.findCustomer(id);
		if (customer == null)
			throw new CustomerNotFoundException();
		return customer;
	}

	@Override
	public List<Customer> findAllCustomers() {
		return customerDAO.findAllCustomers();
	}

}
