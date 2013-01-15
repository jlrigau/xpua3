package pillar3.poc.jaxrs;

import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class AppServer {

    private HttpServer httpServer;

    private int port;

    public static void main(String[] args) throws IOException {
        new AppServer(8042).start();
    }

    public AppServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        DefaultResourceConfig config = new DefaultResourceConfig(TweetResource.class);

        httpServer = HttpServerFactory.create(String.format("http://localhost:%d/", port), config);
        httpServer.start();
    }

    public void stop() {
        httpServer.stop(0);
    }

    public int getPort() {
        return port;
    }
}
