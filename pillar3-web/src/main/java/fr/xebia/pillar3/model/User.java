package fr.xebia.pillar3.model;

import com.mongodb.BasicDBList;
import com.mongodb.DBObject;

import java.util.ArrayList;
import java.util.List;

public class User {

    String login;

    double latitude;

    double longitude;

    List<String> artists = new ArrayList<String>();

    public User(DBObject jsonUser) {
        this.login = (String) jsonUser.get("login");
        this.latitude = (Double) jsonUser.get("latitude");
        this.longitude = (Double) jsonUser.get("longitude");

        BasicDBList dbArtists = (BasicDBList) jsonUser.get("artists");
        for (Object a : dbArtists) {
            DBObject dbArtist = (DBObject) a;
            artists.add((String) dbArtist.get("name"));
        }
    }

}
