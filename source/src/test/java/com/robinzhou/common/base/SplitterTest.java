package com.robinzhou.common.base;


import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableMap;
import com.google.common.testing.NullPointerTester;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2015/9/5.
 */
public class SplitterTest {

    private static final Splitter COMMA_SPLITTER = Splitter.on(',');

    private static final Pattern literalDotPattern() {
        return Pattern.compile("\\.");
    }

    @Test
    public void testSplitNullString() {
        try {
            COMMA_SPLITTER.split(null);
            fail();
        } catch (NullPointerException expected) {
        }
    }

    @Test
    public void testCharacterSimpleSplit() {
        String simple = "a,b,c";
        Iterable<String> letters = COMMA_SPLITTER.split(simple);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testCharacterSimpleSplitToList() {
        String simple = "a,b,c";
        List<String> letters = COMMA_SPLITTER.splitToList(simple);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testToString() {
        assertEquals("[]", Splitter.on(",").split("").toString());
        assertEquals("[a, b, c]", Splitter.on(",").split("a,b,c").toString());
        assertEquals("[yam, bam, jam, ham]", Splitter.on(",").split("yam,bam,jam,ham").toString());
    }

    @Test
    public void testCharacterSimpleSplitWithNoDelimiter() {
        String simple = "a,b,c";
        Iterable<String> letters = Splitter.on(".").split(simple);
        assertThat(letters).containsExactly("a,b,c").inOrder();
    }

    @Test
    public void testCharacterSimpleSplitWithDoubleDelimiter() {
        String simple = "a,,b,c";
        Iterable<String> letters = COMMA_SPLITTER.split(simple);
        assertThat(letters).containsExactly("a", "", "b", "c").inOrder();
    }

    @Test
    public void testCharacterSimpleSplitWithDoubleDelimiterAndSpace() {
        String simple = "a,, b,c";
        Iterable<String> letters = COMMA_SPLITTER.split(simple);
        assertThat(letters).containsExactly("a", "", " b", "c").inOrder();
    }

    @Test
    public void testCharacterSimpleSplitWithTrailingDelimiter() {
        String simple = "a,b,c,";
        Iterable<String> letters = COMMA_SPLITTER.split(simple);
        assertThat(letters).containsExactly("a", "b", "c", "").inOrder();
    }

    @Test
    public void testCharacterSimpleSplitWithLeadingDelimiter() {
        String simple = ",a,b,c";
        Iterable<String> letters = COMMA_SPLITTER.split(simple);
        assertThat(letters).containsExactly("", "a", "b", "c");
    }

    @Test
    public void testCharacterSplitWithMultipleLetters() {
        Iterable<String> testCharacterMotto = Splitter.on("-").split("Testing-rocks-Debugging-sucks");
        assertThat(testCharacterMotto).containsExactly("Testing", "rocks", "Debugging", "sucks");
    }

    @Test
    public void testCharacterSplitWithMatcherLetters() {
        Iterable<String> testCharacterMotto = Splitter.on(CharMatcher.WHITESPACE).split("Testing\nrocks\tDebugging sucks");
        assertThat(testCharacterMotto).containsExactly("Testing", "rocks", "Debugging", "sucks").inOrder();
    }

    @Test
    public void testCharacterSplitWithDoubleDelimiterOmitEmptyStrings() {
        String simple = "a,,b,c";
        Iterable<String> letters = COMMA_SPLITTER.omitEmptyStrings().split(simple);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testCharacterSplitEmptyToken() {
        String simple = "a. .c";
        Iterable<String> letters = Splitter.on(".").trimResults().split(simple);
        assertThat(letters).containsExactly("a", "", "c").inOrder();
    }

    @Test
    public void testCharacterSplitEmptyTokenOmitEmptyStrings() {
        String simple = "a. .c";
        Iterable<String> letters = Splitter.on(".").trimResults().omitEmptyStrings().split(simple);
        assertThat(letters).containsExactly("a", "c").inOrder();
    }

    @Test
    public void testCharacterSplitOnEmptyString() {
        Iterable<String> nothing = Splitter.on(".").split("");
        assertThat(nothing).containsExactly("").inOrder();
    }

    @Test
    public void testCharacterSplitOnEmptyStringOmitEmptyStrings() {
        assertThat(Splitter.on(".").omitEmptyStrings().split("")).isEmpty();
    }

    @Test
    public void testCharacterSplitOnOnlyDelimiter() {
        Iterable<String> blankblank = Splitter.on(".").split(".");
        assertThat(blankblank).containsExactly("", "").inOrder();
    }

    @Test
    public void testCharacterSplitOnOnlyDelimiteOmitEmptyString() {
        Iterable<String> empty = Splitter.on(".").omitEmptyStrings().split(".");
        assertThat(empty).isEmpty();
    }

    @Test
    public void testCharacterSplitWithTrim() {
        String jacksons = "arfo(Marlon)aorf, (Michael)orfa, afro(Jackie)orfa, " +
                "ofar(Jemaine), aff(Tito)";
        Iterable<String> family = COMMA_SPLITTER.trimResults(CharMatcher.anyOf("afro").or(CharMatcher.WHITESPACE)).split(jacksons);
        assertThat(family).containsExactly("(Marlon)", "(Michael)", "(Jackie)", "(Jemaine)", "(Tito)");
    }

    @Test
    public void testStringSplitWithLongDelimiter() {
        String longDelimiter = "a, b, c";
        Iterable<String> letters = Splitter.on(", ").split(longDelimiter);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testStringSplitWithLongLeadingDelimiter() {
        String longDelimiter = ", a, b, c";
        Iterable<String> letters = Splitter.on(", ").split(longDelimiter);
        assertThat(letters).containsExactly("", "a", "b", "c").inOrder();
    }

    @Test
    public void testStringSplitWithLongTrailingDelimiter() {
        String longDelimiter = "a, b, c, ";
        Iterable<String> letters = Splitter.on(", ").split(longDelimiter);
        assertThat(letters).containsExactly("a", "b", "c", "").inOrder();
    }

    @Test
    public void testStringSplitWithSubstringInValue() {
        String fourCommasFourSpaces = ",,,,    ";
        Iterable<String> threeCommasThenThreeSpaces = Splitter.on(", ").split(fourCommasFourSpaces);
        assertThat(threeCommasThenThreeSpaces).containsExactly(",,,", "   ").inOrder();
    }

    @Test
    public void testStringSplitWithEmptyString() {
        try {
            Splitter.on("");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void testPatternSimpleSplit() {
        String simple = "a,b,c";
        Iterable<String> letters = Splitter.onPattern(",").split(simple);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testPatternSplitWithNoDelimiter() {
        String simple = "a,b,c";
        Iterable<String> letters = Splitter.onPattern("foo").split(simple);
        assertThat(letters).containsExactly("a,b,c").inOrder();
    }

    @Test
    public void testPatternSplitWithDoubleDelimiter() {
        String simple = "a,,b,c";
        Iterable<String> letters = Splitter.onPattern(",").split(simple);
        assertThat(letters).containsExactly("a", "", "b", "c").inOrder();
    }

    @Test
    public void testPatternSplitWithDoubleDelimiterAndSpace() {
        String simple = "a,, b,c";
        Iterable<String> letters = Splitter.onPattern(",").split(simple);
        assertThat(letters).containsExactly("a", "", " b", "c").inOrder();
    }

    @Test
    public void testPatternSplitWithTrailingDelimiter() {
        String simple = "a,b,c,";
        Iterable<String> letters = Splitter.onPattern(",").split(simple);
        assertThat(letters).containsExactly("a", "b", "c", "").inOrder();
    }

    @Test
    public void testPatternSplitWithLeadingDelimiter() {
        String simple = ",a,b,c";
        Iterable<String> letters = Splitter.onPattern(",").split(simple);
        assertThat(letters).containsExactly("", "a", "b", "c").inOrder();
    }

    @Test
    public void testPatternSplitWithmultipleLetters() {
        Iterable<String> testPatternMotto = Splitter.on("-").split("Testing-rocks-Debugging-sucks");
        assertThat(testPatternMotto).containsExactly("Testing", "rocks", "Debugging", "sucks").inOrder();
    }

    @Test
    public void testPatternSplitWithDoubleDelimiterOmitEmptyStrings() {
        String simple = "a..b.c";
        Iterable<String> letters = Splitter.on(literalDotPattern()).omitEmptyStrings().split(simple);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testPatternSplitLookBehind() {
        String toSplit = ":foo::barbaz:";
        String regexPattern = "(?<=foo):";
        Iterable<String> split = Splitter.onPattern(regexPattern).split(toSplit);
        assertThat(split).containsExactly(":foo", ":barbaz:").inOrder();
    }

    @Test
    public void testPatternSplitWordBoundary() {
        String toSplit = "foo<bar>bletch";
        Iterable<String> split = Splitter.onPattern("\\b").split(toSplit);
        assertThat(split).containsExactly("foo", "<", "bar", ">", "bletch").inOrder();
    }

    @Test
    public void testPatternSplitEmptyToken() {
        String toSplit = "a. .c";
        Iterable<String> letters = Splitter.on(literalDotPattern()).trimResults().split(toSplit);
        assertThat(letters).containsExactly("a", "", "c").inOrder();
    }


    @Test
    public void testPatternSplitEmptyTokenOmitEmptyString() {
        String toSplit = "a. .c";
        Iterable<String> letters = Splitter.on(literalDotPattern())
                .omitEmptyStrings().trimResults().split(toSplit);
        assertThat(letters).containsExactly("a", "c").inOrder();
    }

    @Test
    public void testPatternSplitOnOnlyDelimiter() {
        Iterable<String> blankblank = Splitter.on(literalDotPattern()).split(".");
        assertThat(blankblank).containsExactly("", "").inOrder();
    }

    @Test
    public void testPatternSPlitOnOnlyDelimiterOmitEmptyString() {
        Iterable<String> empty = Splitter.on(literalDotPattern()).omitEmptyStrings().split("...");
        assertThat(empty).isEmpty();
    }

    @Test
    public void testPatternSplitMatchingIsGreedy() {
        String longDelimiter = "a,b,    c";
        Iterable<String> letters = Splitter.on(Pattern.compile(",\\s*")).split(longDelimiter);
        assertThat(letters).containsExactly("a", "b", "c").inOrder();
    }

    @Test
    public void testPatternSplitWithLongLeadingDelimiter() {
        String longDelimiter = ", a, b, c";
        Iterable<String> letters = Splitter.on(Pattern.compile(", ")).split(longDelimiter);
        assertThat(letters).containsExactly("", "a", "b", "c").inOrder();
    }

    @Test
    public void testPatternSplitWithLongTrailingDelimiter() {
        String longDelimiter = "a, b, c/ ";
        Iterable<String> letters = Splitter.on(Pattern.compile("[,/]\\s")).split(longDelimiter);
        assertThat(letters).containsExactly("a", "b", "c", "").inOrder();
    }


    @Test
    public void testPatternSplitInvalidPattern() {
        try {
            Splitter.on(Pattern.compile("a*"));     //a* can match empty string
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void testPatternSplitWithTrim() {
        String jacksons = "arfo(Marlon)aorf, (Michael)orfa, afro(Jackie)orfa, "
                + "ofar(Jemaine), aff(Tito)";
        Iterable<String> family = Splitter.on(Pattern.compile(","))
                .trimResults(CharMatcher.anyOf("afro").or(CharMatcher.WHITESPACE))
                .split(jacksons);
        assertThat(family)
                .containsExactly("(Marlon)", "(Michael)", "(Jackie)", "(Jemaine)", "(Tito)")
                .inOrder();
    }

    @Test
    public void testSplitterIterableIsUnmodifiable_char() {
        assertIteratorIsUnmodifiable(COMMA_SPLITTER.split("a,b").iterator());
    }

    @Test
    public void testSplitterIterableIsUnmodifiable_string() {
        assertIteratorIsUnmodifiable(Splitter.on(',').split("a,b").iterator());
    }

    @Test
    public void testSplitterIterableIsUnmodifiable_pattern() {
        assertIteratorIsUnmodifiable(
                Splitter.on(Pattern.compile(",")).split("a,b").iterator());
    }

    private void assertIteratorIsUnmodifiable(Iterator<?> iterator) {
        iterator.next();
        try {
            iterator.remove();
            fail();
        } catch (UnsupportedOperationException expected) {
        }
    }


    @Test
    public void testSplitterIteratorIsLazy_String() {
        assertSplitterIterableIsLazy(COMMA_SPLITTER);
    }

    @Test
    public void testSplitterIteratorIsLazy_Char() {
        assertSplitterIterableIsLazy(Splitter.on(','));
    }

    @Test
    public void testSplitterIteratorIsLazy_Pattern() {
        assertSplitterIterableIsLazy(Splitter.on(Pattern.compile(",")));
    }

    private void assertSplitterIterableIsLazy(Splitter splitter) {
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = splitter.split(builder).iterator();

        builder.append("A,");
        assertEquals("A", iterator.next());
        builder.append("B,");
        assertEquals("B", iterator.next());
        builder.append("C");
        assertEquals("C", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void testFixedLengthSimpleSplit() {
        String simple = "abcde";
        Iterable<String> letters = Splitter.fixedLength(2).split(simple);
        assertThat(letters).containsExactly("ab", "cd", "e").inOrder();
    }

    @Test
    public void testFixedLengthSplitEqualChunkLength() {
        String simple = "abcdef";
        Iterable<String> letters = Splitter.fixedLength(2).split(simple);
        assertThat(letters).containsExactly("ab", "cd", "ef").inOrder();
    }

    @Test
    public void testFixedLengthSplitOnlyOneChunk() {
        String simple = "abc";
        Iterable<String> letters = Splitter.fixedLength(3).split(simple);
        assertThat(letters).containsExactly("abc").inOrder();
    }

    @Test
    public void testFixedLengthSplitSmallerString() {
        String simple = "ab";
        Iterable<String> letters = Splitter.fixedLength(3).split(simple);
        assertThat(letters).containsExactly("ab").inOrder();
    }

    @Test
    public void testFixedLengthSplitEmptyString() {
        String simple = "";
        Iterable<String> letters = Splitter.fixedLength(3).split(simple);
        assertThat(letters).containsExactly("").inOrder();
    }

    @Test
    public void testFixedLengthSplitEmptyStringOmitEmptyString() {
        assertThat(Splitter.fixedLength(3).omitEmptyStrings().split("")).isEmpty();
    }


    @Test
    public void testFixedLengthSplitIntoChars() {
        String simple = "abcd";
        Iterable<String> letters = Splitter.fixedLength(1).split(simple);
        assertThat(letters).containsExactly("a", "b", "c", "d").inOrder();
    }


    @Test
    public void testFixedLengthSplitZeroChunkLen() {
        try {
            Splitter.fixedLength(0);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }


    @Test
    public void testFixdLengthSplitNegativeChunkLen() {
        try {
            Splitter.fixedLength(-1);
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void testLimitLarge() {
        String simple = "abcd";
        Iterable<String> letters = Splitter.fixedLength(1).limit(100).split(simple);
        assertThat(letters).containsExactly("a", "b", "c", "d").inOrder();
    }

    @Test
    public void testLimitOne() {
        String simple = "abcd";
        Iterable<String> letters = Splitter.fixedLength(1).limit(1).split(simple);
        assertThat(letters).containsExactly("abcd").inOrder();
    }

    @Test
    public void testLimitFixedLength() {
        String simple = "abcd";
        Iterable<String> letters = Splitter.fixedLength(1).limit(2).split(simple);
        assertThat(letters).containsExactly("a", "bcd").inOrder();
    }

    @Test
    public void testLimitSeparator() {
        String simple = "a,b,c,d";
        Iterable<String> letters = COMMA_SPLITTER.limit(2).split(simple);
        assertThat(letters).containsExactly("a", "b,c,d").inOrder();
    }

    @Test
    public void testNullPointers() {
        NullPointerTester tester = new NullPointerTester();
        tester.testAllPublicStaticMethods(Splitter.class);
        tester.testAllPublicInstanceMethods(Splitter.on(','));
        tester.testAllPublicInstanceMethods(Splitter.on(',').trimResults());
    }

    @Test
    public void testMapSplitter_trimmedBoth() {
        Map<String, String> m = COMMA_SPLITTER
                .trimResults()
                .withKeyValueSeparator(Splitter.on(':').trimResults())
                .split("boy  : tom , girl: tina , cat  : kitty , dog: tommy ");
        ImmutableMap<String, String> expected =
                ImmutableMap.of("boy", "tom", "girl", "tina", "cat", "kitty", "dog", "tommy");
        assertThat(m).isEqualTo(expected);
        assertThat(m.entrySet()).containsExactlyElementsIn(expected.entrySet()).inOrder();
    }

    @Test
    public void testMapSplitter_trimmedEntries() {
        Map<String, String> m = COMMA_SPLITTER
                .trimResults()
                .withKeyValueSeparator(":")
                .split("boy  : tom , girl: tina , cat  : kitty , dog: tommy ");
        ImmutableMap<String, String> expected =
                ImmutableMap.of("boy  ", " tom", "girl", " tina", "cat  ", " kitty", "dog", " tommy");

        assertThat(m).isEqualTo(expected);
        assertThat(m.entrySet()).containsExactlyElementsIn(expected.entrySet()).inOrder();
    }

    @Test
    public void testMapSplitter_trimmedKeyValue() {
        Map<String, String> m =
                COMMA_SPLITTER.withKeyValueSeparator(Splitter.on(':').trimResults()).split(
                        "boy  : tom , girl: tina , cat  : kitty , dog: tommy ");
        ImmutableMap<String, String> expected =
                ImmutableMap.of("boy", "tom", "girl", "tina", "cat", "kitty", "dog", "tommy");
        assertThat(m).isEqualTo(expected);
        assertThat(m.entrySet()).containsExactlyElementsIn(expected.entrySet()).inOrder();
    }

    @Test
    public void testMapSplitter_notTrimmed() {
        Map<String, String> m = COMMA_SPLITTER.withKeyValueSeparator(":").split(
                " boy:tom , girl: tina , cat :kitty , dog:  tommy ");
        ImmutableMap<String, String> expected =
                ImmutableMap.of(" boy", "tom ", " girl", " tina ", " cat ", "kitty ", " dog", "  tommy ");
        assertThat(m).isEqualTo(expected);
        assertThat(m.entrySet()).containsExactlyElementsIn(expected.entrySet()).inOrder();
    }

    @Test
    public void testMapSplitter_CharacterSeparator() {
        // try different delimiters.
        Map<String, String> m = Splitter
                .on(",")
                .withKeyValueSeparator(':')
                .split("boy:tom,girl:tina,cat:kitty,dog:tommy");
        ImmutableMap<String, String> expected =
                ImmutableMap.of("boy", "tom", "girl", "tina", "cat", "kitty", "dog", "tommy");

        assertThat(m).isEqualTo(expected);
        assertThat(m.entrySet()).containsExactlyElementsIn(expected.entrySet()).inOrder();
    }

    @Test
    public void testMapSplitter_multiCharacterSeparator() {
        // try different delimiters.
        Map<String, String> m = Splitter
                .on(",")
                .withKeyValueSeparator(":^&")
                .split("boy:^&tom,girl:^&tina,cat:^&kitty,dog:^&tommy");
        ImmutableMap<String, String> expected =
                ImmutableMap.of("boy", "tom", "girl", "tina", "cat", "kitty", "dog", "tommy");

        assertThat(m).isEqualTo(expected);
        assertThat(m.entrySet()).containsExactlyElementsIn(expected.entrySet()).inOrder();
    }

    @Test
    public void testMapSplitter_emptySeparator() {
        try {
            COMMA_SPLITTER.withKeyValueSeparator("");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void testMapSplitter_malformedEntry() {
        try {
            COMMA_SPLITTER.withKeyValueSeparator("=").split("a=1,b,c=2");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }

    @Test
    public void testMapSplitter_orderedResults() {
        Map<String, String> m = Splitter.on(',')
                .withKeyValueSeparator(":")
                .split("boy:tom,girl:tina,cat:kitty,dog:tommy");

        assertThat(m.keySet()).containsExactly("boy", "girl", "cat", "dog").inOrder();
        assertThat(m).isEqualTo(
                ImmutableMap.of("boy", "tom", "girl", "tina", "cat", "kitty", "dog", "tommy"));

        // try in a different order
        m = Splitter.on(',')
                .withKeyValueSeparator(":")
                .split("girl:tina,boy:tom,dog:tommy,cat:kitty");

        assertThat(m.keySet()).containsExactly("girl", "boy", "dog", "cat").inOrder();
        assertThat(m).isEqualTo(
                ImmutableMap.of( "girl", "tina","boy", "tom", "cat", "kitty", "dog", "tommy"));
    }

    @Test
    public void testMapSplitter_duplicateKeys() {
        try {
            Splitter.on(',').withKeyValueSeparator(":").split("a:1,b:2,a:3");
            fail();
        } catch (IllegalArgumentException expected) {
        }
    }


}
