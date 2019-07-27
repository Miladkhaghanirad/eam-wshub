package ch.cern.cmms.wshub.tools;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.cmms.wshub.handlers.SOAPHandlerResolver;
import ch.cern.eam.wshub.core.tools.InforException;
import com.sun.xml.bind.v2.runtime.unmarshaller.UnmarshallingContext;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.logging.Level;

@ApplicationScoped
public class InforClientProducer {

	@Produces
	private InforClient inforClient;
	@Inject
	private ApplicationData applicationData;
	@Resource
	private ManagedExecutorService executorService;
	
	@PostConstruct
	public void init() throws InforException {
		inforClient = new InforClient.Builder(applicationData.getInforWSURL(), applicationData.getTenant())
				.withSOAPHandlerResolver(new SOAPHandlerResolver())
				.withExecutorService(executorService)
				.build();

		//TODO setUnmarshallingContextErrorsCounter();
	}

	/*
	private void setUnmarshallingContextErrorsCounter() {
		try {
			inforClient.getTools().log(Level.INFO, "Setting UnmarshallingContext.errorsCounter to -1 to avoid unmarshaller errors about unknown properties.");
			Field field = UnmarshallingContext.class.getDeclaredField("errorsCounter");
			field.setAccessible(true);
			field.setInt(null, -1);
		} catch (Exception exception) {
			inforClient.getTools().log(Level.SEVERE, "Couldn't set UnmarshallingContext.errorsCounter: " + exception.getMessage());
		}
	}
	*/

}