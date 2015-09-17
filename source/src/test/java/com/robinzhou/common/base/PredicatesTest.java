package com.robinzhou.common.base;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;
import com.google.common.testing.ClassSanityTester;
import com.google.common.testing.EqualsTester;
import com.google.common.testing.NullPointerTester;
import com.google.common.testing.SerializableTester;
import junit.framework.AssertionFailedError;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2015/9/16.
 */
public class PredicatesTest {

    private static final Predicate<Integer> TRUE = Predicates.alwaysTrue();
    private static final Predicate<Integer> FALSE = Predicates.alwaysFalse();
    private static final Predicate<Integer> NEVER_REACHED = input -> {
        throw new AssertionFailedError("This predicate should never have been evaluated");
    };

    private static IsOdd isOdd() {
        return new IsOdd();
    }

    private static void assertEvalsToTrue(Predicate<? super Integer> predicate) {
        assertTrue(predicate.apply(1));
        assertTrue(predicate.apply(0));
        assertTrue(predicate.apply(null));
    }

    private static void checkSerialization(Predicate<? super Integer> predicate) {
        Predicate<? super Integer> reserialized = SerializableTester.reserializeAndAssert(predicate);
        assertEvalsLike(predicate, reserialized);
    }

    private static void assertEvalsLike(Predicate<? super Integer> expected, Predicate<? super Integer> actual) {
        assertEvalsLike(expected, actual, 0);
        assertEvalsLike(expected, actual, 1);
        assertEvalsLike(expected, actual, null);
    }

    private static <T> void assertEvalsLike(Predicate<? super T> expected, Predicate<? super T> actual, T input) {
        Boolean expectedResult = null;
        RuntimeException expectedRuntimeException = null;
        try {
            expectedResult = expected.apply(input);
        } catch (RuntimeException e) {
            expectedRuntimeException = e;
        }

        Boolean actualResult = null;
        RuntimeException actualRuntimeException = null;
        try {
            actualResult = actual.apply(input);
        } catch (RuntimeException e) {
            actualRuntimeException = e;
        }

        assertEquals(expectedResult, actualResult);
        if (expectedRuntimeException != null) {
            assertNotNull(actualRuntimeException);
            assertEquals(
                    expectedRuntimeException.getClass(),
                    actualRuntimeException.getClass());
        }
    }

    private static void assertEvalsToFalse(Predicate<? super Integer> predicate) {
        assertFalse(predicate.apply(0));
        assertFalse(predicate.apply(1));
        assertFalse(predicate.apply(null));
    }

    private static void assertEvalsLikeOdd(Predicate<? super Integer> predicate) {
        assertEvalsLike(isOdd(), predicate);
    }

    @Test
    public void testAlwaysTrue_apply() {
        assertEvalsToTrue(Predicates.alwaysTrue());
    }

    @Test
    public void testAlwaysTrue_equality() {
        new EqualsTester().addEqualityGroup(TRUE, Predicates.alwaysTrue())
                .addEqualityGroup(isOdd())
                .addEqualityGroup(Predicates.alwaysFalse())
                .testEquals();
    }

    @Test
    public void testAlwaysTrue_serialization() {
        checkSerialization(Predicates.alwaysTrue());
    }

    @Test
    public void testAlwaysFalse_apply() {
        assertEvalsToFalse(Predicates.alwaysFalse());
    }

    @Test
    public void testAlwaysFalse_equality() {
        new EqualsTester().addEqualityGroup(FALSE, Predicates.alwaysFalse())
                .addEqualityGroup(isOdd())
                .addEqualityGroup(Predicates.alwaysTrue())
                .testEquals();
    }

    @Test
    public void testAlwaysFalse_serialization() {
        checkSerialization(Predicates.alwaysFalse());
    }

    @Test
    public void testNot_apply() {
        assertEvalsToTrue(Predicates.not(FALSE));
        assertEvalsToFalse(Predicates.not(TRUE));
        assertEvalsLikeOdd(Predicates.not(Predicates.not(isOdd())));
    }

    @Test
    public void testNot_equality() {
        new EqualsTester().addEqualityGroup(Predicates.not(isOdd()), Predicates.not(isOdd()))
                .addEqualityGroup(Predicates.not(TRUE))
                .addEqualityGroup(isOdd())
                .testEquals();
    }

    @Test
    public void testNot_equalityForNotOfKnownValues() {
        new EqualsTester()
                .addEqualityGroup(TRUE, Predicates.alwaysTrue())
                .addEqualityGroup(FALSE)
                .addEqualityGroup(Predicates.not(TRUE))
                .testEquals();

        new EqualsTester()
                .addEqualityGroup(FALSE, Predicates.alwaysFalse())
                .addEqualityGroup(TRUE)
                .addEqualityGroup(Predicates.not(FALSE))
                .testEquals();

        new EqualsTester()
                .addEqualityGroup(Predicates.isNull(), Predicates.isNull())
                .addEqualityGroup(Predicates.notNull())
                .addEqualityGroup(Predicates.not(Predicates.isNull()))
                .testEquals();

        new EqualsTester()
                .addEqualityGroup(Predicates.notNull(), Predicates.notNull())
                .addEqualityGroup(Predicates.isNull())
                .addEqualityGroup(Predicates.not(Predicates.notNull()))
                .testEquals();
    }

    @Test
    public void testNot_serialization() {
        checkSerialization(Predicates.not(isOdd()));
    }


    @Test
    public void testAnd_applyNoArgs() {
        assertEvalsToTrue(Predicates.and());
    }

    @Test
    public void testAnd_equalityNoArgs() {
        new EqualsTester().addEqualityGroup(Predicates.and(), Predicates.and())
                .addEqualityGroup(Predicates.and(FALSE))
                .addEqualityGroup(Predicates.or())
                .testEquals();
    }

    @Test
    public void testAnd_serializationNoArgs() {
        checkSerialization(Predicates.and());
    }

    @Test
    public void testAnd_applyOneArgs() {
        assertEvalsLikeOdd(Predicates.and(isOdd()));
    }

    @Test
    public void testAnd_equalityOneArg() {
        Object[] notEqualObjects = {Predicates.and(NEVER_REACHED, FALSE)};
        new EqualsTester()
                .addEqualityGroup(
                        Predicates.and(NEVER_REACHED), Predicates.and(NEVER_REACHED))
                .addEqualityGroup(notEqualObjects)
                .addEqualityGroup(Predicates.and(isOdd()))
                .addEqualityGroup(Predicates.and())
                .addEqualityGroup(Predicates.or(NEVER_REACHED))
                .testEquals();
    }

    @Test
    public void testAnd_serializationOneArg() {
        checkSerialization(Predicates.and(isOdd()));
    }

    @Test
    public void testAnd_applyBinary() {
        assertEvalsLikeOdd(Predicates.and(TRUE, isOdd()));
        assertEvalsLikeOdd(Predicates.and(isOdd(), TRUE));
        assertEvalsToFalse(Predicates.and(FALSE, NEVER_REACHED));
    }

    @Test
    public void testAnd_equalityBinary() {
        new EqualsTester()
                .addEqualityGroup(
                        Predicates.and(TRUE, NEVER_REACHED),
                        Predicates.and(TRUE, NEVER_REACHED))
                .addEqualityGroup(Predicates.and(NEVER_REACHED, TRUE))
                .addEqualityGroup(Predicates.and(TRUE))
                .addEqualityGroup(Predicates.or(TRUE, NEVER_REACHED))
                .testEquals();
    }

    @Test
    public void testAnd_serializationBinary() {
        checkSerialization(Predicates.and(TRUE, isOdd()));
    }

    @Test
    public void testAnd_applyTernary() {
        assertEvalsLikeOdd(Predicates.and(TRUE, TRUE, isOdd()));
        assertEvalsLikeOdd(Predicates.and(TRUE, isOdd(), TRUE));
        assertEvalsLikeOdd(Predicates.and(isOdd(), TRUE, TRUE));
        assertEvalsToFalse(Predicates.and(TRUE, FALSE, NEVER_REACHED));

    }

    @Test
    public void testAnd_equalityTernary() {
        new EqualsTester()
                .addEqualityGroup(
                        Predicates.and(TRUE, isOdd(), NEVER_REACHED),
                        Predicates.and(TRUE, isOdd(), NEVER_REACHED))
                .addEqualityGroup(Predicates.and(isOdd(), NEVER_REACHED, TRUE))
                .addEqualityGroup(Predicates.and(TRUE))
                .addEqualityGroup(Predicates.or(TRUE, isOdd(), NEVER_REACHED))
                .testEquals();
    }

    @Test
    public void testAnd_serializationTernary() {
        checkSerialization(Predicates.and(TRUE, isOdd(), FALSE));
    }

    @Test
    public void testIsEqualTo_apply() {
        Predicate<Integer> isOne = Predicates.equalTo(1);
        assertTrue(isOne.apply(1));
        assertFalse(isOne.apply(2));
        assertFalse(isOne.apply(null));
    }

    @Test
    public void testIsEqualTo_equality() {
        new EqualsTester()
                .addEqualityGroup(Predicates.equalTo(1), Predicates.equalTo(1))
                .addEqualityGroup(Predicates.equalTo(2))
                .addEqualityGroup(Predicates.equalTo(null))
                .testEquals();
    }

    @Test
    public void testIsEqualTo_serialization() {
        checkSerialization(Predicates.equalTo(1));
    }

    @Test
    public void testIsEqualToNull_apply() {
        Predicate<Integer> isNull = Predicates.equalTo(null);
        assertFalse(isNull.apply(1));
        assertTrue(isNull.apply(null));
    }

    @Test
    public void testIsEqualToNull_equality() {
        new EqualsTester()
                .addEqualityGroup(Predicates.equalTo(null), Predicates.equalTo(null))
                .addEqualityGroup(Predicates.equalTo(1))
                .addEqualityGroup(Predicates.equalTo("null"))
                .testEquals();
    }

    @Test
    public void testIsEqualToNull_serialization() {
        checkSerialization(Predicates.equalTo(null));
    }

    @Test
    public void testIsInstanceOf_apply() {
        Predicate<Object> isInteger = Predicates.instanceOf(Integer.class);
        assertTrue(isInteger.apply(1));
        assertFalse(isInteger.apply(2.0));
        assertFalse(isInteger.apply(""));
        assertFalse(isInteger.apply(null));
    }

    @Test
    public void testIsInstanceOf_subclass() {
        Predicate<Object> isNumber = Predicates.instanceOf(Number.class);
        assertTrue(isNumber.apply(1));
        assertTrue(isNumber.apply(2.0));
        assertFalse(isNumber.apply(""));
        assertFalse(isNumber.apply(null));
    }

    @Test
    public void testIsInstanceOf_interface() {
        Predicate<Object> isInterface = Predicates.instanceOf(Comparable.class);
        assertTrue(isInterface.apply(1));
        assertTrue(isInterface.apply(2.0));
        assertTrue(isInterface.apply(""));
        assertFalse(isInterface.apply(null));
    }

    @Test
    public void testIsInstanceOf_equality() {
        new EqualsTester()
                .addEqualityGroup(
                        Predicates.instanceOf(Integer.class),
                        Predicates.instanceOf(Integer.class))
                .addEqualityGroup(Predicates.instanceOf(Number.class))
                .addEqualityGroup(Predicates.instanceOf(Float.class))
                .testEquals();
    }

    @Test
    public void testIsInstanceOf_serialization() {
        checkSerialization(Predicates.instanceOf(Integer.class));
    }

    @Test
    public void testIsAssignableFrom_apply() {
        Predicate<Class<?>> isInteger = Predicates.assignableFrom(Integer.class);
        assertTrue(isInteger.apply(Integer.class));
        assertFalse(isInteger.apply(Float.class));

        try {
            isInteger.apply(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testIsAssignableFrom_subclass() {
        Predicate<Class<?>> isNumber = Predicates.assignableFrom(Number.class);
        assertTrue(isNumber.apply(Integer.class));
        assertTrue(isNumber.apply(Float.class));
    }

    @Test
    public void testIsAssignable_comparable() {
        Predicate<Class<?>> isComparable = Predicates.assignableFrom(Comparable.class);
        assertTrue(isComparable.apply(Integer.class));
        assertTrue(isComparable.apply(Float.class));
    }

    @Test
    public void testIsAssignableFrom_equality() {
        new EqualsTester()
                .addEqualityGroup(
                        Predicates.assignableFrom(Integer.class),
                        Predicates.assignableFrom(Integer.class))
                .addEqualityGroup(Predicates.assignableFrom(Number.class))
                .addEqualityGroup(Predicates.assignableFrom(Float.class))
                .testEquals();
    }

    @Test
    public void testIsAssignableFrom_serialization() {
        Predicate<Class<?>> predicate =
                Predicates.assignableFrom(Integer.class);
        Predicate<Class<?>> reserialized =
                SerializableTester.reserializeAndAssert(predicate);

        assertEvalsLike(predicate, reserialized, Integer.class);
        assertEvalsLike(predicate, reserialized, Float.class);
        assertEvalsLike(predicate, reserialized, null);
    }

    @Test
    public void testIsNull_apply() {
        Predicate<Integer> isNull = Predicates.isNull();
        assertTrue(isNull.apply(null));
        assertFalse(isNull.apply(1));
    }

    @Test
    public void testIsNull_equality() {
        new EqualsTester().addEqualityGroup(Predicates.isNull(), Predicates.isNull())
                .addEqualityGroup(Predicates.notNull())
                .testEquals();
    }

    @Test
    public void testIsNull_serialization() {
        checkSerialization(Predicates.isNull());
    }

    @Test
    public void testNotNull_apply() {
        Predicate<Integer> notNull = Predicates.notNull();
        assertTrue(notNull.apply(1));
        assertFalse(notNull.apply(null));
    }

    @Test
    public void testNotNull_equality() {
        new EqualsTester().addEqualityGroup(Predicates.notNull(), Predicates.notNull())
                .addEqualityGroup(Predicates.isNull())
                .testEquals();
    }

    @Test
    public void testNotNull_serialization() {
        checkSerialization(Predicates.notNull());
    }

    @Test
    public void testIn_apply() {
        Collection<Integer> nums = Arrays.asList(1, 5);
        Predicate<Integer> isOneOrFive = Predicates.in(nums);
        assertTrue(isOneOrFive.apply(1));
        assertTrue(isOneOrFive.apply(5));
        assertFalse(isOneOrFive.apply(3));
        assertFalse(isOneOrFive.apply(null));
    }

    @Test
    public void testIn_equality() {
        Collection<Integer> nums = ImmutableSet.of(1, 5);
        Collection<Integer> sameOrder = ImmutableSet.of(1, 5);
        Collection<Integer> differentOrder = ImmutableSet.of(5, 1);
        Collection<Integer> differentNums = ImmutableSet.of(1, 3, 5);

        new EqualsTester().addEqualityGroup(Predicates.in(nums), Predicates.in(nums),
                Predicates.in(sameOrder), Predicates.in(differentOrder))
                .addEqualityGroup(Predicates.in(differentNums))
                .testEquals();
    }

    @Test
    public void testIn_serialization() {
        checkSerialization(Predicates.in(Arrays.asList(1, 2, 3, null)));
    }

    @Test
    public void testIn_handlesNullPointerException() {
        class CollectionThatThrowsNPE<T> extends ArrayList<T> {
            @Override
            public boolean contains(Object element) {
                checkNotNull(element);
                return super.contains(element);
            }
        }
        Collection<Integer> nums = new CollectionThatThrowsNPE<>();
        Predicate<Integer> isFalse = Predicates.in(nums);
        assertFalse(isFalse.apply(null));
    }

    @Test
    public void testIn_handlesClassCastException() {
        class CollectionThatThrowsCCE<T> extends ArrayList<T> {
            @Override
            public boolean contains(Object element) {
                throw new ClassCastException("");
            }
        }
        Collection<Integer> nums = new CollectionThatThrowsCCE<>();
        Predicate<Integer> isFalse = Predicates.in(nums);
        assertFalse(isFalse.apply(null));
    }

    @Test
    public void testIn_compilesWithExplicitSupertype() {
        Collection<Number> nums = ImmutableSet.of();
        Predicate<Number> p1 = Predicates.in(nums);
        Predicate<Object> p2 = Predicates.<Object>in(nums);
        // The next two lines are not expected to compile.
//         Predicate<Integer> p3 = Predicates.in(nums);
//         Predicate<Integer> p4 = Predicates.<Integer>in(nums);
    }

    @Test
    public void testNullPointerExceptions() {
        NullPointerTester tester = new NullPointerTester();
        tester.testAllPublicStaticMethods(Predicates.class);
    }

    @Test
    public void testCascadingSerialization() throws Exception {
        // Eclipse says Predicate<Integer>; javac says Predicate<Object>.
        Predicate<? super Integer> nasty = Predicates.not(Predicates.and(
                Predicates.or(
                        Predicates.equalTo((Object) 1), Predicates.equalTo(null),
                        Predicates.alwaysFalse(), Predicates.alwaysTrue(),
                        Predicates.isNull(), Predicates.notNull(),
                        Predicates.in(Arrays.asList(1)))));
        assertEvalsToFalse(nasty);

        Predicate<? super Integer> stillNasty =
                SerializableTester.reserializeAndAssert(nasty);

        assertEvalsToFalse(stillNasty);
    }

    @Test
    public void testCompose() {
        Function<String, String> trim = TrimStringFunction.INSTANCE;
        Predicate<String> equalsFoo = Predicates.equalTo("Foo");
        Predicate<String> equalsBar = Predicates.equalTo("Bar");
        Predicate<String> trimEqualsFoo = Predicates.compose(equalsFoo, trim);
        Function<String, String> identity = Functions.identity();

        assertTrue(trimEqualsFoo.apply("Foo"));
        assertTrue(trimEqualsFoo.apply("   Foo   "));
        assertFalse(trimEqualsFoo.apply("Foo-q"));

        new EqualsTester().addEqualityGroup(trimEqualsFoo, Predicates.compose(equalsFoo, trim))
                .addEqualityGroup(equalsFoo)
                .addEqualityGroup(trim)
                .addEqualityGroup(Predicates.compose(equalsBar, trim))
                .addEqualityGroup(Predicates.compose(equalsFoo, identity))
                .testEquals();
    }

    @Test
    public void testComposeSerialization() {
        Function<String, String> trim = TrimStringFunction.INSTANCE;
        Predicate<String> equalsFoo = Predicates.equalTo("Foo");
        Predicate<String> trimEqualsFoo = Predicates.compose(equalsFoo, trim);
        SerializableTester.reserializeAndAssert(trimEqualsFoo);
    }

    @Test
    public void testContainsPattern_apply() {
        Predicate<CharSequence> isFoobar = Predicates.containsPattern("^Fo.*o.*bar$");
        assertTrue(isFoobar.apply("Foxyzoabcbar"));
        assertFalse(isFoobar.apply("Foobarx"));
    }

    @Test
    public void testContains_apply() {
        Predicate<CharSequence> isFoobar = Predicates.contains(Pattern.compile("^Fo.*o.*bar$"));
        assertTrue(isFoobar.apply("Foxyzoabcbar"));
        assertFalse(isFoobar.apply("Foobarx"));
    }

    @Test
    public void testContainsPattern_nulls() throws Exception {
        NullPointerTester tester = new NullPointerTester();
        Predicate<CharSequence> isWooString = Predicates.containsPattern("Woo");

        tester.testAllPublicInstanceMethods(isWooString);
    }

    @Test
    public void testContains_nulls() throws Exception {
        NullPointerTester tester = new NullPointerTester();
        Predicate<CharSequence> isWooPattern =
                Predicates.contains(Pattern.compile("Woo"));

        tester.testAllPublicInstanceMethods(isWooPattern);
    }

    @Test
    public void testContainsPattern_serialization() {
        Predicate<CharSequence> pre = Predicates.containsPattern("foo");
        Predicate<CharSequence> post = SerializableTester.reserializeAndAssert(pre);
        assertEquals(pre.apply("foo"), post.apply("foo"));
    }

    @Test
    public void testContains_equals() {
        new EqualsTester()
                .addEqualityGroup(
                        Predicates.contains(Pattern.compile("foo")),
                        Predicates.containsPattern("foo"))
                .addEqualityGroup(
                        Predicates.contains(
                                Pattern.compile("foo", Pattern.CASE_INSENSITIVE)))
                .addEqualityGroup(
                        Predicates.containsPattern("bar"))
                .testEquals();
    }

    @Test
    public void testNulls() throws Exception {
        new ClassSanityTester().forAllPublicStaticMethods(Predicates.class).testNulls();
    }

    @Test
    public void testEqualsAndSerializable() throws Exception {
        new ClassSanityTester().forAllPublicStaticMethods(Predicates.class).testEqualsAndSerializable();
    }

    public void assertEqualHashCode(
            Predicate<? super Integer> expected, Predicate<? super Integer> actual) {
        assertEquals(actual + " should hash like " + expected, expected.hashCode(), actual.hashCode());
    }

    @Test
    public void testHashCodeForBooleanOperations() {
        Predicate<Integer> p1 = Predicates.isNull();
        Predicate<Integer> p2 = isOdd();

        // Make sure that hash codes are not computed per-instance.
        assertEqualHashCode(
                Predicates.not(p1),
                Predicates.not(p1));

        assertEqualHashCode(
                Predicates.and(p1, p2),
                Predicates.and(p1, p2));

        assertEqualHashCode(
                Predicates.or(p1, p2),
                Predicates.or(p1, p2));

        // While not a contractual requirement, we'd like the hash codes for ands
        // & ors of the same predicates to not collide.
        assertTrue(Predicates.and(p1, p2).hashCode() != Predicates.or(p1, p2).hashCode());
    }

    private enum TrimStringFunction implements Function<String, String> {
        INSTANCE;

        @Override
        public String apply(String str) {
            return CharMatcher.WHITESPACE.trimFrom(str);
        }
    }

    static class IsOdd implements Predicate<Integer>, Serializable {

        private static final long serialVersionUID = 0x150ddL;

        @Override
        public boolean apply(Integer input) {
            return (input.intValue() & 1) == 1;
        }

        @Override
        public int hashCode() {
            return 0x150dd;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof IsOdd;
        }

        @Override
        public String toString() {
            return "IsOdd";
        }
    }

}
