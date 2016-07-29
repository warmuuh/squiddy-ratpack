package wrm.squiddy.server;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import static ratpack.jackson.Jackson.json;
import ratpack.server.RatpackServer;

@Singleton
public class Server {

    @Inject
    TestResource testResource;

    public void setTestResource(TestResource resource) {
        this.testResource = resource;
    }


    @PostConstruct
    public void startUp() {
        try {
            RatpackServer.start(server -> server.serverConfig(c -> c.port(8080))
                                                .handlers(chain -> chain.get(ctx -> ctx.render("Hello World!"))
                                                                        .get(":name",
                                                                             ctx -> ctx.render(json(testResource.getTest(ctx.getPathTokens().get("name")))))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
