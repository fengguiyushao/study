package com.robinzhou.common.base;

import com.google.common.testing.NullPointerTester;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2015/9/10.
 */
public class ObjectsTest {

    @Test
    public void testEqual() {
        assertTrue(Objects.equal(1, 1));
        assertTrue(Objects.equal(null, null));

        String s1 = "foobar";
        String s2 = new String(s1);
        assertTrue(Objects.equal(s1, s2));

        assertFalse(Objects.equal(null, s1));
        assertFalse(Objects.equal(s1, null));
        assertFalse(Objects.equal("foo", "bar"));
        assertFalse(Objects.equal("1", 1));
    }

    @Test
    public void testHashCode() {
        int h1 = Objects.hashCode(1, "two", 3.0);
        int h2 = Objects.hashCode(new Integer(1), new String("two"), new Double("3.0"));

        assertEquals(h1, h2);

        assertTrue(Objects.hashCode(1, 2, null) != Objects.hashCode(1, 2));
        assertTrue(Objects.hashCode(1, 2, null) != Objects.hashCode(1, null, 2));
        assertTrue(Objects.hashCode(1, null, 2) != Objects.hashCode(1, 2));
        assertTrue(Objects.hashCode(1, 2, 3) != Objects.hashCode(2, 3, 1));
        assertTrue(Objects.hashCode(1, 2, 3) != Objects.hashCode(3, 2, 1));
    }


    @Test
    public void testFirstNonNull_withNonNull() {
        String s1 = "foo";
        String s2 = Objects.firstNonNull(s1, "bar");
        assertSame(s1, s2);

        Long n1 = new Long(42);
        Long n2 = Objects.firstNonNull(null, n1);
        assertSame(n1, n2);
    }

    @Test
    public void testFirstNonNull_throwsNullPointerException() {
        try{
            Objects.firstNonNull(null,null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testNullPointers() {
        NullPointerTester tester = new NullPointerTester();
        tester.testAllPublicStaticMethods(Objects.class);
    }
}
