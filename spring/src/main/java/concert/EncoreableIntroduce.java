package concert;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

/**
 * Created by robinzhou on 2016/8/28.
 */
@Aspect
@Component
public class EncoreableIntroduce {

    @DeclareParents(value = "concert.Performance+", defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;

    @Before("execution(* concert.Performance.perform(..)) && target(encoreable)")
    public void recordUsage(Encoreable encoreable) {
        encoreable.performEncore();
    }
}
