package application.workshop.api.dao;

import java.util.List;

import javax.ejb.Local;

import application.workshop.model.Worker;

@Local
public interface WorkerDAO {
	Worker findWorker(Long id);
	List<Worker> findWorkerToLogin(Worker worker);
	List<Worker> findAllWorkers();
	Worker mergeWorker(Worker worker);
	Worker persistWorker(Worker worker);
	void removeWorker(Worker worker);
}