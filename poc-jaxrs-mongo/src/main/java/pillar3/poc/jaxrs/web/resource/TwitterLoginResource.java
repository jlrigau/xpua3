package pillar3.poc.jaxrs.web.resource;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.scribe.model.*;
import org.scribe.oauth.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response;
import java.net.URI;

import static com.jayway.jsonpath.JsonPath.read;
import static javax.ws.rs.core.Response.seeOther;

@Singleton
@Path("/login/twitter")
public class TwitterLoginResource {

    public static final String TWITTER_API_URL = "https://api.twitter.com/1/";
    public static final String ACCOUNT_SERVICE = "account/settings.json";
    private OAuthService twitterService;

    @Inject
    public TwitterLoginResource(OAuthService twitterService) {
        this.twitterService = twitterService;
    }

    @GET
    public Response loginWithTwitter() {
        Token requestToken = twitterService.getRequestToken();
        return seeOther(URI.create(twitterService.getAuthorizationUrl(requestToken))).build();
    }

    @GET
    @Path("/verify/")
    @Produces(MediaType.TEXT_PLAIN)
    public String verifyLoginWithTwitter(@QueryParam("oauth_token") String tokenString, @QueryParam("oauth_verifier") String verifierString) {
        Token accessToken = getAccessToken(tokenString, verifierString);
        String accountSettingsAsJson = getAccountSettings(accessToken);
        return "Welcome " + read(accountSettingsAsJson, "screen_name") + " !";
    }

    private Token getAccessToken(String tokenString, String verifierString) {
        Verifier verifier = new Verifier(verifierString);
        Token requestToken = new Token(tokenString, "");
        return twitterService.getAccessToken(requestToken, verifier);
    }

    private String getAccountSettings(Token accessToken) {
        OAuthRequest request = new OAuthRequest(Verb.GET, TWITTER_API_URL + ACCOUNT_SERVICE);
        twitterService.signRequest(accessToken, request);
        return request.send().getBody();
    }
}
