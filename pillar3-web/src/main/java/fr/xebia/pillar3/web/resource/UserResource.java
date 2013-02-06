package fr.xebia.pillar3.web.resource;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.DBObject;
import fr.xebia.pillar3.model.Artist;
import fr.xebia.pillar3.model.User;
import fr.xebia.pillar3.repository.NotificationsRepository;
import fr.xebia.pillar3.repository.UserRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static fr.xebia.pillar3.repository.NotificationsRepository.newFavoriteArtistNotification;

@Singleton
@Path("/users")
public class UserResource {

    private UserRepository userRepository;

    private NotificationsRepository notificationsRepository;

    @Inject
    public UserResource(UserRepository userRepository, NotificationsRepository notificationsRepository) {
        this.userRepository = userRepository;
        this.notificationsRepository = notificationsRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(@FormParam("login") String login, @FormParam("city") String city) {
        User user = userRepository.findOrCreateUser(login, city);
        String json = new Gson().toJson(user);
        return Response.ok(json).build();
    }

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/artist")
    public Response addArtist(@CookieParam("login") String login, @PathParam("id") String id, @PathParam("name") String name) {
        User user = userRepository.findByLogin(login);

        user.addArtist(new Artist(id, name));

        userRepository.save(user);
        notificationsRepository.add(newFavoriteArtistNotification(login, name));

        String json = new Gson().toJson(user);

        return Response.ok(json).build();
    }

}
