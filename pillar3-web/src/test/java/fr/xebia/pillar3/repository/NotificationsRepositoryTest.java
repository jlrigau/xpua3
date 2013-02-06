package fr.xebia.pillar3.repository;

import com.google.inject.Inject;
import fr.xebia.pillar3.GuiceJUnitRunner;
import fr.xebia.pillar3.web.Module;
import fr.xebia.pillar3.web.TestModule;
import org.junit.Test;
import org.junit.runner.RunWith;

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

    public void testCreateNotification() throws Exception {

    }

    public void testGetLatest() throws Exception {

    }
}
