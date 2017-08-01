package application.workshop.web.view.admin;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import application.workshop.api.manager.WorkerManager;
import application.workshop.model.Worker;
 
@ManagedBean
@ViewScoped
public class ShowWorkersBean implements Serializable {
	private static final long serialVersionUID = 8376739838091876103L;
 
    private List<Worker> workers;
    private Worker selectedWorker;
      
    @EJB
    WorkerManager workerManager;
    
    @PostConstruct
    public void init() {
    	workers = workerManager.findAllWorkers();
    }  
    
	public List<Worker> getWorkers() {
		return workers;
	}
	
	public Worker getSelectedWorker() {
		return selectedWorker;
	}

	public void setSelectedWorker(Worker selectedWorker) {
		this.selectedWorker = selectedWorker;
	}    
}
