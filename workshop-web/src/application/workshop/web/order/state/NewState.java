package application.workshop.web.order.state;

import java.io.Serializable;

public class NewState implements OrderState, Serializable {
	private static final long serialVersionUID = 8049954524587627733L;

	@Override
	public String getDescription() {
		return "Zam�wienie zosta�o dodane. "
				+ "Prosimy odwiedzi� stron� w ci�gu dw�ch dni w celu akceptacji ceny i rozpocz�cia naprawy."
				+ "Zastrzegamy mozliwo�� kontaktu je�li opis zam�wienia b�dzie niewystarczaj�cy.";
	}

	@Override
	public boolean checkCancelPossibility() {
		return true;
	}
}
