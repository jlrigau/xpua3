package fr.xebia.pillar3.web;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.oauth.OAuthService;

import java.net.UnknownHostException;

public class Module extends AbstractModule {

    static final String USERS_COLLECTION = "users";

    @Override
    protected void configure() {

    }

    @Provides
    public DBCollection createUsersCollection() throws UnknownHostException {
        DBCollection collection = new Mongo().getDB("yawl").getCollection(USERS_COLLECTION);

        collection.ensureIndex("artists");

        return collection;
        /*String host = "tempest.mongohq.com";
        int port = 10052;

        DB db = new Mongo(host, port).getDB("Yloh05kyeaoxxkLK6OXQ");

        String username = "xpua3";
        String password = "xpua3";

        db.authenticate(username, password.toCharArray());

        return db.getCollection(USERS_COLLECTION);*/
    }

    @Provides
    private OAuthService createOAuthServiceForTwitter() {
        return new ServiceBuilder()
                .provider(TwitterApi.class)
                .apiKey("6EK0Es2zfIx4SHaazCNuGg")
                .apiSecret("NJW1RaSzylevlg0Awfv00mRsmr0Tiq3eyRgojHzA")
                .callback("http://x.x.x.x:8080/login/twitter/verify/")
                .build();
    }

}
