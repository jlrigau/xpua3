package fr.xebia.pillar3.web.resource;

import com.google.gson.Gson;
import com.google.inject.Inject;
import fr.xebia.pillar3.repository.NotificationsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Antoine
 * @author Olivier
 */
@Path("/notifications")
public class NotificationsResource {

    NotificationsRepository repository;

    @Inject
    public NotificationsResource(NotificationsRepository repository) {
        this.repository = repository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCachedNotifications() {
        String json = new Gson().toJson(repository.getLatest());
        return Response.ok(json).build();
    }
}
