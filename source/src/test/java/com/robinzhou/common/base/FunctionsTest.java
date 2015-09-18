package com.robinzhou.common.base;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.testing.EqualsTester;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.SerializableTester;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2015/9/11.
 */
public class FunctionsTest {

    private static <Y> void checkCanReserializeSingleton(Function<? super String, Y> f) {
        Function<? super String, Y> g = SerializableTester.reserializeAndAssert(f);
        assertSame(f, g);

        for (Integer i = 1; i < 5; i++) {
            assertEquals(f.apply(i.toString()), g.apply(i.toString()));
        }
    }


    private static <Y> void checkCanReserialize(Function<? super Integer, Y> f) {
        Function<? super Integer, Y> g = SerializableTester.reserializeAndAssert(f);
        for (int i = 1; i < 5; i++) {
            Y expected = null;
            try {
                expected = f.apply(i);
            } catch (IllegalArgumentException e) {
                try {
                    g.apply(i);
                    fail();
                } catch (IllegalArgumentException ok) {
                    continue;
                }
            }
            assertEquals(expected, g.apply(i));
        }
    }

    @Test
    public void testIdentity_same() {
        Function<String, String> identity = Functions.identity();
        assertNull(identity.apply(null));
        assertSame("foo", identity.apply("foo"));
    }

    @Test
    public void testIdentity_notSame() {
        assertNotSame(new Long(135135L), Functions.identity().apply(new Long(135135L)));
    }

    @Test
    public void testIdentitySerializable() {
        checkCanReserializeSingleton(Functions.identity());
    }

    @Test
    public void testToStringFunction_apply() {
        assertEquals("3", Functions.toStringFunction().apply(3));
        assertEquals("hiya", Functions.toStringFunction().apply("hiya"));
        assertEquals("I'm a string", Functions.toStringFunction().apply(new Object() {
            @Override
            public String toString() {
                return "I'm a string";
            }
        }));

        try {
            Functions.toStringFunction().apply(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testToStringSerializable() {
        checkCanReserializeSingleton(Functions.toStringFunction());
    }

    @Test
    public void testNullPointerExceptions() {
        NullPointerTester tester = new NullPointerTester();
        tester.testAllPublicStaticMethods(Functions.class);
    }

    @Test
    public void testForMapWithoutDefault() {
        Map<String, Integer> map = new HashMap<>();
        map.put("one", 1);
        map.put("two", 2);
        map.put("null", null);
        Function<String, Integer> function = Functions.forMap(map);

        assertEquals(1, function.apply("one").intValue());
        assertEquals(2, function.apply("two").intValue());
        assertNull(function.apply("null"));

        try {
            function.apply("three");
            fail();
        } catch (IllegalArgumentException expected) {
        }

        new EqualsTester()
                .addEqualityGroup(function, Functions.forMap(map))
                .addEqualityGroup(Functions.forMap(map, 42))
                .testEquals();
    }

    @Test
    public void testForMapWithoutDefaultSerializable() {
        checkCanReserialize(Functions.forMap(ImmutableMap.of(1, 2)));
    }

    @Test
    public void testForMapWIthDefault() {
        Map<String, Integer> map = Maps.newHashMap();
        map.put("one", 1);
        map.put("two", 2);
        map.put("null", null);
        Function<String, Integer> function = Functions.forMap(map, 42);

        assertEquals(1, function.apply("one").intValue());
        assertEquals(2, function.apply("two").intValue());
        assertEquals(42, function.apply("three").intValue());
        assertNull(function.apply("null"));

        new EqualsTester()
                .addEqualityGroup(function, Functions.forMap(map, 42))
                .addEqualityGroup(Functions.forMap(map))
                .addEqualityGroup(Functions.forMap(map, null))
                .addEqualityGroup(Functions.forMap(map, 43)).testEquals();
    }


    @Test
    public void testForMapWithDefault_includeSerializable() {
        Map<String, Integer> map = Maps.newHashMap();
        map.put("One", 1);
        map.put("Three", 3);
        Function<String, Integer> function = Functions.forMap(map, 42);

        assertEquals(1, function.apply("One").intValue());
        assertEquals(42, function.apply("Two").intValue());
        assertEquals(3, function.apply("Three").intValue());

        new EqualsTester()
                .addEqualityGroup(
                        function,
                        Functions.forMap(map, 42),
                        SerializableTester.reserialize(function))
                .addEqualityGroup(Functions.forMap(map))
                .addEqualityGroup(Functions.forMap(map, null))
                .addEqualityGroup(Functions.forMap(map, 43))
                .testEquals();
    }

    @Test
    public void testForMapWithDefaultSerializable() {
        checkCanReserialize(Functions.forMap(ImmutableMap.of(1, 2), 3));
    }

    @Test
    public void testForMapWithDefault_null() {
        ImmutableMap<String, Integer> map = ImmutableMap.of("one", 1);
        Function<String, Integer> function = Functions.forMap(map, null);

        assertEquals(1, function.apply("one").intValue());
        assertNull(function.apply("two"));

        new EqualsTester().addEqualityGroup(function)
                .addEqualityGroup(Functions.forMap(map, 2))
                .testEquals();
    }

    @Test
    public void testForMapWithDefault_null_compareWithSerializable() {
        ImmutableMap<String, Integer> map = ImmutableMap.of("One", 1);
        Function<String, Integer> function = Functions.forMap(map, null);

        assertEquals((Integer) 1, function.apply("One"));
        assertNull(function.apply("Two"));

        // check basic sanity of equals and hashCode
        new EqualsTester()
                .addEqualityGroup(function, SerializableTester.reserialize(function))
                .addEqualityGroup(Functions.forMap(map, 1))
                .testEquals();
    }

    @Test
    public void testComposition() {
        Map<String, Integer> mJapaneseToInteger = Maps.newHashMap();
        mJapaneseToInteger.put("Ichi", 1);
        mJapaneseToInteger.put("Ni", 2);
        mJapaneseToInteger.put("San", 3);
        Function<String, Integer> japaneseToInteger =
                Functions.forMap(mJapaneseToInteger);

        Map<Integer, String> mIntegerToSpanish = Maps.newHashMap();
        mIntegerToSpanish.put(1, "Uno");
        mIntegerToSpanish.put(3, "Tres");
        mIntegerToSpanish.put(4, "Cuatro");
        Function<Integer, String> integerToSpanish =
                Functions.forMap(mIntegerToSpanish);

        Function<String, String> japaneseToSpanish =
                Functions.compose(integerToSpanish, japaneseToInteger);

        assertEquals("Uno", japaneseToSpanish.apply("Ichi"));
        try {
            japaneseToSpanish.apply("Ni");
            fail();
        } catch (IllegalArgumentException e) {
        }
        assertEquals("Tres", japaneseToSpanish.apply("San"));
        try {
            japaneseToSpanish.apply("Shi");
            fail();
        } catch (IllegalArgumentException e) {
        }

        new EqualsTester()
                .addEqualityGroup(
                        japaneseToSpanish,
                        Functions.compose(integerToSpanish, japaneseToInteger))
                .addEqualityGroup(japaneseToInteger)
                .addEqualityGroup(integerToSpanish)
                .addEqualityGroup(
                        Functions.compose(japaneseToInteger, integerToSpanish))
                .testEquals();
    }

    @Test
    public void testComposeOfFunctionsIsAssociative() {
        Map<Float, String> m = ImmutableMap.of(4.0f, "A", 3.0f, "B");

        Function<? super Integer, Boolean> h = Functions.constant(Boolean.TRUE);
        Function<? super String, Integer> g = new HashCodeFunction();
        Function<Float, String> f = Functions.forMap(m, "F");

        Function<Float, Boolean> c1 = Functions.compose(Functions.compose(h, g), f);
        Function<Float, Boolean> c2 = Functions.compose(h, Functions.compose(g, f));

        assertEquals(c1.hashCode(), c2.hashCode());

        assertEquals(c1.apply(1.0f), c2.apply(1.0f));
        assertEquals(c1.apply(5.0f), c2.apply(3.0f));
    }

    private static class HashCodeFunction implements Function<Object, Integer> {

        @Override
        public Integer apply(@Nullable Object object) {
            return (object == null) ? 0 : object.hashCode();
        }
    }

}
