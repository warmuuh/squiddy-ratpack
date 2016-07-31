package wrm.squiddy.db;

import java.util.List;

import org.bson.BsonDocument;
import org.bson.BsonElement;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.Document;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

public class BsonDsl {
	private static ObjectMapper mapper = new ObjectMapper();

	public static class BsonBuilder extends BsonDocument {

		private static final long serialVersionUID = -5886512761048445736L;

		public BsonBuilder() {
			super();
		}

		public BsonBuilder(List<BsonElement> bsonElements) {
			super(bsonElements);
		}

		public BsonBuilder(String key, BsonValue value) {
			super(key, value);
		}

		
		
	}
	
	public static BsonBuilder bson(String key, String value) {
		return new BsonBuilder(key, new BsonString(value));
	};
	
	@SneakyThrows
	public static Document bson(Object obj){
		String json = mapper.writeValueAsString(obj);
		return Document.parse(json);
	}
	
	@SneakyThrows
	public static <T> T fromBson(Document doc, Class<T> c) {
		return mapper.readValue(doc.toJson(), c);
	}
}
