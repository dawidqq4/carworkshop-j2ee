package application.workshop.api.dao;

import java.util.List;

import javax.ejb.Local;

import application.workshop.model.CustomerOrder;

@Local
public interface CustomerOrderDAO {
	CustomerOrder findCustomerOrder(Long id);
	List<CustomerOrder> findAllCustomerOrders();
	List<CustomerOrder> findAllNewCustomerOrders(String status);
	List<CustomerOrder> findAllWorkerCustomerOrders(String status, Long id);
	List<CustomerOrder> findAllWorkerOrders(Long id);
	CustomerOrder mergeCustomerOrder(CustomerOrder customerOrder);
	CustomerOrder persistCustomerOrder(CustomerOrder customerOrder);
	void removeCustomerOrder(CustomerOrder customerOrder);
}