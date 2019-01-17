package ch.cern.cmms.wshub.tools;

import java.sql.Connection;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.sql.DataSource;
import org.jboss.logging.Logger.Level;

@ApplicationScoped
public class Tools {

	private org.jboss.logging.Logger logger;

	@PostConstruct
	private void init() {
		logger = org.jboss.logging.Logger.getLogger("wshublogger");
	}

	@Resource
	private DataSource datasource;
	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	public DataSource getDatasource() {
		return datasource;
	}

	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	public boolean isEmpty(String value) {
		return value == null || value.trim().equals("");
	}

	public static boolean isNullOrEmpty(String str) {
		return str == null || str.isEmpty();
	}
	/**
	 * Gets the connection with the database
	 * 
	 * @return The connection with the database
	 */
	public Connection getConnection() {
		try {
			return datasource.getConnection();
		} catch (Exception e) {/* Error getting connection */
			log(Level.FATAL, "Couldn't get the DB connection: " + e.getMessage());
		}
		return null;
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
