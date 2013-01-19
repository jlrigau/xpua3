package pillar3.poc.jaxrs;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.net.httpserver.HttpServer;
import pillar3.poc.jaxrs.resource.TweetResource;
import pillar3.poc.jaxrs.resource.TwitterLoginResource;

import java.io.IOException;

import static com.google.inject.util.Modules.override;
import static java.lang.String.format;

public class AppServer {

    private HttpServer httpServer;
    private int port;
    private final Module[] modules;

    public AppServer(int port, Module... modules) {
        this.port = port;
        this.modules = modules;
    }

    public static void main(String[] args) throws IOException {
        new AppServer(8021).start();
    }

    public void start() throws IOException {
        DefaultResourceConfig config = new DefaultResourceConfig(TweetResource.class, TwitterLoginResource.class);

        Injector injector =  Guice.createInjector(override(new AppServerModule()).with(modules));
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(config, injector);

        httpServer = HttpServerFactory.create(String.format("http://localhost:%d/", port), config, ioc);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    public int getPort() {
        return port;
    }
}
