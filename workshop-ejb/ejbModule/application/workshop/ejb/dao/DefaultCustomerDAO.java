package application.workshop.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import application.workshop.api.dao.CustomerDAO;
import application.workshop.model.Customer;

/**
 * Session Bean implementation class DefaultCustomerDAO
 */
@Stateless
public class DefaultCustomerDAO implements CustomerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public Customer findCustomer(Long id) {
		return entityManager.find(Customer.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Customer> findAllCustomers() {
		return entityManager.createNamedQuery("Customer.findAllOrdered").getResultList();
	}

	@Override
	public Customer mergeCustomer(Customer customer) {
		return entityManager.merge(customer);
	}

	@Override
	public Customer persistCustomer(Customer customer) {
		entityManager.persist(customer);
		entityManager.flush();
		return customer;
	}

	@Override
	public void removeCustomer(Customer customer) {
		entityManager.remove(entityManager.contains(customer) ? customer : entityManager.merge(customer));
	}
}
