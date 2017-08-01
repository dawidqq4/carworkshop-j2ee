package application.workshop.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import application.workshop.api.dao.CustomerOrderDAO;
import application.workshop.model.CustomerOrder;

/**
 * Session Bean implementation class DefaultCustomerOrderDao
 */
@Stateless
public class DefaultCustomerOrderDao implements CustomerOrderDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public CustomerOrder findCustomerOrder(Long id) {
		return entityManager.find(CustomerOrder.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerOrder> findAllCustomerOrders() {
		return entityManager.createNamedQuery("CustomerOrder.findAllOrdered").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerOrder> findAllNewCustomerOrders(String status) {
		return entityManager.createNamedQuery("CustomerOrder.findAllNewOrdered").setParameter("status", status) .getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerOrder> findAllWorkerCustomerOrders(String status, Long id) {
		return entityManager.createNamedQuery("CustomerOrder.findWorkerVerificationOrdersOrdered")
				.setParameter("status", status).setParameter("ID_WORKER", id).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerOrder> findAllWorkerOrders(Long id) {
		return entityManager.createNamedQuery("CustomerOrder.findWorkerOrders")
				.setParameter("ID_WORKER", id).getResultList();
	}

	@Override
	public CustomerOrder mergeCustomerOrder(CustomerOrder customerOrder) {
		return entityManager.merge(customerOrder);
	}

	@Override
	public CustomerOrder persistCustomerOrder(CustomerOrder customerOrder) {
		entityManager.persist(customerOrder);
		entityManager.flush();
		return customerOrder;
	}

	@Override
	public void removeCustomerOrder(CustomerOrder customerOrder) {
		entityManager.remove(entityManager.contains(customerOrder) ? customerOrder : entityManager.merge(customerOrder));
	}
}
