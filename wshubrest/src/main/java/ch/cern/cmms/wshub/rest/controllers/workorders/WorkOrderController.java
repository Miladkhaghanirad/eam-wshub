package ch.cern.cmms.wshub.rest.controllers.workorders;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.workorders.entities.WorkOrder;
import ch.cern.eam.wshub.core.tools.InforException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/workorders")
public class WorkOrderController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @GET
    @Path("/{number}")
    @Produces("application/json")
    public Response readWorkOrder(@PathParam("number") String number) {
        try {
            return ok(inforClient.getWorkOrderService().readWorkOrder(authentication.getInforContext(), number));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Path("/{number}")
    @Produces("application/json")
    @Consumes("application/json")
    public Response updateWorkOrder(@PathParam("number") String number, WorkOrder workOrder) {
        try {
            workOrder.setNumber(number);
            return ok(inforClient.getWorkOrderService().updateWorkOrder(authentication.getInforContext(), workOrder));
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
    public Response createWorkOrder(WorkOrder workOrder) {
        try {
            return ok(inforClient.getWorkOrderService().createWorkOrder(authentication.getInforContext(), workOrder));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }


    @DELETE
    @Path("/{number}")
    @Produces("application/json")
    public Response deleteWorkOrder(@PathParam("number") String number) {
        try {
            inforClient.getWorkOrderService().deleteWorkOrder(authentication.getInforContext(), number);
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
    public Response readWorkOrders(List<String> workOrders) {
        try {
            return ok(inforClient.getWorkOrderService().readWorkOrderBatch(authentication.getInforContext(), workOrders));
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
    public Response createWorkOrders(List<WorkOrder> workOrders) {
        try {
            return ok(inforClient.getWorkOrderService().createWorkOrderBatch(authentication.getInforContext(), workOrders));
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
    public Response updateWorkOrders(List<WorkOrder> workOrders) {
        try {
            return ok(inforClient.getWorkOrderService().updateWorkOrderBatch(authentication.getInforContext(), workOrders));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @DELETE
    @Path("/list/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response deleteWorkOrders(List<String> workOrders) {
        try {
            return ok(inforClient.getWorkOrderService().deleteWorkOrderBatch(authentication.getInforContext(), workOrders));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
