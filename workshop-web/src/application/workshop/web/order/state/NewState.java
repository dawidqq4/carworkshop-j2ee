package application.workshop.web.order.state;

import java.io.Serializable;

public class NewState implements OrderState, Serializable {
	private static final long serialVersionUID = 8049954524587627733L;

	@Override
	public String getDescription() {
		return "Zamówienie zosta³o dodane. "
				+ "Prosimy odwiedziæ stronê w ci¹gu dwóch dni w celu akceptacji ceny i rozpoczêcia naprawy."
				+ "Zastrzegamy mozliwoœæ kontaktu jeœli opis zamówienia bêdzie niewystarczaj¹cy.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return true;
	}
}
