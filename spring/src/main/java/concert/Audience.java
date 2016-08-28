package concert;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * Created by robinzhou on 2016/8/27.
 */
@Aspect
@Component
public class Audience {

    public int times = 0;

    @Pointcut("execution(* concert.Performance.perform(int)) && args(times)")
    public void performance(int times) {
    }

//    @Before("performance()")
//    public void silenceCellPhones() {
//        System.out.println("Sillencing cell phones");
//    }
//
//
//    @Before("performance()")
//    public void takeSeats() {
//        System.out.println("Taking seats");
//    }
//
//    @Before("performance()")
//    public void applause() {
//        System.out.println("CLAP CLAP CLAP!!!");
//    }
//
//    @Before("performance()")
//    public void demandRefund() {
//        System.out.println("Demanding a refund");
//    }

    @Around("performance(times)")
    public void watchPerformance(ProceedingJoinPoint jp, int times) {
        try {
            this.times += times;
            System.out.println("Sillencing cell phones");
            System.out.println("Taking seats");
            jp.proceed();
            System.out.println("CLAP CLAP CLAP!!!");
        } catch (Throwable throwable) {
            System.out.println("Demanding a refund");
        }

    }
}
