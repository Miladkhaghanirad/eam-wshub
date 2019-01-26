package ch.cern.cmms.wshub.tools;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger.Level;

@ApplicationScoped
public class Tools {

	private org.jboss.logging.Logger logger;

	@PostConstruct
	private void init() {
		logger = org.jboss.logging.Logger.getLogger("wshublogger");
	}

	public boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}

	public void log(String message) {
		this.log(Level.INFO, message);
	}

	public void log(Level logLevel, String message) {
		if (logger.isEnabled(logLevel)) {
			logger.log(logLevel, message);
			if (logLevel == Level.FATAL) {
				//TODO // Notify per e-mail in case of fatalities
				logger.log(logLevel, message);
			}
		}
	}

	public boolean isDebugEnabled() {
		return logger.isEnabled(Level.DEBUG);
	}

}
