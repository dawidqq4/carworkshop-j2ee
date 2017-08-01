package application.workshop.web.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class VinValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2) throws ValidatorException {
		String vin = (String) arg2;
		if (!isVinValid(vin, arg0, arg1))
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nieprawid³owy numer VIN.", ""));
	}

	public boolean isVinValid(String vin, FacesContext context, UIComponent component) {
		int[] values = { 1, 2, 3, 4, 5, 6, 7, 8, 0, 1, 2, 3, 4, 5, 0, 7, 0, 9, 2, 3, 4, 5, 6, 7, 8, 9 };
		int[] weights = { 8, 7, 6, 5, 4, 3, 2, 10, 0, 9, 8, 7, 6, 5, 4, 3, 2 };

		String s = vin;
		s = s.replaceAll("-", "");
		s = s.replaceAll(" ", "");
		s = s.toUpperCase();
		if (s.length() != 17) {
			FacesMessage message = new FacesMessage("Numer VIN musi mieæ 17 znaków.");
			context.addMessage(component.getClientId(context), message);
			return false;
		}

		int sum = 0;
		for (int i = 0; i < 17; i++) {
			char c = s.charAt(i);
			int value = 0;
			int weight = weights[i];

			// letter
			if (c >= 'A' && c <= 'Z') {
				value = values[c - 'A'];
				if (value == 0) {
					FacesMessage message = new FacesMessage("Niedozwolony znak: " + c);
					context.addMessage(component.getClientId(context), message);
					return false;
				}

			}

			// number
			else if (c >= '0' && c <= '9')
				value = c - '0';

			// illegal character
			else {
				FacesMessage message = new FacesMessage("Niedozwolony znak: " + c);
				context.addMessage(component.getClientId(context), message);
				return false;
			}
			sum = sum + weight * value;
		}

		// check digit
		sum = sum % 11;
		char check = s.charAt(8);
		if (sum == 10 && check == 'X') {
			return true;
		} else if (sum == transliterate(check)) {
			return true;
		} else {
			return false;
		}
	}

	private int transliterate(char check) {
		if (check == 'A' || check == 'J') {
			return 1;
		} else if (check == 'B' || check == 'K' || check == 'S') {
			return 2;
		} else if (check == 'C' || check == 'L' || check == 'T') {
			return 3;
		} else if (check == 'D' || check == 'M' || check == 'U') {
			return 4;
		} else if (check == 'E' || check == 'N' || check == 'V') {
			return 5;
		} else if (check == 'F' || check == 'W') {
			return 6;
		} else if (check == 'G' || check == 'P' || check == 'X') {
			return 7;
		} else if (check == 'H' || check == 'Y') {
			return 8;
		} else if (check == 'R' || check == 'Z') {
			return 9;
		} else if (Integer.valueOf(Character.getNumericValue(check)) != null) {
			return Character.getNumericValue(check);
		}
		return -1;
	}
	
}
