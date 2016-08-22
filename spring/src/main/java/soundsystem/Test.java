package soundsystem;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by robinzhou on 2016/8/23.
 */
public class Test {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CDPlayerConfig.class);
        CompactDisc disc = context.getBean(CompactDisc.class);
        disc.play();
    }
}
