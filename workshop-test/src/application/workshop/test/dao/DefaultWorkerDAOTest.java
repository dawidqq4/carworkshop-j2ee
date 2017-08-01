package application.workshop.test.dao;

import java.lang.reflect.Field;
import java.security.NoSuchAlgorithmException;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.junit.Before;
import org.junit.Test;

import application.workshop.api.dao.WorkerDAO;
import application.workshop.ejb.dao.DefaultWorkerDAO;
import application.workshop.model.Worker;

public class DefaultWorkerDAOTest {

	private Worker worker;
	private WorkerDAO workerDAO;
	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		worker = new Worker("worker");
		workerDAO = new DefaultWorkerDAO();
		entityManager = Persistence.createEntityManagerFactory("workshop-test").createEntityManager();

		Field field = DefaultWorkerDAO.class.getDeclaredField("entityManager");
		field.setAccessible(true);
		field.set(workerDAO, entityManager);
	}

	@Test
	public void testPersistWorker() throws NoSuchAlgorithmException {
		entityManager.getTransaction().begin();
		worker.setFirstName("FirstNameTestDAO");
		worker.setLastName("LastNameTestDAO");
		worker.setAddress("AddressTestDAO");
		worker.setEmailAddress("EmailTestDAO");
		worker.setPhone(555444333);
		worker.setUsername("LoginTestDAO");
		worker.setPassword("PasswordTestDAO");
		workerDAO.persistWorker(worker);
		
		Worker findWorker = workerDAO.findWorker(worker.getId());
		assert worker.equals(findWorker) : "Worker persist unsuccessfully";

		workerDAO.findAllWorkers();

		worker.setFirstName("NewFirstNameTestDAO");
		workerDAO.mergeWorker(worker);
		
		findWorker = workerDAO.findWorker(worker.getId());
		assert worker.equals(findWorker) : "Worker merged unsuccessfully";

		workerDAO.removeWorker(worker);
		assert (workerDAO.findWorker(worker.getId()) == null) : "Worker removed unsuccessfully";
		entityManager.getTransaction().commit();
	}
}
