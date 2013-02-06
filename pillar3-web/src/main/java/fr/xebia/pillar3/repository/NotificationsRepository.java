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

    public void createNotification(String message) {
        DBObject notifDbObject = BasicDBObjectBuilder.start("date", new Date()).add("message", message).get();
        collection.insert(notifDbObject);
    }

    public List<DBObject> getLatest() {
        Date minDate = new Date(System.currentTimeMillis() - REFRESH_PERIOD_MILLISECONDS);
        return collection.find(
                QueryBuilder.start("date").greaterThan(minDate).get()
        ).toArray();
    }
}
