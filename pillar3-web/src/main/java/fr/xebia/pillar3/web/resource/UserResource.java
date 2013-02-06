package fr.xebia.pillar3.web.resource;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.xebia.pillar3.model.User;
import fr.xebia.pillar3.repository.UserRepository;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/users")
public class UserResource {

    private UserRepository userRepository;

    @Inject
    public UserResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/login")
    public Response login(@FormParam("login") String login, @FormParam("city") String city) {
        User user = userRepository.findOrCreateUser(login, city);

        String json = new Gson().toJson(user);

        return Response.ok(json).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/artist")
    public Response addArtist(@FormParam("login") String login, @FormParam("name") String name) {
        User user = userRepository.findByLogin(login);

        String json = new Gson().toJson(user);

        return Response.ok(json).build();
    }

}
