package com.robinzhou.common.base;

import com.google.common.base.CharMatcher;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by robinzhou on 2015/8/31.
 */
public class CharMatcherTest {

    @Test
    public void whenRemoveSpecialCharacters_thenRemoved(){
        String input = "H*el.lo,}12";
        CharMatcher matcher = CharMatcher.JAVA_LETTER_OR_DIGIT;
        String result = matcher.retainFrom(input);

        assertEquals("Hello12", result);
    }


}
