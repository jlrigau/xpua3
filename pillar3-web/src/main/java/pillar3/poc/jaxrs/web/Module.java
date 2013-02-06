package pillar3.poc.jaxrs.web;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.mongodb.DB;
import com.mongodb.Mongo;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.oauth.OAuthService;

import java.net.UnknownHostException;

public class Module extends AbstractModule {

    @Override
    protected void configure() {

    }

    @Provides
    public DB createMongoDb() throws UnknownHostException {
        return new Mongo().getDB("tweetDb");
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
