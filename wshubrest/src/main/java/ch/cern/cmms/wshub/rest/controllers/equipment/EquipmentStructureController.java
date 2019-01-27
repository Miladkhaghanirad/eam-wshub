package ch.cern.cmms.wshub.rest.controllers.equipment;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentStructure;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/equipmentstructure")
public class EquipmentStructureController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response addEquipmentToStructure(EquipmentStructure equipmentStructure) {
        try {
            inforClient.getEquipmentStructureService().addEquipmentToStructure(authentication.getInforContext(), equipmentStructure);
            return noConent();
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateEquipmentStructure(EquipmentStructure equipmentStructure) {
        try {
            return ok(inforClient.getEquipmentStructureService().updateEquipmentStructure(authentication.getInforContext(), equipmentStructure));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @DELETE
    @Produces("application/json")
    @Consumes("application/json")
    public Response removeEquipmentFromStructure(EquipmentStructure equipmentStructure) {
        try {
            return ok(inforClient.getEquipmentStructureService().removeEquipmentFromStructure(authentication.getInforContext(), equipmentStructure));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
