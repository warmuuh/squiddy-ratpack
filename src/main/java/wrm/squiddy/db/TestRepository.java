package wrm.squiddy.db;

import static wrm.squiddy.db.BsonDsl.bson;
import static wrm.squiddy.db.BsonDsl.fromBson;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bson.Document;

import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoDatabase;

import lombok.Setter;
import static rx.RxReactiveStreams.*;
import rx.Single;
import wrm.squiddy.server.TestDescription;


@Singleton
public class TestRepository {

	@Inject @Setter
	MongoDatabase db;
	
	 
	public Single<TestDescription> getTest(String id) {
		FindPublisher<Document> found = db.getCollection("tests").find(bson("id", id));
		return toSingle(found)
				.map(doc -> fromBson(doc, TestDescription.class));
	}
	
	
	
}
