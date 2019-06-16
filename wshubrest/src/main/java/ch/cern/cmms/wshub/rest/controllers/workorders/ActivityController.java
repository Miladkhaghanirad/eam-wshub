package ch.cern.cmms.wshub.rest.controllers.workorders;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.workorders.entities.Activity;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/workorders/activities")
public class ActivityController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @GET
    @Path("/{number}")
    @Produces("application/json")
    public Response readWorkOrderActivities(@PathParam("number") String workOrderNumber) {
        try {
            return ok(inforClient.getLaborBookingService().readActivities(authentication.getInforContext(), workOrderNumber, true));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @POST
    @Path("/{number}")
    @Produces("application/json")
    public Response createWorkOrderActivity(@PathParam("number") String workOrderNumber, Activity activity) {
        try {
            activity.setWorkOrderNumber(workOrderNumber);
            return ok(inforClient.getLaborBookingService().createActivity(authentication.getInforContext(), activity));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Path("/{number}")
    @Produces("application/json")
    public Response updateWorkOrderActivity(@PathParam("number") String workOrderNumber, Activity activity) {
        try {
            activity.setWorkOrderNumber(workOrderNumber);
            return ok(inforClient.getLaborBookingService().updateActivity(authentication.getInforContext(), activity));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
