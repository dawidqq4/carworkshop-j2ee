package applicaton.workshop.web.view.home;

import java.io.File;
import java.io.FileOutputStream;
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

import application.workshop.api.exception.CustomerOrderNotFoundException;
import application.workshop.api.manager.CarManager;
import application.workshop.api.manager.CustomerManager;
import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.ejb.mail.MailSendingService;
import application.workshop.model.Car;
import application.workshop.model.Customer;
import application.workshop.model.CustomerOrder;
import applicaton.workshop.web.servlet.Log4J;

@ManagedBean
@ViewScoped
public class EditOrderBean implements Serializable {
	private static final long serialVersionUID = -4866302996655004108L;
	
	private Customer customer;
	private Car car;
	private CustomerOrder customerOrder = null;
	private Long id;
	private String address = "";

	@EJB
	CustomerManager customerManager;
	
	@EJB
	CarManager carManager;
	
	@EJB
	CustomerOrderManager customerOrderManager;

	@EJB
	MailSendingService mailSendingService;
	
	
	public void validateOrder(FacesContext context, UIComponent component, Object value) {
		try {
			Long id = (Long) value;
			customerOrder = customerOrderManager.findCustomerOrder(id);
			customer = customerOrder.getCar().getCustomer();
			car = customerOrder.getCar();
			
			if (customerOrder.getStatus().equals("inprogress") || customerOrder.getStatus().equals("finished") 
					|| customerOrder.getStatus().equals("closed")) {
				((UIInput) component).setValid(false);
				FacesMessage message = new FacesMessage("Brak zezwolenia na modyfikacjê danych. "
						+ "Zamówienie jest w trakcie realizacji.");
				context.addMessage(component.getClientId(context), message);
			}
		} catch (CustomerOrderNotFoundException e) {
			((UIInput) component).setValid(false);
			FacesMessage message = new FacesMessage("Brak zamówienia o wprowadzonym identyfikatorze.");
			context.addMessage(component.getClientId(context), message);
		}
	}
	
	public void editCustomerOrder(ActionEvent event) throws MessagingException {
		Log4J.getLogger().debug("QASDASD");
		customerManager.mergeCustomer(customer);
		carManager.mergeCar(car);
		customerOrderManager.mergeCustomerOrder(customerOrder);
		
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		
		String body = "Witamy \n";
		body += "Adres e-mail zosta³ wys³any automatycznie w zwi¹zku z modyfikacj¹ zamówienia.\n";
		body += "Za³¹czyliœmy plik ze szczegó³ami modyfikowanego zamówienia, prosimy zapamiêtaæ identyfikator "
				+ "potrzebny w celach weryfikacji zamówienia. Znajduje siê w za³¹czonym pliku.\n\n";
		body += "Pozdrawiamy\n" + "Zespó³ warsztatu samochodowego";
		messageBodyPart.setText(body);
		
		multipart.addBodyPart(messageBodyPart);
		createEditOrderConfirmation(multipart);
		mailSendingService.sendEmailWithAttachment(customer.getEmailAddress(), 
				"Warsztat samochodowy - pomyœlna modyfikacja zamówienia.", multipart);
		
		FacesMessage message = new FacesMessage("Informacja", "Poprawna modyfikacja zamówienia");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public void createEditOrderConfirmation(Multipart multipart) {
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address += address + " ";
		customer.setAddress(this.address);
	}
}