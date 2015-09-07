package com.robinzhou.common.base;

import com.robinzhou.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by robinzhou on 2015/9/5.
 */
public class JoinerTest {
    @Test
    public void whenConvertListToString_thenConverted() {
        List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
        String result = Joiner.on(",").join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenConvertArrayToString_thenConverted() {
        String result = Joiner.on(",").join("John", "Jane", "Adam", "Tom");

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenConvertMapToString_thenConverted() {
        Map<String, Integer> salary = Maps.newHashMap();
        salary.put("John", 1000);
        salary.put("Jane", 1500);
        String result = Joiner.on(" , ").withKeyValueSeparator(" = ")
                .join(salary);

        assertThat(result, containsString("John = 1000"));
        assertThat(result, containsString("Jane = 1500"));
    }

    @Test
    public void whenJoinNestedCollections_thenJoined() {
        List<ArrayList<String>> nested = Lists.newArrayList(
                Lists.newArrayList("apple", "banana", "orange"),
                Lists.newArrayList("cat", "dog", "bird"),
                Lists.newArrayList("John", "Jane", "Adam"));

        String result = Joiner.on(";").join(Iterables.transform(nested, (input) -> Joiner.on("-").join(input)));


        assertThat(result, containsString("apple-banana-orange"));
        assertThat(result, containsString("cat-dog-bird"));
        assertThat(result, containsString("apple-banana-orange"));
    }

    @Test
    public void whenConvertListToStringAndSkipNull_thenConverted() {
        List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        String result = Joiner.on(",").skipNulls().join(names);

        assertEquals(result, "John,Jane,Adam,Tom");
    }

    @Test
    public void whenUseForNull_thenUsed() {
        List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
        String result = Joiner.on(",").useForNull("nameless").join(names);

        assertEquals(result, "John,nameless,Jane,Adam,Tom");
    }


}
