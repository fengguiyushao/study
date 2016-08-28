package concert;

/**
 * Created by robinzhou on 2016/8/28.
 */
public class DefaultEncoreable implements Encoreable {
    @Override
    public void performEncore() {
        System.out.println("default perform encore");
    }
}
