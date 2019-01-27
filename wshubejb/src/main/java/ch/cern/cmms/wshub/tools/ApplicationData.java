package ch.cern.cmms.wshub.tools;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationData {

	//
	// Getters for individual properties
	//
	public String getTenant() {
		return getVariableValue("INFOR_TENANT");
	}

	public String getOrganization() {
			return getVariableValue("INFOR_ORGANIZATION_CODE");
	}

	public String getInforWSURL() {
		return getVariableValue("INFOR_WS_URL");
	}

	/**
	 * Fetch a variable from the following sources:
	 *  - environment variable
	 *  - application.xml
	 * @param variableName
	 * @return
	 */
	private String getVariableValue(String variableName) {
		String valueFromEnv = System.getenv().get(variableName);
		if (valueFromEnv != null && !valueFromEnv.isEmpty()) {
			return valueFromEnv;
		} else {
			return null;
		}
	}

}
