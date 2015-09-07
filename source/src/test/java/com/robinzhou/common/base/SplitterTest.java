package com.robinzhou.common.base;

import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by robinzhou on 2015/9/5.
 */
public class SplitterTest {

    @Test
    public void whenCreateListFromString_thenCreated() {
        String input = "apple - banana - orange";
        List<String> result = Splitter.on("-").trimResults()
                .splitToList(input);
        assertThat(result, hasItems("apple", "banana", "orange"));
    }

    @Test
    public void whenCreateMapFromString_thenCreated() {
        String input = "John=first,Adam=second";
        Map<String, String> result = Splitter.on(",")
                .withKeyValueSeparator("=")
                .split(input);

        assertEquals("first", result.get("John"));
        assertEquals("second", result.get("Adam"));
    }

    @Test
    public void whenSplitStringOnMultipleSeparator_thenSplit() {
        String input = "apple.banana,,orange,,.";
        List<String> result = Splitter.onPattern("[.|,]")
                .omitEmptyStrings()
                .splitToList(input);

        assertThat(result, hasItems("apple", "banana", "orange"));
    }

    @Test
    public void whenSplitStringOnSpecificLength_thenSplit() {
        String input = "Hello world";
        List<String> result = Splitter.fixedLength(3).splitToList(input);

        assertThat(result, hasItems("Hel", "lo ", "wor", "ld"));
    }

    @Test
    public void whenLimitSplitting_thenLimited() {
        String input = "a,b,c,d,e";
        List<String> result = Splitter.on(",")
                .limit(4)
                .splitToList(input);

        assertEquals(4, result.size());
        assertThat(result, hasItems("a", "b", "c", "d,e"));
    }

}
