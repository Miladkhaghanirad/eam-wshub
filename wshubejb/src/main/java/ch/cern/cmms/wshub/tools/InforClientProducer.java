package ch.cern.cmms.wshub.tools;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.cmms.wshub.handlers.SOAPHandlerResolver;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

@ApplicationScoped
public class InforClientProducer {

	@Produces
	private InforClient inforClient;
	@Inject
	private ApplicationData applicationData;
	//@Resource
	//private DataSource datasource;
	//@PersistenceUnit
	//private EntityManagerFactory entityManagerFactory;
	@Resource
	private ManagedExecutorService executorService;
	
	@PostConstruct
	public void init() throws InforException {
		inforClient = new InforClient.Builder(applicationData.getInforWSURL(), applicationData.getTenant())
				.withDefaultOrganizationCode("*")
				.withSOAPHandlerResolver(new SOAPHandlerResolver())
				//.withDataSource(datasource)
				//.withEntityManagerFactory(entityManagerFactory)
				.withExecutorService(executorService)
				.build();
	}

}