package ch.cern.cmms.wshub.rest.controllers.equipment;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentPMSchedule;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentStructure;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/equipment/pmschedules")
public class PMScheduleController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createEquipmentPMSchedule(EquipmentPMSchedule equipmentPMSchedule) {
        try {
            return ok(inforClient.getPmScheduleService().createEquipmentPMSchedule(authentication.getInforContext(), equipmentPMSchedule));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @PUT
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateEquipmentPMSchedule(EquipmentPMSchedule equipmentPMSchedule) {
        try {
            return ok(inforClient.getPmScheduleService().updateEquipmentPMSchedule(authentication.getInforContext(), equipmentPMSchedule));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @DELETE
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response deleteEquipmentPMSchedule(EquipmentPMSchedule equipmentPMSchedule) {
        try {
            inforClient.getPmScheduleService().deleteEquipmentPMSchedule(authentication.getInforContext(), equipmentPMSchedule);
            return noConent();
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
