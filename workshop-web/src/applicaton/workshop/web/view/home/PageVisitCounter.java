package applicaton.workshop.web.view.home;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import application.workshop.ejb.counter.VisitCounter;

@ManagedBean
@SessionScoped
public class PageVisitCounter implements Serializable {
	private static final long serialVersionUID = -8648356046576726345L;
	
	@EJB
	private VisitCounter visitCounter;
	
	public Long getVisitNumber() {
		return visitCounter.getVisits();
	}
	
	@PostConstruct
	public void init() {
		visitCounter.getVisitsAndIncrement();
	}
}
