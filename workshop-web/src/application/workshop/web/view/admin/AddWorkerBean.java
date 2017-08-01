package application.workshop.web.view.admin;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.jms.JMSException;

import application.workshop.ejb.mdb.PersistWorkerService;
import application.workshop.model.Worker;

@ManagedBean
@ViewScoped
public class AddWorkerBean implements Serializable {
	private static final long serialVersionUID = -2287490949877830785L;

	private String address = "";
	private Worker worker = new Worker("worker");

	@EJB
	PersistWorkerService persistWorkerService;


	public void validatePassword(FacesContext context, UIComponent component, Object value) {
		String password = (String) value;
		if (password.length() < 6) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Has³o musi mieæ conajmniej szeœæ znaków.");
			context.addMessage(component.getClientId(context), message);
		}
		
		UIInput uiInputConfirmPassword = (UIInput) component.getAttributes().get("confrimPassword");
		String confrimPassword = (String) uiInputConfirmPassword.getSubmittedValue();
		
		System.out.println(password);
		
		System.out.println(confrimPassword);
		
		if (!password.equals(confrimPassword)) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Has³a rózni¹ siê.");
			context.addMessage(component.getClientId(context), message);
		}
	}

	public void save() throws JMSException {
		persistWorkerService.sendMessage(worker);
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