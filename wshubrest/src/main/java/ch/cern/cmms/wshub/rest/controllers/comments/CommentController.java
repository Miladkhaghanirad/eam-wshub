package ch.cern.cmms.wshub.rest.controllers.comments;

import ch.cern.cmms.wshub.rest.controllers.WSHubController;
import ch.cern.cmms.wshub.rest.tools.AuthenticationTools;
import ch.cern.eam.wshub.core.client.InforClient;
import ch.cern.eam.wshub.core.services.entities.Comment;
import ch.cern.eam.wshub.core.tools.InforException;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/comments")
public class CommentController extends WSHubController {

    @Inject
    private AuthenticationTools authentication;

    @Inject
    private InforClient inforClient;

    @POST
    @Path("/")
    @Produces("application/json")
    @Consumes("application/json")
    public Response createComment(Comment comment) {
        try {
            return ok(inforClient.getCommentService().createComment(authentication.getInforContext(), comment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

    @GET
    @Path("/{entityCode}/{entityKeyCode}")
    @Produces("application/json")
    public Response readComments(@PathParam("entityCode") String entityCode,
                                 @PathParam("entityKeyCode") String entityKeyCode) {
        try {
            return ok(inforClient.getCommentService().readComments(authentication.getInforContext(), entityCode, entityKeyCode, "*"));
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
    public Response updatePart(Comment comment) {
        try {
            return ok(inforClient.getCommentService().updateComment(authentication.getInforContext(), comment));
        } catch (InforException e) {
            return badRequest(e);
        } catch(Exception e) {
            return serverError(e);
        }
    }

}
