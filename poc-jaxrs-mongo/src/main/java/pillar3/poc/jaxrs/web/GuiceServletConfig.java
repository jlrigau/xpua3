package pillar3.poc.jaxrs.web;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

import java.util.HashMap;
import java.util.Map;

public class GuiceServletConfig extends GuiceServletContextListener {
    @Override
    protected Injector getInjector() {
        final Map<String, String> params = new HashMap<>();
        params.put("javax.ws.rs.Application", "pillar3.poc.jaxrs.web.JerseyApplication");
        return Guice.createInjector(
                new Module(),
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        serve("/resources/*").with(GuiceContainer.class, params);
                    }
                }
        );
    }
}
