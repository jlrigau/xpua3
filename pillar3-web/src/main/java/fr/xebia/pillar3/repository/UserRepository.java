package fr.xebia.pillar3.repository;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderGeometry;
import com.google.code.geocoder.model.GeocoderRequest;
import com.google.code.geocoder.model.GeocoderResult;
import com.google.inject.Inject;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import fr.xebia.pillar3.model.User;

import java.net.UnknownHostException;
import java.util.List;

public class UserRepository {

    private final double DEFAULT_LAT = 48.8566140;
    private final double DEFAULT_LNG = 2.35222190;


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

            geocodeCity(city, searchUser);

            userCollection.save(searchUser);
            return new User(searchUser);
        }
    }

    private void geocodeCity(String city, DBObject searchUser) {
        final Geocoder geocoder = new Geocoder();
        GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(city + ", France").setLanguage("fr").getGeocoderRequest();
        GeocodeResponse geocoderResponse = geocoder.geocode(geocoderRequest);
        double lat = DEFAULT_LAT;
        double lng = DEFAULT_LNG;
        List<GeocoderResult> results = geocoderResponse.getResults();

        if (results.size() != 0) {
            GeocoderGeometry geometry = results.get(0).getGeometry();
            lat = geometry.getLocation().getLat().doubleValue();
            lng = geometry.getLocation().getLng().doubleValue();
        }

        searchUser.put("latitude", lat);
        searchUser.put("longitude", lng);
    }
}
