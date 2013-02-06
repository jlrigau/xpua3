package fr.xebia.pillar3.web;


import fr.xebia.pillar3.web.resource.UserResource;
import fr.xebia.pillar3.web.resource.ArtistResource;
import fr.xebia.pillar3.web.resource.TwitterLoginResource;
import fr.xebia.pillar3.web.resource.NotificationsResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JerseyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(TwitterLoginResource.class);
        classes.add(UserResource.class);
        classes.add(ArtistResource.class);
        classes.add(NotificationsResource.class);
        return classes;
    }
}

