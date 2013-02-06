package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.mongodb.*;
import fr.xebia.pillar3.model.User;

import java.net.UnknownHostException;

public class UserRepository {

    static final String TWEETS_COLLECTION = "users";
    private final DBCollection userCollection;

    @Inject
    public UserRepository(DB tweetDB) throws UnknownHostException {
        userCollection = tweetDB.getCollection(TWEETS_COLLECTION);
    }

    public User findOrCreateUser(String login, String city) {
        DBObject searchUser = new BasicDBObject("login", login);

        DBObject user = userCollection.findOne(searchUser);

        if (user != null) {
            return new User(user);
        } else {
            // save new user
            return null;
        }
    }
}
