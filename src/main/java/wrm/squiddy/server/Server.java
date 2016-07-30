package wrm.squiddy.server;

import static ratpack.jackson.Jackson.json;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Setter;
import lombok.SneakyThrows;
import lombok.val;
import ratpack.handling.Chain;
import ratpack.jackson.Jackson;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import rx.Single;

@Singleton
public class Server {

    @Inject @Setter
    TestResource testResource;

    @PostConstruct
    @SneakyThrows 
    public void startUp() {
    	RxRatpack.initialize();
        RatpackServer.start(server -> server.serverConfig(c -> c.port(8080))
                                                .handlers(this::setupRoutes));
    }
    
    Chain setupRoutes(Chain chain){
    	return chain
    			.get(ctx -> ctx.render("Hello World!"))
    			.get("test/:id",
    						ctx -> {
    							String name = ctx.getPathTokens().get("id");
    							testResource.getTest(name)
    								.map(Jackson::json)
    								.subscribe(ctx::render);
    						});
    }
}
