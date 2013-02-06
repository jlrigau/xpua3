package fr.xebia.pillar3.web;

import fr.xebia.pillar3.web.resource.LoginResource;
import fr.xebia.pillar3.web.resource.TwitterLoginResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JerseyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TwitterLoginResource.class);
        classes.add(LoginResource.class);
        return classes;
    }
}

