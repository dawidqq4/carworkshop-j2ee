package application.workshop.web.order.state;

import java.io.Serializable;

public class VerificationState implements OrderState, Serializable {
	private static final long serialVersionUID = 3314576364199943394L;

	@Override
	public String getDescription() {
		return "Zam�wienie jest w trakcie weryfikacji. Pracownik weryfikuje informacje zamieszczone w zg�oszeniu";
	}

	@Override
	public boolean checkCancelPossibility() {
		return true;
	}
}
