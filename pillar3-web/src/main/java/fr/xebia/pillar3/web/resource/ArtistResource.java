package fr.xebia.pillar3.web.resource;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.xebia.pillar3.model.User;
import fr.xebia.pillar3.repository.UserRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("/artists")
public class ArtistResource {

    private UserRepository userRepository;

    @Inject
    public ArtistResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    @Path("/{artist}/fans")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findFans(@PathParam("artist") String artist) {
        List<User> fans = userRepository.findFansOf(artist);
        String json = new Gson().toJson(fans);
        return Response.ok(json).build();
    }

}
