package pillar3.poc.jaxrs.model;

import com.mongodb.DBObject;

public class User {

    String login;

    double latitude;

    double longitude;

    public User(DBObject jsonUser) {
        this.login = (String) jsonUser.get("login");
        this.latitude = (double) jsonUser.get("latitude");
        this.longitude = (double) jsonUser.get("longitude");
    }
}
