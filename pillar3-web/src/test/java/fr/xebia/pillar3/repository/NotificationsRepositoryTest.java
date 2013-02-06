package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.mongodb.*;
import fr.xebia.pillar3.GuiceJUnitRunner;
import fr.xebia.pillar3.web.MongoDBRule;
import fr.xebia.pillar3.web.TestModule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({TestModule.class})
public class NotificationsRepositoryTest {
    @Rule
    public MongoDBRule mongoDb = new MongoDBRule();

    @Inject
    NotificationsRepository repository;


    @Before
    public void purgeData() {
        DBCollection collection = repository.collection;
        collection.getDB().command(new BasicDBObject("emptycapped", collection.getName()));
    }

    @Test
    public void shouldCreateCappedCollection() {
        assertThat(repository.collection.isCapped()).isTrue();
    }

    @Test
    public void testCreateNotification() throws Exception {
        final String msgNotif = "Artiste Jonny ajouté !";
        final long oldCount = repository.collection.count();
        repository.createNotification(msgNotif);
        assertThat(repository.collection.count()).isEqualTo(oldCount + 1);
    }

    @Test
    public void testGetLatest() throws Exception {
        insertNotification(new Date(), "test");
        List<DBObject> latests = repository.getLatest();
        assertThat(latests.size()).isEqualTo(1);
    }

    @Test
    public void getLatest_should_not_return_old_notifications() {
        insertNotification(new Date(0), "too old, should not appear");
        insertNotification(new Date(), "test");
        List<DBObject> latests = repository.getLatest();
        assertThat(latests.size()).isEqualTo(1);
        assertThat(latests.get(0).get("message")).isEqualTo("test");
    }

    private void insertNotification(Date date, String message) {
        DBObject notifDbObject = BasicDBObjectBuilder.start("date", date).add("message", message).get();
        repository.collection.insert(notifDbObject);
    }
}
