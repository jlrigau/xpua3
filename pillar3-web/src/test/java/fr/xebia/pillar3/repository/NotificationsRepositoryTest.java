package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;
import fr.xebia.pillar3.GuiceJUnitRunner;
import fr.xebia.pillar3.model.Notification;
import fr.xebia.pillar3.web.Module;
import fr.xebia.pillar3.web.TestModule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.print.attribute.standard.DateTimeAtCompleted;

import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(GuiceJUnitRunner.class)
@GuiceJUnitRunner.GuiceModules({TestModule.class})
public class NotificationsRepositoryTest {
    @Inject
    NotificationsRepository repository;

    @Test
    public void shouldCreateCappedCollection() {
        assertThat(repository.collection.isCapped()).isTrue();
    }

    @Test
    public void testCreateNotification() throws Exception {
        final String msgNotif =                   "Artiste Jonny ajouté !";
        final long oldCount = repository.collection.count();
        repository.createNotification(msgNotif);
        assertThat(repository.collection.count()).isEqualTo(oldCount + 1);
    }

    @Test
    public void testGetLatest() throws Exception {
    }
}
