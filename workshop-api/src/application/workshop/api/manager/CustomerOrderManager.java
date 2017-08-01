package application.workshop.api.manager;

import java.util.List;

import javax.ejb.Remote;

import application.workshop.api.exception.CustomerOrderNotFoundException;
import application.workshop.model.CustomerOrder;

@Remote
public interface CustomerOrderManager {
	List<CustomerOrder> findAllCustomerOrders();
	List<CustomerOrder> findAllNewCustomerOrders(String status);
	List<CustomerOrder> findAllWorkerCustomerOrders(String status, Long id);
	List<CustomerOrder> findAllWorkerOrders(Long id);
	CustomerOrder findCustomerOrder(Long id) throws CustomerOrderNotFoundException;
	CustomerOrder mergeCustomerOrder(CustomerOrder customerOrder);
	CustomerOrder persistCustomerOrder(CustomerOrder customerOrder);
	void removeCustomerOrder(CustomerOrder customerOrder);
}
