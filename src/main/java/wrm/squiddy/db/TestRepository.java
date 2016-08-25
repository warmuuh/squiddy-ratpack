package wrm.squiddy.db;

import static wrm.squiddy.db.BsonDsl.bson;
import static wrm.squiddy.db.BsonDsl.fromBson;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.bson.Document;
import org.reactivestreams.Publisher;

import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoDatabase;
import com.mongodb.reactivestreams.client.Success;

import lombok.AllArgsConstructor;
import lombok.Setter;
import static rx.RxReactiveStreams.*;

import rx.Observable;
import rx.Single;
import wrm.squiddy.server.TestDescription;


@Singleton
public class TestRepository {

	
	@Setter
	private MongoDatabase db;
	
	 
	public Single<TestDescription> getTest(String id) {
		FindPublisher<Document> found = db.getCollection("tests").find(bson("id", id));
		return toSingle(found)
				.map(doc -> fromBson(doc, TestDescription.class));
	}
	
	public Observable<TestDescription> getAllTests() {
		FindPublisher<Document> found = db.getCollection("tests").find();
		return toObservable(found)
				.map(doc -> fromBson(doc, TestDescription.class));
	}
	
	public Single<Success> addTest(TestDescription newTest) {
		Publisher<Success> insertion = db.getCollection("tests").insertOne(bson(newTest));
		return toSingle(insertion);
	}
	
}
