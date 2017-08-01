package application.workshop.web.view.admin;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

@ManagedBean
@ViewScoped
public class WorkerTabbedView implements Serializable {
	private static final long serialVersionUID = -6581943407302621942L;

	public void onTabChange(TabChangeEvent event) {
        FacesMessage message = new FacesMessage("Zmienieno modu³", "Aktywny modu³: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
         
    public void onTabClose(TabCloseEvent event) {
        FacesMessage message = new FacesMessage("Zamkniêto modu³", "Zamkniêty modu³: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}