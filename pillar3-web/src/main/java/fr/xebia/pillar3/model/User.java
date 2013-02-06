package fr.xebia.pillar3.model;

import com.google.common.collect.Lists;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

import java.util.List;

public class User {

    String id;

    String login;

    double latitude;

    double longitude;

    List<String> artists = Lists.newArrayList();

    public User(DBObject dbUser) {
        this.id = (String) dbUser.get("_id");
        this.login = (String) dbUser.get("login");
        this.latitude = (Double) dbUser.get("latitude");
        this.longitude = (Double) dbUser.get("longitude");

        BasicDBList dbArtists = (BasicDBList) dbUser.get("artists");

        for (Object artist : dbArtists) {
            BasicDBObject dbArtist = (BasicDBObject) artist;

            artists.add((String) dbArtist.get("name"));
        }
    }

    public void addArtist(String name) {
        artists.add(name);
    }

    public DBObject toDBOject() {
        DBObject dbUser = new BasicDBObject();

        dbUser.put("_id", id);
        dbUser.put("login", login);
        dbUser.put("latitude", latitude);
        dbUser.put("longitude", longitude);
        dbUser.put("artists", createDBListArtists());

        return dbUser;
    }

    private BasicDBList createDBListArtists() {
        BasicDBList dBListArtists = new BasicDBList();

        for (String name : artists) {
            dBListArtists.add(new BasicDBObject("name", name));
        }

        return dBListArtists;
    }

}
