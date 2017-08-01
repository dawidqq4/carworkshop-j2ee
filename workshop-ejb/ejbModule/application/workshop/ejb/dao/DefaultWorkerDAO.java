package application.workshop.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import application.workshop.api.dao.WorkerDAO;
import application.workshop.model.Worker;

/**
 * Session Bean implementation class DefaultWorkerDAO
 */
@Stateless
public class DefaultWorkerDAO implements WorkerDAO {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Worker findWorker(Long id) {
		return entityManager.find(Worker.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Worker> findWorkerToLogin(Worker worker) {
		return entityManager.createNamedQuery("SELECT worker FROM Worker worker WHERE worker.username = :" + worker.getUsername() + " AND worker.password = :" + worker.getPassword()).getResultList();
		
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Worker> findAllWorkers() {
		return entityManager.createNamedQuery("Worker.findAllOrdered").getResultList();
	}

	@Override
	public Worker mergeWorker(Worker worker) {
		return entityManager.merge(worker);
	}
	
	@Override
	public Worker persistWorker(Worker worker) {
		entityManager.persist(worker);
		entityManager.flush();
		return worker;
	}

	@Override
	public void removeWorker(Worker worker) {
		entityManager.remove(entityManager.contains(worker) ? worker : entityManager.merge(worker));
	}
}
