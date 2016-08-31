package spittr.data;

import spittr.Spittle;

import java.util.List;

/**
 * Created by robinzhou on 2016/8/30.
 */
public interface SpittleRepository {

    List<Spittle> findSpittles(long max, int count);

    Spittle findOne(long spittleId);
}
