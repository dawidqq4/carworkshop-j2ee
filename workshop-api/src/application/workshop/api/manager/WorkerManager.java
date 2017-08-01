package application.workshop.api.manager;

import java.util.List;

import javax.ejb.Remote;

import application.workshop.api.exception.WorkerNotFoundException;
import application.workshop.model.Worker;

@Remote
public interface WorkerManager {
	List<Worker> findAllWorkers();
	List<Worker> findWorkerToLogin(Worker worker) throws WorkerNotFoundException ;
	Worker findWorker(Long id) throws WorkerNotFoundException;
	Worker mergeWorker(Worker worker);
	Worker persistWorker(Worker worker);
	void removeWorker(Worker worker);
}
