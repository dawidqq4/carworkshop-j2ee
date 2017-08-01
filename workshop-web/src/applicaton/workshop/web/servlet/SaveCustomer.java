package applicaton.workshop.web.servlet;
 
import java.io.IOException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.workshop.api.manager.CustomerManager;
import application.workshop.model.Customer;
 
@WebServlet("/saveCustomer")
public class SaveCustomer extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Context ctx;
		CustomerManager manager = null;
		try {
			ctx = new InitialContext();
			manager = (CustomerManager) ctx.lookup("java:app/workshop-ejb/DefaultCustomerManager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String emailAddress =  request.getParameter("emailaddress");
        Integer phone = Integer.parseInt(request.getParameter("phone"));
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setEmailAddress(emailAddress);
        customer.setPhone(phone);
		manager.persistCustomer(customer);
    }
}