package wrm.squiddy.server;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.reactivestreams.Publisher;

import com.mongodb.reactivestreams.client.Success;

import lombok.Setter;
import ratpack.jackson.Jackson;
import ratpack.jackson.JsonRender;
import rx.Observable;
import rx.RxReactiveStreams;
import rx.Single;
import wrm.squiddy.db.TestRepository;
import static rx.RxReactiveStreams.*;

@Singleton
public class TestResource {


	@Inject @Setter
	TestRepository repository;
	
	
    public Publisher<JsonRender> getTest(String id) {
    	return toPublisher(repository.getTest(id).map(Jackson::json));
    }


	public Publisher<JsonRender> getAllTests() {
		return toPublisher(repository.getAllTests().map(Jackson::json));
	}


	public Publisher<JsonRender> addTest(TestDescription newTest) {
		return toPublisher(repository.addTest(newTest).map(Jackson::json));
	}
    
    
}
