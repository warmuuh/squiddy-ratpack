package wrm.squiddy.db;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

import wrm.hardwire.Module;

@Module
public class DatabaseModule extends DatabaseModuleBase {
	
	//TODO: hardwire external does not work!
	@Override
	protected void wireTestRepository(TestRepository vTestRepository) {
		super.wireTestRepository(vTestRepository);
		MongoClient mongoClient = MongoClients.create("mongodb://localhost");
		vTestRepository.setDb(mongoClient.getDatabase("mydb"));
	}
}
 