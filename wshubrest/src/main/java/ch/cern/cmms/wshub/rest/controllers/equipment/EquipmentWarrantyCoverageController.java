package ch.cern.cmms.wshub.rest.controllers.equipment;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentWarranty;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/equipment/warrantiescoverage")
public class EquipmentWarrantyCoverageController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createEquipmentPMSchedule(EquipmentWarranty equipmentWarranty) {
        try {
            return ok(inforClient.getEquipmentWarrantyCoverageService().createEquipmentWarrantyCoverage(authentication.getInforContext(), equipmentWarranty));
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
    public Response updateEquipmentPMSchedule(EquipmentWarranty equipmentWarranty) {
        try {
            return ok(inforClient.getEquipmentWarrantyCoverageService().updateEquipmentWarrantyCoverage(authentication.getInforContext(), equipmentWarranty));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
