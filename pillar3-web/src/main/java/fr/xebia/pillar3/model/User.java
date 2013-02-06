package fr.xebia.pillar3.model;

import com.mongodb.DBObject;

public class User {

    String login;

    double latitude;

    double longitude;

    public User(DBObject jsonUser) {
        this.login = (String) jsonUser.get("login");
        this.latitude = (Double) jsonUser.get("latitude");
        this.longitude = (Double) jsonUser.get("longitude");
    }
}
