package concert;

import org.springframework.stereotype.Component;

/**
 * Created by robinzhou on 2016/8/28.
 */
@Component
public class Sing implements Performance {
    @Override
    public void perform(int times) {
        System.out.println("Sing");
    }
}
