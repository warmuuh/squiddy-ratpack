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
	
	
    public Observable<TestDescription> getTest(String id) {
    	return repository.getTest(id).toObservable();
    }


	public Observable<TestDescription> getAllTests() {
		return repository.getAllTests();
	}


	public Observable<Success> addTest(TestDescription newTest) {
		return repository.addTest(newTest).toObservable();
	}
    
    
}
