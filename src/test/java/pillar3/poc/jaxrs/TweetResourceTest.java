package pillar3.poc.jaxrs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TweetResourceTest {

    private AppServer appServer = new AppServer(8021);

    @Before
    public void before() throws IOException {
       appServer.start();
    }

    @Test
    public void test() {
        given().port(appServer.getPort())
                .expect().body("[0]._id.$oid", equalTo("50f54d2a9df5ad812a497e7a"))
                .when().get("/tweet/article");
    }

    @After
    public void after() throws IOException {
        appServer.stop();
    }
}
