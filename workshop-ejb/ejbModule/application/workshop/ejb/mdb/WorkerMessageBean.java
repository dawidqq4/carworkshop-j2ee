package application.workshop.ejb.mdb;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import application.workshop.api.manager.WorkerManager;
import application.workshop.model.Worker;
 
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(
                propertyName = "destination", 
                propertyValue = "java:/jms/queue/mailsQueue"),
})
public class WorkerMessageBean implements MessageListener {
 
	@EJB
	WorkerManager workerManager;
	
    @Override
    public void onMessage(Message message) {
    	ObjectMessage objectMessage = (ObjectMessage) message;
        try { 
        	Worker worker = (Worker) objectMessage.getObject();
        	workerManager.persistWorker(worker);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}