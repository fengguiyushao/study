package concert;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by robinzhou on 2016/8/28.
 */
@Aspect
@Component
public class EncoreableIntroduce {

    @DeclareParents(value = "concert.Performance+", defaultImpl = DefaultEncoreable.class)
    public static Encoreable encoreable;

    @Pointcut("execution(* concert.Performance.perform(int)) && args(times)")
    public void performance(int times) {
    }

    @Before("performance(times) && this(encoreable)")
    public void recordUsage (Encoreable encoreable, int times) {
        System.out.println("times:" + times);
        encoreable.performEncore();
    }
}
