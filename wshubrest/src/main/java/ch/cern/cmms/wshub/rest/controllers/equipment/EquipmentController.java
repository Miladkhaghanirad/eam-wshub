package ch.cern.cmms.wshub.rest.controllers.equipment;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.equipment.entities.Equipment;
import ch.cern.eam.wshub.core.tools.InforException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/equipment")
public class EquipmentController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @GET
    @Path("/{code}")
    @Produces("application/json")
    public Response readEquipment(@PathParam("code") String code) {
        try {
            return ok(inforClient.getEquipmentFacadeService().readEquipment(authentication.getInforContext(), code));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Path("/{code}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateEquipment(@PathParam("code") String code, Equipment workOrder) {
        try {
            workOrder.setCode(code);
            return ok(inforClient.getEquipmentFacadeService().updateEquipment(authentication.getInforContext(), workOrder));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createEquipment(Equipment equipment) {
        try {
            return ok(inforClient.getEquipmentFacadeService().createEquipment(authentication.getInforContext(), equipment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @DELETE
    @Path("/{code}")
    @Produces("application/json")
    public Response deleteEquipment(@PathParam("code") String code) {
        try {
            inforClient.getEquipmentFacadeService().deleteEquipment(authentication.getInforContext(), code);
            return noConent();
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    //
    // BATCH PROCESSING
    //

    @GET
    @Path("/list/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response readEquipment(List<String> equipment) {
        try {
            return ok(inforClient.getEquipmentFacadeService().readEquipmentBatch(authentication.getInforContext(), equipment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @POST
    @Path("/list/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createEquipment(List<Equipment> equipment) {
        try {
            return ok(inforClient.getEquipmentFacadeService().createEquipmentBatch(authentication.getInforContext(), equipment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Path("/list/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateEquipments(List<Equipment> equipment) {
        try {
            return ok(inforClient.getEquipmentFacadeService().updateEquipmentBatch(authentication.getInforContext(), equipment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
