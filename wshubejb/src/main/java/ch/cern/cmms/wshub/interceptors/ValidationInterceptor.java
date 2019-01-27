package ch.cern.cmms.wshub.interceptors;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.client.InforContext;
import org.jboss.logging.Logger.Level;
import ch.cern.cmms.wshub.tools.Tools;

@Dependent
public class ValidationInterceptor {

	@Inject
	private Tools tools;
	@Inject
	private InforClient inforClient;
	
	@AroundInvoke
	public Object verifyCredentialsAndSessionID(InvocationContext ic) throws Exception {
		InforContext inforContext;
		try {
			inforContext = (InforContext) ic.getParameters()[0];
		} catch (Exception e) {
			tools.log(Level.FATAL, "Couldn't fetch credentials and session ID");
			throw inforClient.getTools().generateFault("Couldn't fetch credentials and session ID");
		}
		// Verify the credentials
		if (inforContext != null &&
				inforContext.getCredentials() != null &&
				inforContext.getCredentials().getPassword() != null &&
				!inforContext.getCredentials().getPassword().trim().equals("") &&
				inforContext.getCredentials().getUsername() != null &&
				!inforContext.getCredentials().getUsername().trim().equals("")) {
			inforContext.getCredentials().setUsername(inforContext.getCredentials().getUsername().trim().toUpperCase());
			return ic.proceed();
		}
		else if (inforContext != null && inforContext.getSessionID() != null && !inforContext.getSessionID().trim().equals("")) {
			return ic.proceed();
		}
		else {
			throw inforClient.getTools().generateFault("Please supply valid credentials or a valid session ID.");
		}
	}

}
