package application.workshop.ejb.manager;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import application.workshop.api.dao.WorkerDAO;
import application.workshop.api.exception.WorkerNotFoundException;
import application.workshop.api.manager.WorkerManager;
import application.workshop.model.Worker;

/**
 * Session Bean implementation class DefaultWorkerManager
 */
@Stateless
public class DefaultWorkerManager implements WorkerManager {

	@EJB
	private WorkerDAO workerDAO;

	@Override
	public void removeWorker(Worker worker) {
		workerDAO.removeWorker(worker);
	}

	@Override
	public Worker persistWorker(Worker worker) {
		return workerDAO.persistWorker(worker);
	}

	@Override
	public Worker mergeWorker(Worker worker) {
		return workerDAO.mergeWorker(worker);
	}

	@Override
	public Worker findWorker(Long id) throws WorkerNotFoundException {
		Worker worker = workerDAO.findWorker(id);
		if (worker == null)
			throw new WorkerNotFoundException();
		return worker;
	}

	@Override
	public List<Worker> findAllWorkers() {
		return workerDAO.findAllWorkers();
	}
	
	@Override
	public List<Worker> findWorkerToLogin(Worker worker) throws WorkerNotFoundException {
		List<Worker> workers = workerDAO.findAllWorkers();
		if (workers == null)
			throw new WorkerNotFoundException();
		return workers;
	}
}
