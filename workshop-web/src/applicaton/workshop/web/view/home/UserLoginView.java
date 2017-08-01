package applicaton.workshop.web.view.home;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import application.workshop.api.manager.WorkerManager;
import application.workshop.model.Worker;

@ManagedBean
@SessionScoped
public class UserLoginView implements Serializable {
	private static final long serialVersionUID = 7469529183077233288L;
	
	private Boolean loggedIn = false;
    private String username;
    private String password;
    private Worker worker;
    
    @EJB
    private WorkerManager workerManager;

	
	public String hashPassword(String password) throws NoSuchAlgorithmException {
		MessageDigest messageDigest = MessageDigest.getInstance("MD5");
		messageDigest.reset();
		messageDigest.update(password.getBytes());
		byte[] digest = messageDigest.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		password = bigInt.toString(16);
		return password;
	}

    public void login() throws IOException, NoSuchAlgorithmException {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        
        List<Worker> workers = workerManager.findAllWorkers();
        String hashPassword = hashPassword(password);
        for (Worker i : workers) {
			if (i.getUsername().equals(username) && i.getPassword().equals(hashPassword)) {
					worker = i;
					loggedIn = true;
					break;
			}
		}
        
        try {
            request.login(username, password);
            externalContext.getSessionMap().put("user", worker);
            externalContext.redirect(externalContext.getRequestContextPath() + "/navigation/home.jsf");
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Unknown login"));
        }
    }

    public void logout() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.invalidateSession();
        externalContext.redirect(externalContext.getRequestContextPath() + "/navigation/home.jsf");
        loggedIn = false;
    }

    
	public Boolean getLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
	
    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Worker getWorker() {
		return worker;
	}

	public void setWorker(Worker worker) {
		this.worker = worker;
	}
}
