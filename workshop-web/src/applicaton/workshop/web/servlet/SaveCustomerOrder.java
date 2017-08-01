package applicaton.workshop.web.servlet;
 
import java.io.IOException;
import java.util.Date;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.workshop.api.exception.CarNotFoundException;
import application.workshop.api.exception.WorkerNotFoundException;
import application.workshop.api.manager.CarManager;
import application.workshop.api.manager.WorkerManager;
import application.workshop.api.manager.CustomerOrderManager;
import application.workshop.model.Worker;
import application.workshop.model.Car;
import application.workshop.model.CustomerOrder;
 
@WebServlet("/saveOrder")
public class SaveCustomerOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Context ctx;
		WorkerManager workerManager = null;
		CarManager carManager = null;
		CustomerOrderManager customerOrderManager = null;
		
		try {
			ctx = new InitialContext();
			workerManager = (WorkerManager) ctx.lookup("java:app/workshop-ejb/DefaultWorkerManager");
			carManager = (CarManager) ctx.lookup("java:app/workshop-ejb/DefaultCarManager");
			customerOrderManager = (CustomerOrderManager) ctx.lookup("java:app/workshop-ejb/DefaultCustomerOrderManager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
        String description = request.getParameter("description");
        Double price = Double.parseDouble(request.getParameter("price"));
        Long idWorker = Long.parseLong(request.getParameter("idWorker"));
        Long idCar = Long.parseLong(request.getParameter("idCar"));
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setDescription(description);
        customerOrder.setPrice(price);
        
        long millis = System.currentTimeMillis();
        Date date = new java.util.Date(millis);
        customerOrder.setDate(date);
        customerOrder.setStatus("new");
        
        Worker worker = null;
		try {
			worker = workerManager.findWorker(idWorker);
		} catch (WorkerNotFoundException e) {
			e.printStackTrace();
		}
        customerOrder.setWorker(worker);
        
        Car car = null;
		try {
			car = carManager.findCar(idCar);
		} catch (CarNotFoundException e) {
			e.printStackTrace();
		}
        customerOrder.setCar(car);
        
        customerOrderManager.persistCustomerOrder(customerOrder);
    }
}