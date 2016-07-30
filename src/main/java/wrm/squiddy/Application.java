package wrm.squiddy;

import wrm.squiddy.db.DatabaseModule;
import wrm.squiddy.server.ServerModule;

public class Application {
    public static void main(String[] argvs) throws Exception {
    	DatabaseModule databaseModule = new DatabaseModule();
		databaseModule.start();
    	ServerModule serverModule = new ServerModule();
    	serverModule.reference(databaseModule);
		serverModule.start();
    }
}
