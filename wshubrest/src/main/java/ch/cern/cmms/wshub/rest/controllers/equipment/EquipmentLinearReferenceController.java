package ch.cern.cmms.wshub.rest.controllers.equipment;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.equipment.entities.EquipmentWarranty;
import ch.cern.eam.wshub.core.services.equipment.entities.LinearReference;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/equipment/linearreferences")
public class EquipmentLinearReferenceController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createEquipmentLinearReference(LinearReference linearReference) {
        try {
            return ok(inforClient.getLinearReferenceService().createEquipmentLinearReference(authentication.getInforContext(), linearReference));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @PUT
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateEquipmentLinearReference(@PathParam("id") String id, LinearReference linearReference) {
        try {
            linearReference.setID(id);
            return ok(inforClient.getLinearReferenceService().updateEquipmentLinearReference(authentication.getInforContext(), linearReference));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response deleteEquipmentLinearReference(@PathParam("id") String id) {
        try {
            inforClient.getLinearReferenceService().deleteEquipmentLinearReference(authentication.getInforContext(), id);
            return noConent();
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
