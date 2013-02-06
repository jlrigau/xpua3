package pillar3.poc.jaxrs.web.resource;

import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import pillar3.poc.jaxrs.repository.UserRepository;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("/login")
public class LoginResource {

    private UserRepository userRepository;

    @Inject
    public LoginResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@FormParam("login") String login, @FormParam("city") String city) {
        String json = new Gson().toJson(userRepository.findOrCreateUser(login, city));
        return Response.ok(json).build();
    }
}
