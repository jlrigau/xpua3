package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.mongodb.DBCollection;
import fr.xebia.pillar3.model.Notification;

import java.util.List;


public class NotificationsRepository {
    private final DBCollection notifications;

    @Inject
    public NotificationsRepository(DBCollection notifications) {
        this.notifications = notifications;
    }

    public void createNotification(String message) {
        // todo insérer dans la collection
    }

    public List<Notification> getLatest() {
        // TODO gérer cache toutes les x secondes

        // TODO refresh: renvoyer les notifs depuis x secondes
        return null;
    }

}
