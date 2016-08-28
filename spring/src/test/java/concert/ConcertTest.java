package concert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;


/**
 * Created by robinzhou on 2016/8/28.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ConcertConfig.class)
public class ConcertTest {

    @Autowired
    private Performance sing;

    @Autowired
    private Audience audience;

    @Test
    public void performanceTest() {
        sing.perform(1);
        sing.perform(2);
        sing.perform(3);
        sing.perform(4);
        assertEquals(10, audience.times);
        ((Encoreable) sing).performEncore();
    }
}
