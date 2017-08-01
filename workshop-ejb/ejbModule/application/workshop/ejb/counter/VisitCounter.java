package application.workshop.ejb.counter;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
 
@Startup
@Singleton
public class VisitCounter {
    private Long visits;
     
    @PostConstruct
    private void init() {
        visits = new Long(0);
    }
     
    public Long getVisits() {
        return visits;
    }
    
    public Long getVisitsAndIncrement() {
        return ++visits;
    }
}