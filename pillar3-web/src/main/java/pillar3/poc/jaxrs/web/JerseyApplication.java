package pillar3.poc.jaxrs.web;

import pillar3.poc.jaxrs.web.resource.TweetResource;
import pillar3.poc.jaxrs.web.resource.TwitterLoginResource;

import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class JerseyApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(TwitterLoginResource.class);
        classes.add(TweetResource.class);
        return classes;
    }
}

