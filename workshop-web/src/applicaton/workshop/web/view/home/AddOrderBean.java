package applicaton.workshop.web.view.home;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import application.workshop.api.manager.CarManager;
import application.workshop.api.manager.CustomerManager;
import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.ejb.mail.MailSendingService;
import application.workshop.model.Car;
import application.workshop.model.Customer;
import application.workshop.model.CustomerOrder;

@ManagedBean
@ViewScoped
public class AddOrderBean implements Serializable {
	private static final long serialVersionUID = -2287490949877830785L;

	private Customer customer = new Customer();
	private Car car = new Car();
	private CustomerOrder customerOrder = new CustomerOrder();
	private Integer key;
	private Integer enteredKey;
	private String address = "";
	
	@EJB
	CustomerManager customerManager;

	@EJB
	CarManager carManager;

	@EJB
	CustomerOrderManager customerOrderManager;
	
	@EJB
	MailSendingService mailSendingService;

	
	public void validateEmailKey(FacesContext context, UIComponent component, Object value) {
		Integer emailKey = (Integer) value;
		if (!emailKey.equals(key)) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Wprowadzono nieprawid³owy kod.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void generateKey(ActionEvent event) {
		key = (int)(Math.random() * 9000) + 1000;
		String body = "Witamy \n";
		body += "Adres e-mail zosta³ wys³any automatycznie w celu weryfikacji uzytkownika.\n";
		body += "Prosimy o wprowadzenie podanego kodu w celu weryfikacji: " + key + "\n\n";
		body += "Pozdrawiamy\n" + "Zespó³ warsztatu samochodowego";
		mailSendingService.sendEmail(customer.getEmailAddress(), "Warsztat samochodowy - weryfikacja adresu e-mail.", body);
		
		FacesMessage message = new FacesMessage("Na wprowadzony adres e-mail wys³aliœmy kod weryfikacyjny.");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void cancelAddOrderProcess(ActionEvent event) throws IOException {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath() + "/navigation/home.jsf");
	}
	
	public void save(ActionEvent event) throws MessagingException, IOException {
		customer = customerManager.persistCustomer(customer);
		
		car.setCustomer(customer);
		car = carManager.persistCar(car);

		long millis = System.currentTimeMillis();
		Date date = new java.util.Date(millis);
		customerOrder.setDate(date);
		customerOrder.setStatus("new");
		customerOrder.setCar(car);
		customerOrder = customerOrderManager.persistCustomerOrder(customerOrder);

		if (customerOrder.getId() != null) {
			FacesMessage msg = new FacesMessage("Dodano zamówienie", "Witamy klienta :" + customer.getLastName());
			FacesContext.getCurrentInstance().addMessage(null, msg);
			Multipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			
			String body = "Witamy \n";
			body += "Adres e-mail zosta³ wys³any automatycznie w zwi¹zku z dodaniem zamówienia.\n";
			body += "Za³¹czyliœmy plik ze szczegó³ami wprowadzonego zamówienia, prosimy zapamiêtaæ identyfikator "
					+ "potrzebny w celach weryfikacji zamówienia. Znajduje siê w za³¹czonym pliku.\n\n";
			body += "Pozdrawiamy\n" + "Zespó³ warsztatu samochodowego";
			
			messageBodyPart.setText(body);
			multipart.addBodyPart(messageBodyPart);
			createOrderConfirmation(multipart);
			mailSendingService.sendEmailWithAttachment(customer.getEmailAddress(), 
					"Warsztat samochodowy - pomyœlnie z³ozono zamówienie.", multipart);
		} else { 
			FacesMessage msg = new FacesMessage("B³¹d", "B³¹d przy dodawaniu zlecenia");
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		externalContext.redirect(externalContext.getRequestContextPath() + "/navigation/home.jsf");
	}
	
	private void createOrderConfirmation(Multipart multipart) {
		try {
			OutputStream file = new FileOutputStream(new File("potwierdzenie.pdf"));
			Document document = new Document();
			PdfWriter.getInstance(document, file);
			document.open();
			
			Date date = new Date();
			String dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(date);
			
			document.add(new Paragraph("Kielce, dnia: " + dateFormat));
			document.add(new Paragraph("___________________________________________________"));
			document.add(new Paragraph("Warsztat samochodowy: www.workshop-app.pl"));
			document.add(new Paragraph("Jan Kowalski, ul. Warszawska 200, 25-050 Kielce"));
			document.add(new Paragraph("ID zamówienia:" + customerOrder.getId()));
			document.add(new Paragraph("___________________________________________________"));
			document.add(new Paragraph("Dane klienta"));
			document.add(new Paragraph("Imie: " + customer.getFirstName()));
			document.add(new Paragraph("Nazwisko: " + customer.getLastName()));
			document.add(new Paragraph("Adres: " + customer.getAddress()));
			document.add(new Paragraph("Telefon: " + customer.getPhone()));
			document.add(new Paragraph("Adres e-mail: " + customer.getEmailAddress()));
			document.add(new Paragraph("___________________________________________________"));
			document.add(new Paragraph("Dane samochodu"));
			document.add(new Paragraph("Nazwa samochodu: " + car.getName()));
			document.add(new Paragraph("VIN: " + car.getVin()));
			document.add(new Paragraph("___________________________________________________"));
			document.add(new Paragraph("Dane zamówienia"));
			document.add(new Paragraph("Opis problemu: " + customerOrder.getDescription()));
			document.close();
			file.close();
			
			BodyPart messageBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource("potwierdzenie.pdf");
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName("potwierdzenie.pdf");
			multipart.addBodyPart(messageBodyPart);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public CustomerOrder getCustomerOrder() {
		return customerOrder;
	}

	public void setCustomerOrder(CustomerOrder customerOrder) {
		this.customerOrder = customerOrder;
	}

	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	public Integer getEnteredKey() {
		return enteredKey;
	}

	public void setEnteredKey(Integer enteredKey) {
		this.enteredKey = enteredKey;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address += address + " ";
		customer.setAddress(this.address);
	}
}