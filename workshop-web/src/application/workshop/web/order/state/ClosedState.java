package application.workshop.web.order.state;

import java.io.Serializable;

public class ClosedState implements OrderState, Serializable  {
	private static final long serialVersionUID = -4398148562157450163L;

	@Override
	public String getDescription() {
		return "Zam�wienie jest zamkni�te.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return false;
	}
}
