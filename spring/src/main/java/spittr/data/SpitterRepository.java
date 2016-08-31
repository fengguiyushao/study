package spittr.data;

import spittr.Spitter;

/**
 * Created by robinzhou on 2016/8/31.
 */
public interface SpitterRepository {

    Spitter save(Spitter spitter);

    Spitter findByUsername(String username);
}
