package wrm.squiddy.db;

import javax.inject.Singleton;

import org.bson.BsonDocument;
import org.bson.BsonString;
import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoDatabase;

import lombok.Setter;
import lombok.SneakyThrows;
import rx.RxReactiveStreams;
import rx.Single;
import wrm.squiddy.server.TestDescription;

@Singleton
public class TestRepository {

	@Setter
	MongoDatabase db;
	
	ObjectMapper mapper = new ObjectMapper();
	
	public Single<TestDescription> getTest(String id) {
		FindPublisher<Document> found = db.getCollection("tests").find(new BsonDocument("id", new BsonString(id)));
		return RxReactiveStreams.toSingle(found)
				.map(doc -> readValue(doc, TestDescription.class));
	}
	
	@SneakyThrows
	private <T> T readValue(Document doc, Class<T> c) {
		return mapper.readValue(doc.toJson(), c);
	}
}
