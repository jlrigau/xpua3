package pillar3.poc.jaxrs.web.resource;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.Mongo;
import de.flapdoodle.embedmongo.MongoDBRuntime;
import de.flapdoodle.embedmongo.MongodProcess;
import de.flapdoodle.embedmongo.config.MongodConfig;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

import static com.jayway.restassured.RestAssured.given;
import static de.flapdoodle.embedmongo.distribution.Version.V2_1_0;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;


public class TweetResourceIntegrationTest {

    public static final String MONGO_HOST = "localhost";
    public static final int MONGO_PORT = 27018;
    public static final int HTTP_PORT = 8022;

    private static MongodProcess mongod;
    private static DB tweetDB;

    private static AppServer appServer = new AppServer(HTTP_PORT, new AbstractModule() {
        @Override
        protected void configure() {

        }

        @Provides
        public DB createMongoBase() throws UnknownHostException {
            return tweetDB;
        }
    });

    @BeforeClass
    public static void beforeClass() throws IOException {
        mongod = MongoDBRuntime.getDefaultInstance().prepare(new MongodConfig(V2_1_0, MONGO_PORT, false)).start();
        tweetDB = new Mongo(MONGO_HOST, MONGO_PORT).getDB("tweetDBtest");
        appServer.start();
    }

    @Before
    public void before() throws IOException {
        tweetDB.getCollection("tweets").drop();
    }

    @Test
    public void matchTextOfTweet() throws UnknownHostException {
        injectTweetAsJson("{from_user : \"mrenou42\", text : \"this is a hype tweet\"}");
        injectTweetAsJson("{from_user : \"billoute42\", text : \"this is a bad tweet\"}");
        injectTweetAsJson("{from_user : \"joe42\", text : \"this is a bomb tweet\"}");
        injectTweetAsJson("{from_user : \"coco42\", text : \"this is a love tweet\"}");

        given().port(appServer.getPort())
                .expect().body("$", hasSize(1))
                .expect().body("[0].text", equalTo("this is a hype tweet"))
                .expect().body("[0].from_user", equalTo("mrenou42"))
                .when().get("/tweet/hype");
    }

    @Test
    public void findAllTweets() throws UnknownHostException {
        injectTweetAsJson("{from_user : \"mrenou42\", text : \"this is a hype tweet\"}");
        injectTweetAsJson("{from_user : \"billoute42\", text : \"this is a bad tweet\"}");
        injectTweetAsJson("{from_user : \"joe42\", text : \"this is a bomb tweet\"}");
        injectTweetAsJson("{from_user : \"coco42\", text : \"this is a love tweet\"}");

        given().port(appServer.getPort())
                .expect().body("$", hasSize(4))
                .when().get("/tweet/");
    }

    @AfterClass
    public static void afterClass() throws IOException {
        appServer.stop();
        mongod.stop();
    }

    private void injectTweetAsJson(String json) throws UnknownHostException {
        //tweetDB.getCollection(TWEETS_COLLECTION).insert((BasicDBObject) JSON.parse(json));
    }
}
