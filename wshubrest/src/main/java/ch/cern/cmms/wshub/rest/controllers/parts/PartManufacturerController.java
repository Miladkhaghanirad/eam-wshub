package ch.cern.cmms.wshub.rest.controllers.parts;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.material.entities.Part;
import ch.cern.eam.wshub.core.services.material.entities.PartManufacturer;
import ch.cern.eam.wshub.core.tools.InforException;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/parts")
public class PartManufacturerController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/{partCode}/manufacturers")
    @Consumes("application/json")
    @Produces("application/json")
    public Response addPartManufacturer(@PathParam("partCode") String partCode, PartManufacturer partManufacturer) {
        try {
            partManufacturer.setPartCode(partCode);
            return ok(inforClient.getPartManufacturerService().addPartManufacturer(authentication.getInforContext(), partManufacturer));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @PUT
    @Path("/{partCode}/manufacturers/{manufacturerCode}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response updatePartManufacturer(@PathParam("partCode") String partCode,
                                           @PathParam("manufacturerCode") String manufacturerCode,
                                           PartManufacturer partManufacturer) {
        try {
            partManufacturer.setPartCode(partCode);
            partManufacturer.setManufacturerCode(manufacturerCode);
            return ok(inforClient.getPartManufacturerService().updatePartManufacturer(authentication.getInforContext(), partManufacturer));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @DELETE
    @Path("/{partCode}/manufacturers/{manufacturerCode}")
    @Consumes("application/json")
    @Produces("application/json")
    public Response deletePartManufacturer(@PathParam("partCode") String partCode, @PathParam("manufacturerCode") String manufacturerCode) {
        try {
            PartManufacturer partManufacturer = new PartManufacturer();
            partManufacturer.setPartCode(partCode);
            partManufacturer.setManufacturerCode(manufacturerCode);
            return ok(inforClient.getPartManufacturerService().deletePartManufacturer(authentication.getInforContext(), partManufacturer));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
