package applicaton.workshop.web.view.home;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.event.TabChangeEvent;
import org.primefaces.event.TabCloseEvent;

@ManagedBean
@ViewScoped
public class OrderTabbedView implements Serializable {
	private static final long serialVersionUID = 5973032251684198005L;

	public void onTabChange(TabChangeEvent event) {
        FacesMessage message = new FacesMessage("Zmienieno modu³", "Aktywny modu³: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
         
    public void onTabClose(TabCloseEvent event) {
        FacesMessage message = new FacesMessage("Zamkniêto modu³", "Zamkniêty modu³: " + event.getTab().getTitle());
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
}