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
public class EditWorkerBean implements Serializable {
	private static final long serialVersionUID = 1136132770649396763L;
	
	private Boolean renderComponents = false;
	private Long id;
	private String address = "";
	private Worker worker = null;

	@EJB
	WorkerManager workerManager;


	public void validateWorker(FacesContext context, UIComponent component, Object value) {
		try {
			Long id = (Long) value;
			worker = workerManager.findWorker(id);;
		} catch (WorkerNotFoundException e) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Brak pracownika o wprowadzonym identyfikatorze.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void validatePassword(FacesContext context, UIComponent component, Object value) {
		String password = (String) value;
		if (password.length() < 6) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Has³o musi mieæ conajmniej szeœæ znaków.");
			context.addMessage(component.getClientId(context), message);
		}
		
		UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("confrimPassword");
		String confrimPassword = (String) uiInputConfirmPassword.getSubmittedValue();
		
		if (!password.equals(confrimPassword)) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Has³a rózni¹ siê.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void editWorker(ActionEvent event) {
		workerManager.mergeWorker(worker);
		FacesMessage message = new FacesMessage("Informacja", "Poprawna modyfikacja pracownika");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	
	public Boolean getRenderComponents() {
		return renderComponents;
	}

	public void setRenderComponents(Boolean renderComponent) {
		this.renderComponents = renderComponent;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address += address + " ";
		worker.setAddress(this.address);
	}
	
	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}
}