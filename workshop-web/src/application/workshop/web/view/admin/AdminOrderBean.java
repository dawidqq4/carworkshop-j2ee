package application.workshop.web.view.admin;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
 
@ManagedBean
@ViewScoped
public class AdminOrderBean implements Serializable {
	private static final long serialVersionUID = 8376739838091876103L;
 
	private Boolean isButtonDisabled = true;
    private CustomerOrder selectedCustomerOrder;
    private List<CustomerOrder> customerOrders;
    private List<CustomerOrder> droppedCustomerOrders;
    private List<Worker> workers;
    private Worker selectedWorker;
     
    @EJB
    CustomerOrderManager customerOrderManager;
    
    @EJB
    WorkerManager workerManager;
    
    @EJB
    MailSendingService mailSendingService;
    
    @PostConstruct
    public void init() {
    	customerOrders = customerOrderManager.findAllNewCustomerOrders("new");
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

	public void onCustomerOrderDrop(DragDropEvent ddEvent) {
    	CustomerOrder customerOrder = ((CustomerOrder) ddEvent.getData());
  
    	droppedCustomerOrders.add(customerOrder);
    	customerOrders.remove(customerOrder);
    }
    
    public void workerChanged(AjaxBehaviorEvent event) {
    	isButtonDisabled = false;
    }
    
    public void addWorkerToOrder(ActionEvent event) throws IOException {
    	selectedCustomerOrder.setWorker(selectedWorker);
    	selectedCustomerOrder.setStatus("verification");
    	customerOrderManager.mergeCustomerOrder(selectedCustomerOrder);
    	
		String body = "Witamy \n";
		body += "Adres e-mail zosta³ wys³any automatycznie w zwi¹zku ze z³ozeniem zamówienia w naszym serwisie.\n";
		body += "Dodaliœmy pracownika który zajmie siê napraw¹ samochodu.\n";
		body += "Imiê: " + selectedWorker.getFirstName() + "\n";
		body += "Nazwisko: " + selectedWorker.getLastName() + "\n";
		body += "Telefon: " + selectedWorker.getPhone() + "\n";
		body += "Pozdrawiamy\n" + "Zespó³ warsztatu samochodowego";
		mailSendingService.sendEmail(selectedCustomerOrder.getCar().getCustomer().getEmailAddress(), 
				"Warsztat samochodowy - dodano pracownika do obs³ugi zamówienia.", body);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath() + "/navigation/home.jsf");
    }
    
    public Worker getWorker(Long id) {
        if (id == null){
            throw new IllegalArgumentException("Brak pracownika o podanym ID");
        }
        for (Worker i : workers){
            if (id.equals(i.getId())){
                return i;
            }
        }
        return null;
    }
}