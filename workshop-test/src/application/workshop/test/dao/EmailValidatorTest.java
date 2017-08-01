package application.workshop.test.dao;

import java.security.NoSuchAlgorithmException;

import javax.faces.validator.ValidatorException;

import org.junit.Test;

import application.workshop.web.validator.EmailValidator;

public class EmailValidatorTest {
	
	@Test(expected = ValidatorException.class)
	public void testIncorrectEmailAddress() throws NoSuchAlgorithmException {
		EmailValidator emailValidator = new EmailValidator();
		
		
		String email = "test@mailpl";
		emailValidator.validate(null, null, email);
	}
	
	@Test
	public void testCorrectEmailAddress() throws NoSuchAlgorithmException {
		EmailValidator emailValidator = new EmailValidator();
		
		
		String email = "test@mail.pl";
		emailValidator.validate(null, null, email);
	}
}
