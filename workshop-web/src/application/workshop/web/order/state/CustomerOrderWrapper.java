package application.workshop.web.order.state;

import application.workshop.model.CustomerOrder;

public class CustomerOrderWrapper {
	private CustomerOrder customerOrder;
	private OrderState orderState;

	public CustomerOrderWrapper(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public OrderState getOrderState() {
		return orderState;
	}

	public void setOrderState() {
		switch (customerOrder.getStatus()) {
		case "new":
			orderState = new NewState();
			break;
		case "verification":
			orderState = new VerificationState();
			break;
		case "inprogress":
			orderState = new InProgressState();
			break;
		case "finished":
			orderState = new FinishedState();
			break;
		case "closed":
			orderState = new ClosedState();
			break;
		}
	}
}
