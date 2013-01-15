package pillar3.poc.jaxrs;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

@Path("/tweet")
public class TweetResource {

    private DB tweetDB ;

    public TweetResource() throws UnknownHostException {
        this.tweetDB = new Mongo().getDB("tweetDb");;
    }

    @GET
    @Path("/test")
    @Produces(MediaType.APPLICATION_JSON)
    public String test() {
        return "[ { \"elem1\" : \"val1\" } , { \"elem2\" : \"val2\" } ]";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String find() {
        String json = JSON.serialize(tweetDB.getCollection("tweets").find());
        return json;
    }

    @GET
    @Path("/{toMatch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String find(@PathParam("toMatch") String toMatch) {
        Pattern pattern = Pattern.compile(toMatch, Pattern.CASE_INSENSITIVE);
        String json = JSON.serialize(tweetDB.getCollection("tweets").find(new BasicDBObject("text", pattern)));
        return json;
    }
}
