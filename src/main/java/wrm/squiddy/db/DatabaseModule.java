package wrm.squiddy.db;


import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.reactivestreams.client.MongoDatabase;

import wrm.hardwire.Module;

@Module//(external=MongoDatabase.class)
public class DatabaseModule extends DatabaseModuleBase {
	
	protected MongoDatabase createMongoDatabase() {
		MongoClient mongoClient = MongoClients.create("mongodb://localhost");		
		return mongoClient.getDatabase("mydb");
	}
	
	@Override
	protected void wireTestRepository(TestRepository vTestRepository) {
		super.wireTestRepository(vTestRepository);
		vTestRepository.setDb(createMongoDatabase());
	}
	
}
 