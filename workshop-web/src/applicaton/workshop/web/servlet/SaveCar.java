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

import application.workshop.api.exception.CustomerNotFoundException;
import application.workshop.api.manager.CarManager;
import application.workshop.api.manager.CustomerManager;
import application.workshop.model.Car;
import application.workshop.model.Customer;
 
@WebServlet("/saveCar")
public class SaveCar extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Context ctx;
		CustomerManager customerManager = null;
		CarManager carManager = null;
		try {
			ctx = new InitialContext();
			customerManager = (CustomerManager) ctx.lookup("java:app/workshop-ejb/DefaultCustomerManager");
			carManager = (CarManager) ctx.lookup("java:app/workshop-ejb/DefaultCarManager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        String name = request.getParameter("name");
        String vin = request.getParameter("vin");
        Long idCustomer = Long.parseLong(request.getParameter("customerid"));
        Car car = new Car();
        car.setName(name);
        car.setVin(vin);
        Customer customer = null;
		try {
			customer = customerManager.findCustomer(idCustomer);
		} catch (CustomerNotFoundException e) {
			e.printStackTrace();
		}
        car.setCustomer(customer);
		carManager.persistCar(car);
    }
}