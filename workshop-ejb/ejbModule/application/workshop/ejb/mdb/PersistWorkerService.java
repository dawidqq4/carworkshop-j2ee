package application.workshop.ejb.mdb;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import application.workshop.model.Worker;
 
@Stateless
public class PersistWorkerService {
     
	@Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
	
    @Resource(mappedName = "java:/jms/queue/mailsQueue")
    private Queue mailQueue;
     
    public void sendMessage(Worker worker) throws JMSException {
    	Connection connection = connectionFactory.createConnection();
    	Session session = connection.createSession();
    	MessageProducer messageProducer = session.createProducer(mailQueue);
    	ObjectMessage objectMessage = session.createObjectMessage(worker);
    	
    	connection.start();
    	messageProducer.send(objectMessage);
    	session.close();
    	connection.close();
    }
}