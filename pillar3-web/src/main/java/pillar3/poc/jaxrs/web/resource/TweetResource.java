package pillar3.poc.jaxrs.web.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.net.UnknownHostException;
import java.util.regex.Pattern;

@Singleton
@Path("/tweet")
public class TweetResource {

    static final String TWEETS_COLLECTION = "tweets";
    private final DBCollection tweetsCollections;

    @Inject
    public TweetResource(DB tweetDB) throws UnknownHostException {
        tweetsCollections = tweetDB.getCollection(TWEETS_COLLECTION);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String find() {
        return JSON.serialize(tweetsCollections.find());
    }

    @GET
    @Path("/{toMatch}")
    @Produces(MediaType.APPLICATION_JSON)
    public String find(@PathParam("toMatch") String toMatch) {
        Pattern pattern = Pattern.compile(toMatch, Pattern.CASE_INSENSITIVE);
        return JSON.serialize(tweetsCollections.find(new BasicDBObject("text", pattern)));
    }
}
