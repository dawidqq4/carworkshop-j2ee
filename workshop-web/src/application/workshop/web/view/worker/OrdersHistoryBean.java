package application.workshop.web.view.worker;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.event.DragDropEvent;

import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.api.manager.WorkerManager;
import application.workshop.ejb.mail.MailSendingService;
import application.workshop.model.CustomerOrder;
import application.workshop.model.Worker;
import applicaton.workshop.web.view.home.UserLoginView;
 
@ManagedBean
@ViewScoped
public class OrdersHistoryBean implements Serializable {
	private static final long serialVersionUID = 8376739838091876103L;
 
	private Boolean isButtonDisabled = true;
    private CustomerOrder selectedCustomerOrder;
    private List<CustomerOrder> customerOrders;
    private List<CustomerOrder> droppedCustomerOrders;
    private List<Worker> workers;
    private Worker selectedWorker;
     
    @ManagedProperty("#{userLoginView}")
    private UserLoginView userLoginView;
    
    @EJB
    CustomerOrderManager customerOrderManager;
    
    @EJB
    WorkerManager workerManager;
    
    @EJB
    MailSendingService mailSendingService;
    
    @PostConstruct
    public void init() {
    	customerOrders = customerOrderManager.findAllWorkerOrders(userLoginView.getWorker().getId());
    	workers = workerManager.findAllWorkers();
    	droppedCustomerOrders = new LinkedList<CustomerOrder>();
    }  
    
	public Boolean getIsButtonDisabled() {
		return isButtonDisabled;
	}

	public void setIsButtonDisabled(Boolean isButtonDisabled) {
		this.isButtonDisabled = isButtonDisabled;
	}

	public CustomerOrder getSelectedCustomerOrder() {
		return selectedCustomerOrder;
	}

	public void setSelectedCustomerOrder(CustomerOrder selectedCustomerOrder) {
		this.selectedCustomerOrder = selectedCustomerOrder;
	}  
	
	public List<CustomerOrder> getCustomerOrders() {
		return customerOrders;
	}

	public List<CustomerOrder> getDroppedCustomerOrders() {
		return droppedCustomerOrders;
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

	public UserLoginView getUserLoginView() {
		return userLoginView;
	}

	public void setUserLoginView(UserLoginView userLoginView) {
		this.userLoginView = userLoginView;
	}

	public void validatePrice(FacesContext context, UIComponent component, Object value) {
		Double price = (Double) value;
		if (price < 30) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Cena musi wynosi� conajmniej 30z�.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void onWorkerOrderDrop(DragDropEvent ddEvent) {
    	CustomerOrder customerOrder = ((CustomerOrder) ddEvent.getData());
  
    	droppedCustomerOrders.add(customerOrder);
    	customerOrders.remove(customerOrder);
    }
    
    public void workerChanged(AjaxBehaviorEvent event) {
    	isButtonDisabled = false;
    }
    
    public void chargeMoney(ActionEvent event) throws IOException {
    	selectedCustomerOrder.setStatus("closed");
    	customerOrderManager.mergeCustomerOrder(selectedCustomerOrder);
    	
		String body = "Witamy \n";
		body += "Adres e-mail zosta� wys�any automatycznie w zwi�zku z zako�czeniem zam�wienia.\n";
		body += "Pozdrawiamy\n" + "Zesp� warsztatu samochodowego";
		mailSendingService.sendEmail(selectedCustomerOrder.getCar().getCustomer().getEmailAddress(), 
				"Warsztat samochodowy - dzi�kujemy za skorzystanie z naszych us�ug.", body);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath() + "/navigation/home.jsf");
    }
}