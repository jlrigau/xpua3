package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.*;
import fr.xebia.pillar3.web.Module;

import javax.inject.Singleton;
import java.util.Date;
import java.util.List;


@Singleton
public class NotificationsRepository {
    public static int REFRESH_PERIOD_MILLISECONDS = 1000;

    @Inject
    @Named(Module.NOTIFS_COLLECTION)
    DBCollection collection;

    /** passer par les méthodes statiques pour créer les dbobjects */
    public void add(DBObject notifDbObject) {
        collection.insert(notifDbObject);
    }

    public List<DBObject> getSince(String login, Long since) {
        Date minDate = new Date(since);
        return collection.find(
                QueryBuilder.start("date").greaterThan(minDate)
                        .and("login").notEquals(login).get()
        ).toArray();
    }

    public static DBObject newSimpleNotification(String login, String message) {
        return  baseNotificationBuilder(login, "simple")
                .add("message", message).get();
    }

    public static DBObject newFavoriteArtistNotification(String login, String artistName) {
        return  baseNotificationBuilder(login, "favoriteArtist")
                .add("artist", artistName).get();
    }

    private static BasicDBObjectBuilder baseNotificationBuilder(String messageType, String login) {
        return BasicDBObjectBuilder
                .start("date", new Date())
                .add("login", login)
                .add("type", messageType);
    }
}
