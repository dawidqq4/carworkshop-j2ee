package application.workshop.web.order.state;

import java.io.Serializable;

public class FinishedState implements OrderState, Serializable {
	private static final long serialVersionUID = 1567624129207979621L;

	@Override
	public String getDescription() {
		return "Zam�wienie zosta�o zako�czone. Sam�chod czeka na odbi�r.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return false;
	}
}
 