package ch.cern.cmms.wshub.base;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.xml.ws.Holder;

import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.client.InforContext;
import ch.cern.eam.wshub.core.services.entities.EAMUser;
import ch.cern.cmms.wshub.entities.UserDepartment;
import ch.cern.cmms.wshub.tools.ApplicationData;
import ch.cern.eam.wshub.core.tools.InforException;
import ch.cern.cmms.wshub.tools.Tools;
import net.datastream.schemas.mp_functions.SessionType;
import net.datastream.schemas.mp_functions.mp9532_001.MP9532_RunEmptyOp_001;

@ApplicationScoped
public class Base {

	@Inject
	private InforClient inforClient;

	@Inject
	private Tools tools;
	@Inject
	private ApplicationData applicationData;

	public String login(InforContext context) throws InforException {
		MP9532_RunEmptyOp_001 runEmptyOp = new MP9532_RunEmptyOp_001();
		if (context != null && context.getCredentials() != null) {
			return inforClient.getInforWebServicesToolkitClient().runEmptyOpOp(runEmptyOp, applicationData.getOrganization(),
					inforClient.getTools().createSecurityHeader(context), "", null, null,
					applicationData.getTenant()).getResultData();
		} else {
			inforClient.getInforWebServicesToolkitClient().runEmptyOpOp(runEmptyOp, applicationData.getOrganization(), null, null,
					new Holder<SessionType>(inforClient.getTools().createInforSession(context)), null, applicationData.getTenant());
			return "OK";
		}
	}

	public EAMUser getUserInfo(String userCode) throws InforException {
		if (userCode == null || userCode.trim().equals("")) {
			throw inforClient.getTools().generateFault("Usercode can not be empty.");
		}
		EAMUser eamUser = null;
		EntityManager em = tools.getEntityManager();
		try {
			// Load the user
			eamUser = em.find(EAMUser.class, userCode.toUpperCase());
			// If the user is found, load its departments
			if (eamUser != null) {
				List<UserDepartment> departments = em
						.createNamedQuery(UserDepartment.GET_USER_DEPARTMENTS, UserDepartment.class)
						.setParameter("userId", userCode).getResultList();
				// Check the result
				if (departments != null && !departments.isEmpty()) {
					// Assign list of departments to the user
					eamUser.setUserDepartments(
							departments.stream().map(UserDepartment::getMrcCode).collect(Collectors.toList()));
				}
				try {
					// Read the CERN ID Prop "0002", entity = "USER"
					String cernId = (String) em
							.createNativeQuery("select PRV_VALUE from R5PROPERTYVALUES where"
									+ " PRV_PROPERTY = '0002' AND PRV_RENTITY = 'USER' AND PRV_CODE = :eamUser")
							.setParameter("eamUser", userCode.toUpperCase()).getSingleResult();
					// Assign Cern Id
					eamUser.setCernId(cernId);
				} catch (Exception e) {
					/* No result */
				}
			}
		} catch (IllegalArgumentException | NullPointerException exception) {
			throw inforClient.getTools().generateFault("The user couldn't be found.");
		} finally {
			em.close();
		}
		return eamUser;
	}

}
