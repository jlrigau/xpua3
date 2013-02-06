package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.mongodb.*;
import fr.xebia.pillar3.model.User;

import java.net.UnknownHostException;

public class UserRepository {

    private final DBCollection userCollection;

    @Inject
    public UserRepository(DBCollection users) throws UnknownHostException {
        userCollection = users;
    }

    public User findOrCreateUser(String login, String city) {
        DBObject searchUser = new BasicDBObject("login", login);

        DBObject user = userCollection.findOne(searchUser);

        if (user != null) {
            return new User(user);
        } else {
            // save new user
            searchUser.put("latitude", 50.0d);
            searchUser.put("longitude", 10.0d);

            userCollection.save(searchUser);
            return new User(searchUser);
        }
    }
}
