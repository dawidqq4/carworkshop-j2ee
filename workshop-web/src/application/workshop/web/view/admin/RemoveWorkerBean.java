package application.workshop.web.view.admin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import application.workshop.api.exception.WorkerNotFoundException;
import application.workshop.api.manager.WorkerManager;
import application.workshop.model.Worker;

@ManagedBean
@ViewScoped
public class RemoveWorkerBean implements Serializable {
	private static final long serialVersionUID = -2714918712145164599L;
	
	private Long id;
	private Worker worker = null;

	@EJB
	WorkerManager workerManager;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
		
	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public void validateWorker(FacesContext context, UIComponent component, Object value) {
		try {
			Long id = (Long) value;
			worker = workerManager.findWorker(id);		
		} catch (WorkerNotFoundException e) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Brak pracownika o wprowadzonym identyfikatorze.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void removeWorker(ActionEvent event) {
		workerManager.removeWorker(worker);	
		FacesMessage message = new FacesMessage("Informacja", "Poprawnie usuniêto pracownika");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}