package fr.xebia.pillar3.model;

import com.mongodb.DBObject;

import java.util.List;

public class User {

    String login;

    double latitude;

    double longitude;

    List<String> artists;

    public User(DBObject jsonUser) {
        this.login = (String) jsonUser.get("login");
        this.latitude = (Double) jsonUser.get("latitude");
        this.longitude = (Double) jsonUser.get("longitude");
//        this.artists = (List) jsonUser.get("artists");
    }

}
