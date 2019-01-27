package ch.cern.cmms.wshub.rest.controllers.equipment;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.equipment.entities.Equipment;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/positions")
public class PositionController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createPosition(Equipment equipment) {
        try {
            return ok(inforClient.getPositionService().createPosition(authentication.getInforContext(), equipment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @GET
    @Path("/{code}")
    @Produces("application/json")
    public Response readPosition(@PathParam("code") String code) {
        try {
            return ok(inforClient.getPositionService().readPosition(authentication.getInforContext(), code));
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
    public Response updatePosition(@PathParam("code") String code, Equipment equipment) {
        try {
            equipment.setCode(code);
            return ok(inforClient.getPositionService().updatePosition(authentication.getInforContext(), equipment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @DELETE
    @Path("/{code}")
    @Produces("application/json")
    public Response deletePosition(@PathParam("code") String code) {
        try {
            inforClient.getPositionService().deletePosition(authentication.getInforContext(), code);
            return noConent();
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
