package applicaton.workshop.web.view.home;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import application.workshop.api.exception.CustomerOrderNotFoundException;
import application.workshop.api.manager.CarManager;
import application.workshop.api.manager.CustomerManager;
import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.model.Car;
import application.workshop.model.Customer;
import application.workshop.model.CustomerOrder;
import applicaton.workshop.web.servlet.Log4J;

@ManagedBean
@ViewScoped
public class CancelOrderBean implements Serializable {
	private static final long serialVersionUID = 1054199155494978081L;
	
	private Customer customer;
	private Car car;
	private CustomerOrder customerOrder = null;
	private Long id;

	@EJB
	CustomerManager customerManager;
	
	@EJB
	CarManager carManager;
	
	@EJB
	CustomerOrderManager customerOrderManager;
		
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void validateOrder(FacesContext context, UIComponent component, Object value) {
		try {
			Long id = (Long) value;
			customerOrder = customerOrderManager.findCustomerOrder(id);
			customer = customerOrder.getCar().getCustomer();
			car = customerOrder.getCar();
			
			if (customerOrder.getStatus().equals("inprogress") || customerOrder.getStatus().equals("finished") 
					|| customerOrder.getStatus().equals("closed")) {
				((UIInput) component).setValid(false);
				FacesMessage message = new FacesMessage("Brak zezwolenia na anulowanie zamówienia. "
						+ "Zamówienie jest w trakcie realizacji.");
				context.addMessage(component.getClientId(context), message);
			}
		} catch (CustomerOrderNotFoundException e) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Brak zamówienia o wprowadzonym identyfikatorze.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void cancelCustomerOrder(ActionEvent event) {
		Log4J.getLogger().debug("QASDASD");
		customerOrderManager.removeCustomerOrder(customerOrder);	
		carManager.removeCar(car);
		customerManager.removeCustomer(customer);
		
		FacesMessage message = new FacesMessage("Informacja", "Poprawnie anulowanie zamówienie");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}