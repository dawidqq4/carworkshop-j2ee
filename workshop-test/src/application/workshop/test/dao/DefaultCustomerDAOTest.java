package application.workshop.test.dao;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import application.workshop.api.dao.CustomerDAO;
import application.workshop.ejb.dao.DefaultCustomerDAO;
import application.workshop.model.Customer;

public class DefaultCustomerDAOTest {

	private Customer customer;
	private CustomerDAO customerDAO;
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		customer = new Customer();
		customerDAO = new DefaultCustomerDAO();
		entityManager = Persistence.createEntityManagerFactory("workshop-test").createEntityManager();

		Field field = DefaultCustomerDAO.class.getDeclaredField("entityManager");
		field.setAccessible(true);
		field.set(customerDAO, entityManager);
	}

	@Test
	public void testPersistWorker() throws NoSuchAlgorithmException {
		entityManager.getTransaction().begin();
		customer.setFirstName("FirstNameTestDAO");
		customer.setLastName("LastNameTestDAO");
		customer.setAddress("AddressTestDAO");
		customer.setEmailAddress("EmailTestDAO");
		customer.setPhone(555444333);
		customerDAO.persistCustomer(customer);
		
		Customer findCustomer = customerDAO.findCustomer(customer.getId());
		assert customer.equals(findCustomer) : "Customer persist unsuccessfully";

		customerDAO.findAllCustomers();

		customer.setFirstName("NewFirstNameTestDAO");
		customerDAO.mergeCustomer(customer);
		
		findCustomer = customerDAO.findCustomer(customer.getId());
		assert customer.equals(findCustomer) : "Customer merged unsuccessfully";

		customerDAO.removeCustomer(customer);
		assert (customerDAO.findCustomer(customer.getId()) == null) : "Customer removed unsuccessfully";
		entityManager.getTransaction().commit();
	}
}
