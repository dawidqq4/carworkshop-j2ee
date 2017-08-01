package application.workshop.web.order.state;

import java.io.Serializable;

public class InProgressState implements OrderState, Serializable  {
	private static final long serialVersionUID = -2275246432601455088L;

	@Override
	public String getDescription() {
		return "Trwa naprawa samochodu. Prosimy o cierpliwoœæ.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return false;
	}
}
