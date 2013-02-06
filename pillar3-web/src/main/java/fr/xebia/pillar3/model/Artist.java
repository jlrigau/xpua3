package fr.xebia.pillar3.model;

import com.mongodb.DBObject;

public class Artist {

    public String id;

    public String name;

    public Artist(DBObject artist) {
        this.id = (String) artist.get("id");
        this.name = (String) artist.get("name");
    }

    public Artist(String name, String id) {
        this.id = id;
        this.name = name;
    }
}
