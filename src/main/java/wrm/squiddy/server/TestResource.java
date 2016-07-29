package wrm.squiddy.server;

import javax.inject.Singleton;

@Singleton
public class TestResource {


    public TestDescription getTest(String id) {
        TestDescription test = new TestDescription();
        test.setId("123");
        return test;
    }
}
