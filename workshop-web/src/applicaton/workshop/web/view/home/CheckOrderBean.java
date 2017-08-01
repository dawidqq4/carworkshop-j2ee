package applicaton.workshop.web.view.home;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;

import application.workshop.api.exception.CustomerOrderNotFoundException;
import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.model.CustomerOrder;
import application.workshop.web.order.state.CustomerOrderWrapper;

@ManagedBean
@ViewScoped
public class CheckOrderBean implements Serializable {
	private static final long serialVersionUID = 868613359885765015L;
	
	private Boolean renderComponents = false;
	private CustomerOrderWrapper order;
	private CustomerOrder customerOrder = null;
	private Long id;
	private String stateDescription;
	private UIComponent component;

	@EJB
	CustomerOrderManager customerOrderManager;

	public Boolean getRenderComponents() {
		return renderComponents;
	}

	public void setRenderComponents(Boolean renderComponent) {
		this.renderComponents = renderComponent;
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
	
	public String getStateDescription() {
		return stateDescription;
	}

	public void setStateDescription(String stateDescription) {
		this.stateDescription = stateDescription;
	}

	public UIComponent getComponent() {
		return component;
	}

	public void setComponent(UIComponent component) {
		this.component = component;
	}

	public void validateOrder(FacesContext context, UIComponent component, Object value) {
		try {
			Long id = (Long) value;
			customerOrder = customerOrderManager.findCustomerOrder(id);
			order = new CustomerOrderWrapper(customerOrder);
			order.setOrderState();
			stateDescription = order.getOrderState().getDescription();
		} catch (CustomerOrderNotFoundException e) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Brak zamówienia o wprowadzonym identyfikatorze.");
			context.addMessage(component.getClientId(context), message);
		}
	}
}