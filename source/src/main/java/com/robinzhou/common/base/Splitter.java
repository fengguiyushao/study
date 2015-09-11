package com.robinzhou.common.base;

import com.google.common.base.CharMatcher;
import com.google.common.collect.AbstractIterator;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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


    public static Splitter on(CharMatcher separatorMatcher) {
        checkNotNull(separatorMatcher);

        return new Splitter((splitter, toSplit) -> new SplittingIterator(splitter, toSplit) {
            @Override
            int separatorStart(int start) {
                return separatorMatcher.indexIn(toSplit, start);
            }

            @Override
            int separatorEnd(int separatorPosition) {
                return separatorPosition + 1;
            }
        });
    }

    public static Splitter on(char separator) {
        return on(CharMatcher.is(separator));
    }

    public static Splitter on(String separator) {
        checkArgument(separator.length() != 0, "The separator may not be the empty string.");

        return new Splitter(((splitter, toSplit) -> new SplittingIterator(splitter, toSplit) {
            @Override
            int separatorStart(int start) {
                int delimeterLength = separator.length();

                positions:
                for (int p = start, last = toSplit.length() - delimeterLength; p <= last; p++) {
                    for (int i = 0; i < delimeterLength; i++) {
                        if (toSplit.charAt(i + p) != separator.charAt(i)) {
                            continue positions;
                        }
                    }
                    return p;
                }
                return -1;
            }

            @Override
            int separatorEnd(int separatorPosition) {
                return separatorPosition + separator.length();
            }
        }));
    }

    public static Splitter on(Pattern separatorPattern) {
        checkNotNull(separatorPattern);
        checkArgument(!separatorPattern.matcher("").matches(),
                "The Pattern may not match the empty String: %s", separatorPattern);
        return new Splitter((splitter, toSplit) -> {
            Matcher matcher = separatorPattern.matcher(toSplit);
            return new SplittingIterator(splitter, toSplit) {
                @Override
                int separatorStart(int start) {
                    return matcher.find(start) ? matcher.start() : -1;
                }

                @Override
                int separatorEnd(int separatorPosition) {
                    return matcher.end();
                }
            };
        });
    }

    public static Splitter onPattern(String separatorPattern) {
        return on(Pattern.compile(separatorPattern));
    }

    public static Splitter fixedLength(int length) {
        checkArgument(length > 0, "The length may not be less than 1");

        return new Splitter(((splitter, toSplit) -> new SplittingIterator(splitter, toSplit) {
            @Override
            int separatorStart(int start) {
                int nextChunkStart = start + length;
                return (nextChunkStart < toSplit.length() ? nextChunkStart : -1);
            }

            @Override
            int separatorEnd(int separatorPosition) {
                return separatorPosition;
            }
        }));
    }

    public Splitter omitEmptyStrings() {
        return new Splitter(limit, strategy, true, trimmer);
    }

    public Splitter limit(int limit) {
        checkArgument(limit > 0, "must be greater than zero:%s", limit);
        return new Splitter(limit, strategy, omitEmptyStrings, trimmer);
    }

    public Splitter trimResults() {
        return trimResults(CharMatcher.WHITESPACE);
    }

    public Splitter trimResults(CharMatcher trimmer) {
        checkNotNull(trimmer);
        return new Splitter(limit, strategy, omitEmptyStrings, trimmer);
    }

    public Iterable<String> split(CharSequence sequence) {
        checkNotNull(sequence);

        return new Iterable<String>() {
            @Override
            public Iterator<String> iterator() {
                return splittingIterator(sequence);
            }

            @Override
            public String toString() {
                return Joiner.on(", ").appendTo(new StringBuilder("["), iterator()).append("]").toString();
            }
        };
    }

    public List<String> splitToList(CharSequence sequence) {
        checkNotNull(sequence);

        Iterator<String> iterator = splittingIterator(sequence);
        List<String> result = new ArrayList<>();

        while (iterator.hasNext()) {
            result.add(iterator.next());
        }

        return Collections.unmodifiableList(result);
    }

    private Iterator<String> splittingIterator(CharSequence sequence) {
        return strategy.iterator(this, sequence);
    }

    public MapSplitter withKeyValueSeparator(String separator) {
        return withKeyValueSeparator(on(separator));
    }

    public MapSplitter withKeyValueSeparator(char separator) {
        return withKeyValueSeparator(on(separator));
    }

    public MapSplitter withKeyValueSeparator(Splitter keyValueSeparator) {
        return new MapSplitter(this, keyValueSeparator);
    }


    private interface Strategy {
        Iterator<String> iterator(Splitter splitter, CharSequence toSplit);
    }

    public static final class MapSplitter {
        private static final String INVALID_ENTRY_MESSAGE = "Chunk [%s] is not a valid entry";
        private final Splitter outerSplitter;
        private final Splitter innerSplitter;

        private MapSplitter(Splitter outerSplitter, Splitter entrySplitter) {
            this.outerSplitter = outerSplitter;
            this.innerSplitter = checkNotNull(entrySplitter);
        }

        public Map<String, String> split(CharSequence sequence) {
            Map<String, String> map = new LinkedHashMap<>();
            for (String entry : outerSplitter.split(sequence)) {
                Iterator<String> entryFields = innerSplitter.splittingIterator(entry);

                checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
                String key = entryFields.next();
                checkArgument(!map.containsKey(key), "Duplicate key [%s] found", key);

                checkArgument(entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
                String value = entryFields.next();
                map.put(key, value);

                checkArgument(!entryFields.hasNext(), INVALID_ENTRY_MESSAGE, entry);
            }
            return Collections.unmodifiableMap(map);
        }
    }

    private abstract static class SplittingIterator extends AbstractIterator<String> {
        final CharSequence toSplit;
        final CharMatcher trimmer;
        final boolean omitEmptyString;
        int offset = 0;
        int limit;

        protected SplittingIterator(Splitter splitter, CharSequence toSplit) {
            this.trimmer = splitter.trimmer;
            this.omitEmptyString = splitter.omitEmptyStrings;
            this.limit = splitter.limit;
            this.toSplit = toSplit;
        }

        abstract int separatorStart(int start);

        abstract int separatorEnd(int separatorPosition);

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

                return toSplit.subSequence(start, end).toString();
            }
            return endOfData();
        }
    }
}
