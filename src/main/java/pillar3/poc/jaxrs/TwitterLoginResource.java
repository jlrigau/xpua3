package pillar3.poc.jaxrs;

import com.jayway.jsonpath.JsonPath;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/login/twitter")
public class TwitterLoginResource {

    private OAuthService twitterService = new ServiceBuilder()
            .provider(TwitterApi.class)
            .apiKey("6EK0Es2zfIx4SHaazCNuGg")
            .apiSecret("NJW1RaSzylevlg0Awfv00mRsmr0Tiq3eyRgojHzA")
            .callback("http://x.x.x.x:8080/login/twitter/verify/")
            .build();

    @GET
    public Response loginWithTwitter() {
        Token requestToken = twitterService.getRequestToken();
        return Response.seeOther(URI.create(twitterService.getAuthorizationUrl(requestToken))).build();
    }

    @GET
    @Path("/verify/")
    @Produces(MediaType.TEXT_PLAIN)
    public String loginWithTwitter(@QueryParam("oauth_token") String tokenString, @QueryParam("oauth_verifier") String verifierString) {
        Verifier verifier = new Verifier(verifierString);
        Token requestToken = new Token(tokenString, "");
        Token accessToken = twitterService.getAccessToken(requestToken, verifier);

        OAuthRequest request = new OAuthRequest(Verb.GET, "https://api.twitter.com/1/account/settings.json");
        twitterService.signRequest(accessToken, request);
        String jsonResponse = request.send().getBody();

        return "Welcome " + JsonPath.read(jsonResponse, "screen_name") + " !";
    }
}
