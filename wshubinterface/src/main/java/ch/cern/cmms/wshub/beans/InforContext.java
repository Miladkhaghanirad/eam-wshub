package ch.cern.cmms.wshub.beans;

import ch.cern.eam.wshub.core.services.entities.Credentials;

import java.io.Serializable;

public class InforContext implements Serializable {

    private Credentials credentials;
    private String sessionID;
    private String organizationCode;

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public String getSessionID() {
        return sessionID;
    }

    public void setSessionID(String sessionID) {
        this.sessionID = sessionID;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }
}
