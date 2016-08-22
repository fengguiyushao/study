package soundsystem;

import org.springframework.stereotype.Component;

/**
 * Created by robinzhou on 2016/8/23.
 */
@Component("lonelyHeartsClub")
public class SgtPeppers implements CompactDisc {

    private String title = "Sgt. Pepper's Lonely Hearts Club Band";

    private String artistt = "The Beatles";

    @Override
    public void play() {
        System.out.println("Playing " + title + " by " + artistt);
    }
}
