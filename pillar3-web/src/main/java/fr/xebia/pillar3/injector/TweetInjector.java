package fr.xebia.pillar3.injector;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.Mongo;
import com.mongodb.util.JSON;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import java.net.UnknownHostException;

public class TweetInjector {

    public static void main(String[] args) throws UnknownHostException {
        DB tweetDB = new Mongo().getDB("tweetDb");
        String tweetsResultAsJson = getTweetsResultsAsJson("mrenou42");
        BasicDBObject tweetsResultAsJsonObject = (BasicDBObject) JSON.parse(tweetsResultAsJson);
        for (Object tweetObject : (BasicDBList) tweetsResultAsJsonObject.get("results")) {
            BasicDBObject tweetAsJsonObject = (BasicDBObject) tweetObject;
            tweetDB.getCollection("tweets").insert(tweetAsJsonObject);
        }
    }

    private static String getTweetsResultsAsJson(String username) {
        Client client = Client.create();

        WebResource webResource = client
                .resource("http://search.twitter.com/search.json?q=from:" + username);

        ClientResponse response = webResource.accept("application/json")
                .get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatus());
        }

        return response.getEntity(String.class);
    }

}
