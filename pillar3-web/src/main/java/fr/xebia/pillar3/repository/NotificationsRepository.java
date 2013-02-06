package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.mongodb.DBCollection;
import fr.xebia.pillar3.model.Notification;
import fr.xebia.pillar3.web.Module;

import java.util.List;


public class NotificationsRepository {
    @Inject
    @Named(Module.NOTIFS_COLLECTION)
    DBCollection collection;

    public void createNotification(String message) {
        // todo insérer dans la collection
    }

    public List<Notification> getLatest() {
        // TODO gérer cache toutes les x secondes

        // TODO refresh: renvoyer les notifs depuis x secondes
        return null;
    }
}
