package org.eclipse.che.examples;

import ratpack.server.RatpackServer;

public class HelloWorld {
    public static void main(String[] argvs) throws Exception {
        RatpackServer.start(server -> server 
         .serverConfig(c -> c.port(8080))
         .handlers(chain -> chain
           .get(ctx -> ctx.render("Hello World!"))
           .get(":name", ctx -> ctx.render("Hello " + ctx.getPathTokens().get("name") + "!"))     
         )
       );
    }
}
