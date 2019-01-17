package ch.cern.cmms.wshub.rest.tools;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.entities.Credentials;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

@RequestScoped
public class AuthenticationTools {

    @Inject
    private HttpServletRequest request;
    @Inject
    private InforClient inforClient;

    public InforContext getInforContext() throws InforException
    {

        String user = request.getHeader("INFOR_USER");
        String password = request.getHeader("INFOR_PASSWORD");
        String organization = request.getHeader("INFOR_ORGANIZATION");
        String sessionid = request.getHeader("INFOR_SESSIONID");

        InforContext.Builder inforContextBuilder = new InforContext.Builder();

        // Organization
        if (organization == null || organization.trim().equals("")) {
            throw inforClient.getTools().generateFault("Organization is required.");
        }
        inforContextBuilder.withOrganizationCode(organization);

        // Credentials, Session ID
        if (notEmpty(user) && notEmpty(password)) {
            Credentials credentials = new Credentials();
            credentials.setUsername(user);
            credentials.setPassword(password);
            inforContextBuilder.withCredentials(credentials);
        } else if (notEmpty(sessionid)) {
            inforContextBuilder.withSessionID(sessionid);
        } else {
            throw inforClient.getTools().generateFault("Credentials or Session ID is required.");
        }

        return inforContextBuilder.build();
    }

    private boolean notEmpty(String string) {
        return string != null && !string.trim().equals("");
    }
}
