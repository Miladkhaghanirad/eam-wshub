package ch.cern.cmms.wshub.beans;

import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.xml.bind.JAXB;
import org.jboss.logging.Logger.Level;

import ch.cern.eam.wshub.core.services.entities.Credentials;
import ch.cern.cmms.wshub.entities.AsyncExecution;
import ch.cern.cmms.wshub.entities.AsyncOperation;
import ch.cern.cmms.wshub.tools.Tools;

/**
 * Message-Driven Bean implementation class for: AsyncWSHandler
 */

@MessageDriven(
		activationConfig = { 
				@ActivationConfigProperty(
						propertyName = "destination", propertyValue = "java:/jms/ayncWSQueue"), 
				@ActivationConfigProperty(
						propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
				@ActivationConfigProperty(
						propertyName = "useGlobalPools", propertyValue = "false"),
				@ActivationConfigProperty(
						propertyName = "threadPoolMaxSize", propertyValue = "20")
		})
public class AsyncWSHandlerBean implements MessageListener {

	@EJB
	private WSHub wshub;
	@Inject
	private Tools tools;

	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void onMessage(Message message) {
		// Fetch required parameters
		AsyncExecution asyncExecution = null;
		Credentials credentials = new Credentials();
		Class<?> cls = null;
		Boolean exceptionOccured = false;
		try {
			asyncExecution = ((AsyncExecution)((ObjectMessage)message).getObject());
			credentials.setUsername((String)message.getObjectProperty("username")); 
			credentials.setPassword((String)message.getObjectProperty("password")); 
			cls = Class.forName("ch.cern.cmms.wshub.beans.WSHub");
		} catch (Exception e) {
			tools.log(Level.FATAL, "ASYNC JMS Exception: " + e.getMessage());
		}

		// Invoke all operations present in asyncExecution.asynOperations
		for (AsyncOperation operation : asyncExecution.getAsyncOperations()) {
			for (Method m: cls.getDeclaredMethods()) {
				// Find the operation first
				if (m.getName().equals(operation.getOperation())) {
					try {
						// Suspend the operation for the duration defined in the delay
						Thread.sleep(operation.getDelay());
						m.invoke(wshub, JAXB.unmarshal(new StringReader(operation.getData()), m.getParameters()[0].getType()), credentials, null);
						break;
					} catch (InvocationTargetException e) {
						exceptionOccured = true;
						tools.log(Level.FATAL, "ASYNC JMS Exception: " + e.getMessage());
						/*
						EMailNotification eMailNotification = asyncExecution.geteMailNotification();
						String additionalContent = eMailNotification.getAdditionalContent() + 
								"\n\nOperation: " + operation.getOperation() + 
								"\nData: " + JAXB.unmarshal(new StringReader(operation.getData()), m.getParameters()[0].getType()).toString() + 
								"\nError: " + e.getTargetException().getMessage(); 
						eMailNotification.setAdditionalContent(additionalContent);
						*/
					} catch(IllegalAccessException e ) {
						// Accessing private method ... ?
						tools.log(Level.WARN, "ASYNC IllegalAccessException: " + e.getMessage());
					} catch ( javax.xml.bind.DataBindingException e) {
						tools.log(Level.WARN, "ASYNC DataBindingException: " + e.getMessage());
					} catch (Exception e) {
						tools.log(Level.WARN, "ASYNC Exception: " + e.getMessage());
					}
				}
			}
		}
		// In case exceptions were raised send and e-mail. 
		if (exceptionOccured) {
			//TODO
			//tools.sendEMailNotification(asyncExecution.geteMailNotification());
		}

	}

}
