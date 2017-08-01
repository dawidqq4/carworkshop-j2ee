package application.workshop.ejb.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import application.workshop.api.dao.CustomerOrderDAO;
import application.workshop.api.exception.CustomerOrderNotFoundException;
import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.model.CustomerOrder;

/**
 * Session Bean implementation class DefaultCustomerOrderManager
 */
@Stateless
public class DefaultCustomerOrderManager implements CustomerOrderManager {

	@EJB
	private CustomerOrderDAO customerOrderDAO;

	@Override
	public void removeCustomerOrder(CustomerOrder customerOrder) {
		customerOrderDAO.removeCustomerOrder(customerOrder);
	}

	@Override
	public CustomerOrder persistCustomerOrder(CustomerOrder customerOrder) {
		return customerOrderDAO.persistCustomerOrder(customerOrder);
	}

	@Override
	public CustomerOrder mergeCustomerOrder(CustomerOrder customerOrder) {
		return customerOrderDAO.mergeCustomerOrder(customerOrder);
	}

	@Override
	public CustomerOrder findCustomerOrder(Long id) throws CustomerOrderNotFoundException {
		CustomerOrder customerOrder = customerOrderDAO.findCustomerOrder(id);
		if (customerOrder == null)
			throw new CustomerOrderNotFoundException();
		return customerOrder;
	}

	@Override
	public List<CustomerOrder> findAllCustomerOrders() {
		return customerOrderDAO.findAllCustomerOrders();
	}
	
	@Override
	public List<CustomerOrder> findAllNewCustomerOrders(String status) {
		return customerOrderDAO.findAllNewCustomerOrders(status);
	}
	
	@Override
	public List<CustomerOrder> findAllWorkerCustomerOrders(String status, Long id) {
		return customerOrderDAO.findAllWorkerCustomerOrders(status, id);
	}
	
	@Override
	public List<CustomerOrder> findAllWorkerOrders(Long id) {
		return customerOrderDAO.findAllWorkerOrders(id);
	}
}
