package ch.cern.cmms.wshub.beans;

import javax.annotation.Resource;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.client.InforContext;
import org.jboss.logging.Logger.Level;

import ch.cern.cmms.wshub.entities.AsyncExecution;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.cmms.wshub.tools.Tools;

@Dependent
public class MessageSender {

	@Inject
	@JMSConnectionFactory("java:/ConnectionFactory")
	private JMSContext jmsContext;
	@Resource(lookup="java:/jms/ayncWSQueue")
	private Queue destination;
	@Inject
	private Tools tools;
	@Inject
	private InforClient inforClient;
	
	public String executeAsync(InforContext context, AsyncExecution asyncExecution) throws InforException {
		
		//TODO Basic validation
		/*
		if (asyncExecution.getAsyncOperations() == null 
				|| asyncExecution.geteMailNotification() == null
				|| asyncExecution.geteMailNotification().getRecepient() == null
				|| asyncExecution.geteMailNotification().getRecepient().trim().equals("")
				|| asyncExecution.geteMailNotification().getSubject() == null
				|| asyncExecution.geteMailNotification().getSubject().trim().equals("")
				|| asyncExecution.geteMailNotification().getContent() == null
				|| asyncExecution.geteMailNotification().getContent().trim().equals("")) {
			throw inforClient.getTools().generateFault("Please supply all parameters.");
		} */
		// Send the message to the queue
		try {
			JMSProducer producer = jmsContext.createProducer();
			ObjectMessage objectMessage = jmsContext.createObjectMessage();
			objectMessage.setObject(asyncExecution);
			if (context.getCredentials() != null) {
				objectMessage.setStringProperty("username", context.getCredentials().getUsername());
				objectMessage.setStringProperty("password", context.getCredentials().getPassword());
			}
			producer.send(destination, objectMessage);
		} catch (Exception e) {
			tools.log(Level.FATAL, "Couldn't invoke the message bean: " + e.getMessage());
		}
		
		return "Request has been placed in the queue.";
	}
	
}
