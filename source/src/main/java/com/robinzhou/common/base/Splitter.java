package com.robinzhou.common.base;

import com.google.common.base.CharMatcher;
import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

/**
 * Created by robinzhou on 2015/9/7.
 */
public final class Splitter {
    private final CharMatcher trimmer;
    private final boolean omitEmptyStrings;
    private final Strategy strategy;
    private final int limit;

    private Splitter(Strategy strategy) {
        this(Integer.MAX_VALUE, strategy, false, CharMatcher.NONE);
    }

    private Splitter(int limit, Strategy strategy, boolean omitEmptyStrings, CharMatcher trimmer) {
        this.limit = limit;
        this.strategy = strategy;
        this.omitEmptyStrings = omitEmptyStrings;
        this.trimmer = trimmer;
    }


    public Splitter on(CharMatcher separatorMatcher) {
        
    }

    private interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence toSplit);
    }

    private abstract static class SplittingIterator extends AbstractIterator<String> {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyString;

        abstract int separatorStart(int start);

        abstract int separatorEnd(int separatorPosition);

        int offset = 0;
        int limit;

        protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyString = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }

        @Override
        protected String computeNext() {
            int nextStart = offset;
            while (offset != -1) {
                int start = nextStart;
                int end;

                int separatorPosition = separatorStart(offset);
                if (separatorPosition == -1) {
                    end = toSplit.length();
                    offset = -1;
                } else {
                    end = separatorPosition;
                    offset = separatorEnd(separatorPosition);
                }
                if (offset == nextStart) {
                    offset++;
                    if (offset >= toSplit.length()) {
                        offset = -1;
                    }
                    continue;
                }

                while (start < end && trimmer.matches(toSplit.charAt(start))) {
                    start++;
                }
                while (start < end && trimmer.matches(toSplit.charAt(end - 1))) {
                    end--;
                }

                if (omitEmptyString && start == end) {
                    nextStart = offset;
                    continue;
                }

                if (limit == 1) {
                    end = toSplit.length();
                    offset = -1;
                    while (end > start && trimmer.matches(toSplit.charAt(end - 1))) {
                        end--;
                    }
                } else {
                    limit--;
                }

                return toString().subSequence(start, end).toString();
            }
            return endOfData();
        }
    }
}
