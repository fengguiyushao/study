package com.robinzhou.common.base;

import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.SerializableTester;
import org.junit.Test;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2015/9/9.
 */
public class OptionalTest {

    @Test
    public void testAbsent() {
        Optional<String> absent = Optional.absent();
        assertFalse(absent.isPresent());
    }

    @Test
    public void testOf() {
        assertEquals("training", Optional.of("training").get());
    }

    @Test
    public void testOf_null() {
        try {
            Optional.of(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testFromNullable() {
        Optional<String> optionalName = Optional.fromNullable("bob");
        assertEquals("bob", optionalName.get());
    }

    @Test
    public void testFromNullable_null() {
        assertSame(Optional.absent(), Optional.fromNullable(null));
    }

    @Test
    public void testIsPresent_no() {
        assertFalse(Optional.absent().isPresent());
    }

    @Test
    public void testIsPresent_yes() {
        assertTrue(Optional.of("training").isPresent());
    }

    @Test
    public void testGet_absent() {
        try {
            Optional.<String>absent().get();
            fail();
        } catch (IllegalStateException expected) {
        }
    }

    @Test
    public void testGet_present() {
        assertEquals("training", Optional.of("training").get());
    }

    @Test
    public void testOr_T_present() {
        assertEquals("a", Optional.of("a").or("default"));
    }

    @Test
    public void testOr_T_absent() {
        assertEquals("default", Optional.absent().or("default"));
    }

    @Test
    public void testOr_supplier_present() {
        assertEquals("a", Optional.of("a").or(Suppliers.ofInstance("fallback")));
    }

    @Test
    public void testOr_supplier_absent() {
        assertEquals("fallback", Optional.absent().or(Suppliers.ofInstance("fallback")));
    }

    @Test
    public void testOr_nullSupplier_absent() {
        Supplier<Object> nullSupplier = Suppliers.ofInstance(null);
        Optional<Object> absentOptional = Optional.absent();
        try {
            absentOptional.or(nullSupplier);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testOr_nullSupplier_present() {
        Supplier<String> nullSupplier = Suppliers.ofInstance(null);
        assertEquals("a", Optional.of("a").or(nullSupplier));
    }

    @Test
    public void testOr_option_present() {
        assertEquals(Optional.of("a"), Optional.of("a").or(Optional.of("fallback")));
    }

    @Test
    public void testOr_option_absent() {
        assertEquals(Optional.of("fallback"), Optional.absent().or(Optional.of("fallback")));
    }

    @Test
    public void testOrNull_present() {
        assertEquals("a", Optional.of("a").orNull());
    }

    @Test
    public void testOrNull_absent() {
        assertNull(Optional.absent().orNull());
    }

    @Test
    public void testAsSet_present() {
        Set<String> expected = Collections.singleton("a");
        assertEquals(expected, Optional.of("a").asSet());
    }

    @Test
    public void testAsSet_absent() {
        assertTrue("Returned set should be empty!", Optional.absent().asSet().isEmpty());
    }

    @Test
    public void testAsSet_presentIsImmutable() {
        Set<String> presentAsSet = Optional.of("a").asSet();
        try {
            presentAsSet.add("b");
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void testAsSet_absentIsImmutable() {
        Set<Object> absentAsSet = Optional.absent().asSet();
        try {
            absentAsSet.add("a");
        } catch (UnsupportedOperationException expected) {
        }
    }

    @Test
    public void testTransform_absent() {
        assertEquals(Optional.absent(), Optional.absent().transform(Functions.identity()));
        assertEquals(Optional.absent(), Optional.absent().transform(Functions.toStringFunction()));
    }

    @Test
    public void testTransform_presentIdentity() {
        assertEquals(Optional.of("a"), Optional.of("a").transform(Functions.identity()));
    }

    @Test
    public void testTransform_presentToString() {
        assertEquals(Optional.of("42"), Optional.of(42).transform(Functions.toStringFunction()));
    }

    @Test
    public void testTransform_present_functionReturnNull() {
        try {
            Optional.of("a").transform(input -> null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testTransform_absent_functionReturnNull() {
        assertEquals(Optional.absent(), Optional.absent().transform(input -> null));
    }

    @Test
    public void testEqualsAndHashCode_absent() {
        assertEquals(Optional.absent(), Optional.absent());
        assertEquals(Optional.absent().hashCode(), Optional.absent().hashCode());
        assertThat(Optional.absent().hashCode()).isNotEqualTo(Optional.of(0).hashCode());
    }

    @Test
    public void testEqualsAndHashCode_present() {
        assertEquals(Optional.of("training"), Optional.of("training"));
        assertFalse(Optional.of("a").equals(Optional.of("b")));
        assertFalse(Optional.of("a").equals(Optional.absent()));
        assertEquals(Optional.of("training").hashCode(), Optional.of("training").hashCode());
    }

    @Test
    public void testToString_absent() {
        assertEquals("Optional.absent()", Optional.absent().toString());
    }

    @Test
    public void testToString_present() {
        assertEquals("Optional.of(training)", Optional.of("training").toString());
    }

    @Test
    public void testPresentInstance_allPresent() {
        List<Optional<String>> optionals = ImmutableList.of(
                Optional.of("a"), Optional.of("b"), Optional.of("c"));
        assertThat(Optional.presentInstances(optionals)).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testPresentInstance_allAbsent() {
        List<Optional<Object>> optionals = ImmutableList.of(Optional.absent(), Optional.absent());
        assertThat(Optional.presentInstances(optionals)).isEmpty();
    }

    @Test
    public void testPresentInstance_somePresent() {
        List<Optional<String>> optionals = ImmutableList.of(Optional.of("a"), Optional.absent(), Optional.of("c"));
        assertThat(Optional.presentInstances(optionals)).containsExactly("a", "c").inOrder();
    }

    @Test
    public void testPresentInstance_callingIteratorTwice() {
        List<Optional<String>> optionals = ImmutableList.of(Optional.of("a"), Optional.absent(), Optional.of("c"));
        Iterable<String> onlyPresent = Optional.presentInstances(optionals);
        assertThat(onlyPresent).containsExactly("a", "c").inOrder();
        assertThat(onlyPresent).containsExactly("a", "c").inOrder();
    }

    @Test
    public void testPresentInstance_wildCards() {
        List<Optional<Number>> optionals = ImmutableList.of(Optional.absent(), Optional.of(2));
        Iterable<Number> onlyPresent = Optional.presentInstances(optionals);
        assertThat(onlyPresent).containsExactly(2).inOrder();
    }

    @Test
    public void testSerialization() {
        SerializableTester.reserializeAndAssert(Optional.absent());
        SerializableTester.reserializeAndAssert(Optional.of("foo"));
    }

    @Test
    public void testNullPointers() {
        NullPointerTester npTester = new NullPointerTester();
        npTester.testAllPublicConstructors(Optional.class);
        npTester.testAllPublicStaticMethods(Optional.class);
        npTester.testAllPublicInstanceMethods(Optional.absent());
        npTester.testAllPublicInstanceMethods(Optional.of("training"));
    }
}
