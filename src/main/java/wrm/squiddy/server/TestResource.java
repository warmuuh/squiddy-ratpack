package wrm.squiddy.server;

import javax.inject.Inject;
import javax.inject.Singleton;

import lombok.Setter;
import rx.Single;
import wrm.squiddy.db.TestRepository;

@Singleton
public class TestResource {


	@Inject @Setter
	TestRepository repository;
	
	
    public Single<TestDescription> getTest(String id) {
    	return repository.getTest(id);
    }
}
