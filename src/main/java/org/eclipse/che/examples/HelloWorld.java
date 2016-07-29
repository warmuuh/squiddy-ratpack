package org.eclipse.che.examples;

import wrm.squiddy.server.ServerModule;

public class HelloWorld {
    public static void main(String[] argvs) throws Exception {
      new ServerModule().start();
    }
}
