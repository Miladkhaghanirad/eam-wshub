package ch.cern.cmms.wshub.rest.controllers.workorders;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.workorders.entities.Activity;
import ch.cern.eam.wshub.core.services.workorders.entities.LaborBooking;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/workorders/laborbookings")
public class LaborBookingController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @GET
    @Path("/{number}")
    @Produces("application/json")
    public Response readWorkOrderLaborBooking(@PathParam("number") String workOrderNumber) {
        try {
            return ok(inforClient.getLaborBookingService().readLaborBookings(authentication.getInforContext(), workOrderNumber));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @POST
    @Path("/{number}")
    @Produces("application/json")
    public Response createWorkOrderLaborBooking(@PathParam("number") String workOrderNumber, LaborBooking laborBooking) {
        try {
            laborBooking.setWorkOrderNumber(workOrderNumber);
            return ok(inforClient.getLaborBookingService().createLaborBooking(authentication.getInforContext(), laborBooking));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
