package fr.xebia.pillar3.web.resource;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import fr.xebia.pillar3.model.Concert;
import fr.xebia.pillar3.model.User;
import fr.xebia.pillar3.repository.UserRepository;
import org.joda.time.DateTime;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Singleton
@Path("/concerts")
public class ConcertResource {

    UserRepository userRepository;
    private Random random = new Random();

    @Inject
    public ConcertResource(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{artistName}")
    public Response findConcerts(@CookieParam("login") String login, @PathParam("name") String artistName) {
        List<Concert> concerts = Lists.newArrayList();
        for (int i = 0; i < random.nextInt() % 15; i++) {
            Date date = generateDateInto2NextWeeks().toDate();
            User user = userRepository.findByLogin(login);
            double latitude;
            if (random.nextBoolean()) {
                latitude = user.latitude + (Math.random() / 2);
            } else {
                latitude = user.latitude - (Math.random() / 2);
            }
            double longitude;
            if (random.nextBoolean()) {
                longitude = user.longitude + (Math.random() / 2);
            } else {
                longitude = user.longitude - (Math.random() / 2);
            }
            concerts.add(new Concert(artistName, date, longitude, latitude));
        }
        return Response.ok(new Gson().toJson(concerts)).build();
    }

    private DateTime generateDateInto2NextWeeks() {
        DateTime now = new DateTime();
        return now.plusDays(((int) (Math.random() * 15d)));
    }

}
