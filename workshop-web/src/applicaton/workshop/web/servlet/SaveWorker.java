package applicaton.workshop.web.servlet;
 
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.workshop.api.manager.WorkerManager;
import application.workshop.model.Worker;
 
@WebServlet("/saveWorker")
public class SaveWorker extends HttpServlet {
    private static final long serialVersionUID = 1L;
        
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
		Context ctx;
		WorkerManager manager = null;
		try {
			ctx = new InitialContext();
			manager = (WorkerManager) ctx.lookup("java:app/workshop-ejb/DefaultWorkerManager");
		} catch (NamingException e) {
			e.printStackTrace();
		}
        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String address = request.getParameter("address");
        String emailAddress =  request.getParameter("emailaddress");
        Integer phone = Integer.parseInt(request.getParameter("phone"));
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Worker worker = new Worker("worker");
        worker.setFirstName(firstName);
        worker.setLastName(lastName);
        worker.setAddress(address);
        worker.setEmailAddress(emailAddress);
        worker.setPhone(phone);
        worker.setUsername(login);
        try {
			worker.setPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		manager.persistWorker(worker);
    }
}