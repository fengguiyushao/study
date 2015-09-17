package com.robinzhou.common.base;

import com.google.common.base.MoreObjects;

import javax.annotation.Nullable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by robinzhou on 2015/9/15.
 */
public class Predicates {

    private static final Joiner COMMA_JOINER = Joiner.on(",");

    public static <T> Predicate<T> alwaysTrue() {
        return ObjectPredicate.ALWAYS_TRUE.withNarrowedType();
    }

    public static <T> Predicate<T> alwaysFalse() {
        return ObjectPredicate.ALWAYS_FALSE.withNarrowedType();
    }

    public static <T> Predicate<T> isNull() {
        return ObjectPredicate.IS_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> notNull() {
        return ObjectPredicate.NOT_NULL.withNarrowedType();
    }

    public static <T> Predicate<T> not(Predicate<T> predicate) {
        return new NotPredicate<>(predicate);
    }

    public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components) {
        return new AndPredicate<T>(defensiveCopy(components));
    }

    public static <T> Predicate<T> and(Predicate<? super T>... components) {
        return new AndPredicate<>(defensiveCopy(components));
    }

    public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second) {
        return new AndPredicate<>(asList(checkNotNull(first), checkNotNull(second)));
    }

    public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components) {
        return new OrPredicate<T>(defensiveCopy(components));
    }

    public static <T> Predicate<T> or(Predicate<? super T>... components) {
        return new OrPredicate<>(defensiveCopy(components));
    }

    public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second) {
        return new OrPredicate<>(asList(checkNotNull(first), checkNotNull(second)));
    }

    public static <T> Predicate<T> equalTo(@Nullable T target) {
        return (target == null) ? Predicates.isNull() : new IsEqualToPredicate<>(target);
    }

    public static Predicate<Object> instanceOf(Class<?> clazz) {
        return new InstanceOfPredicate(clazz);
    }

    public static Predicate<Class<?>> assignableFrom(Class<?> clazz) {
        return new AssignableFromPredicate(clazz);
    }

    public static <T> Predicate<T> in(Collection<? extends T> target) {
        return new InPredicate<>(target);
    }

    public static <A, B> Predicate<A> compose(Predicate<B> predicate, Function<A, ? extends B> function) {
        return new CompositionPredicate<>(predicate, function);
    }

    public static Predicate<CharSequence> contains(Pattern pattern) {
        return new ContainsPatternPredicate(pattern);
    }

    public static Predicate<CharSequence> containsPattern(String pattern) {
        return new ContainsPatternFromStringPredicate(pattern);
    }

    private static <T> List<Predicate<? super T>> asList(Predicate<? super T> first, Predicate<? super T> second) {
        return Arrays.<Predicate<? super T>>asList(first, second);
    }

    private static <T> List<T> defensiveCopy(T... array) {
        return Arrays.asList(array);
    }

    static <T> List<T> defensiveCopy(Iterable<T> iterable) {
        List<T> list = new ArrayList<>();
        for (T element : iterable) {
            list.add(checkNotNull(element));
        }
        return list;
    }

    enum ObjectPredicate implements Predicate<Object> {
        ALWAYS_TRUE {
            @Override
            public boolean apply(@Nullable Object input) {
                return true;
            }

            @Override
            public String toString() {
                return "Predicates.alwaysTrue()";
            }
        },
        ALWAYS_FALSE {
            @Override
            public boolean apply(@Nullable Object input) {
                return false;
            }

            @Override
            public String toString() {
                return "Predicates.alwaysFalse()";
            }
        },
        IS_NULL {
            @Override
            public boolean apply(@Nullable Object input) {
                return input == null;
            }

            @Override
            public String toString() {
                return "Predicates.isNull()";
            }
        },
        NOT_NULL {
            @Override
            public boolean apply(@Nullable Object input) {
                return input != null;
            }

            public String toString() {
                return "Predicates.notNull()";
            }
        };

        <T> Predicate<T> withNarrowedType() {
            return (Predicate<T>) this;
        }
    }

    private static class NotPredicate<T> implements Predicate<T>, Serializable {

        private static final long serialVersionUID = 0l;

        final Predicate<T> predicate;

        NotPredicate(Predicate<T> predicate) {
            this.predicate = checkNotNull(predicate);
        }

        @Override
        public boolean apply(@Nullable T input) {
            return !predicate.apply(input);
        }

        @Override
        public int hashCode() {
            return ~predicate.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof NotPredicate) {
                NotPredicate<?> other = (NotPredicate) obj;
                return predicate.equals(other.predicate);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.not(" + predicate + ")";
        }
    }

    private static class AndPredicate<T> implements Predicate<T>, Serializable {

        private static final long serialVersionUID = 0;
        private final List<? extends Predicate<? super T>> components;

        private AndPredicate(List<? extends Predicate<? super T>> components) {
            this.components = components;
        }

        @Override
        public boolean apply(@Nullable T input) {
            for (Predicate<? super T> component : components) {
                if (!component.apply(input)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            return components.hashCode() + 0x12472c2c;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof AndPredicate) {
                AndPredicate<?> other = (AndPredicate<?>) obj;
                return components.equals(other.components);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.and(" + COMMA_JOINER.join(components) + ")";
        }
    }

    private static class OrPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;

        private final List<? extends Predicate<? super T>> components;

        private OrPredicate(List<? extends Predicate<? super T>> components) {
            this.components = components;
        }


        @Override
        public boolean apply(@Nullable T input) {
            for (Predicate<? super T> predicate : components) {
                if (predicate.apply(input)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int hashCode() {
            return components.hashCode() + 0x053c91cf;
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof OrPredicate) {
                OrPredicate<?> other = (OrPredicate) obj;
                return components.equals(other.components);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.or(" + COMMA_JOINER.join(components) + ")";
        }
    }

    private static class IsEqualToPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;

        private final T target;

        private IsEqualToPredicate(T target) {
            //TODO check not null
            this.target = target;
        }

        @Override
        public boolean apply(@Nullable T input) {
            return target.equals(input);
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof IsEqualToPredicate) {
                IsEqualToPredicate<?> other = (IsEqualToPredicate) obj;
                return target.equals(other.target);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.equalTo(" + target + ")";
        }
    }

    private static class InstanceOfPredicate implements Predicate<Object>, Serializable {
        private static final long serialVersionUID = 0;

        private final Class<?> clazz;

        private InstanceOfPredicate(Class<?> clazz) {
            this.clazz = checkNotNull(clazz);
        }

        @Override
        public boolean apply(@Nullable Object input) {
            return clazz.isInstance(input);
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof InstanceOfPredicate) {
                InstanceOfPredicate other = (InstanceOfPredicate) obj;
                return clazz == other.clazz;
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.instanceOf(" + clazz.getName() + ")";
        }
    }

    private static class AssignableFromPredicate implements Predicate<Class<?>>, Serializable {

        private static final long serialVersionUID = 0;

        private final Class<?> clazz;

        private AssignableFromPredicate(Class<?> clazz) {
            this.clazz = checkNotNull(clazz);
        }

        @Override
        public boolean apply(Class<?> input) {
            return clazz.isAssignableFrom(input);
        }

        @Override
        public int hashCode() {
            return clazz.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof AssignableFromPredicate) {
                AssignableFromPredicate other = (AssignableFromPredicate) obj;
                return clazz.equals(other.clazz);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.assignableFrom(" + clazz.getName() + ")";
        }
    }

    private static class InPredicate<T> implements Predicate<T>, Serializable {
        private static final long serialVersionUID = 0;

        private final Collection<?> target;

        private InPredicate(Collection<?> target) {
            this.target = checkNotNull(target);
        }

        @Override
        public boolean apply(@Nullable T input) {
            try {
                return target.contains(input);
            } catch (NullPointerException e) {
                return false;
            } catch (ClassCastException e) {
                return false;
            }
        }

        @Override
        public int hashCode() {
            return target.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof InPredicate) {
                InPredicate other = (InPredicate) obj;
                return target.equals(other.target);
            }
            return false;
        }

        @Override
        public String toString() {
            return "Predicates.in(" + target + ")";
        }
    }

    private static class CompositionPredicate<A, B> implements Predicate<A>, Serializable {

        private static final long serialVersionUID = 0;

        final Predicate<B> p;
        final Function<A, ? extends B> f;

        private CompositionPredicate(Predicate<B> p, Function<A, ? extends B> f) {
            this.p = checkNotNull(p);
            this.f = checkNotNull(f);
        }

        @Override
        public boolean apply(@Nullable A input) {
            return p.apply(f.apply(input));
        }

        @Override
        public int hashCode() {
            return p.hashCode() ^ f.hashCode();
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof CompositionPredicate) {
                CompositionPredicate<?, ?> other = (CompositionPredicate<?, ?>) obj;
                return p.equals(other.p) && f.equals(other.f);
            }
            return false;
        }

        @Override
        public String toString() {
            return p + "(" + f + ")";
        }
    }

    private static class ContainsPatternPredicate implements Predicate<CharSequence>, Serializable {

        private static final long serialVersionUID = 0;

        final Pattern pattern;

        private ContainsPatternPredicate(Pattern pattern) {
            this.pattern = checkNotNull(pattern);
        }

        @Override
        public boolean apply(@Nullable CharSequence input) {
            return pattern.matcher(input).find();
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(pattern.pattern(), pattern.flags());
        }

        @Override
        public boolean equals(@Nullable Object obj) {
            if (obj instanceof ContainsPatternPredicate) {
                ContainsPatternPredicate other = (ContainsPatternPredicate) obj;
                return Objects.equal(pattern.pattern(), other.pattern.pattern())
                        && Objects.equal(pattern.flags(), other.pattern.flags());
            }
            return false;
        }

        @Override
        public String toString() {
            String patternString = MoreObjects.toStringHelper(pattern)
                    .add("pattern", pattern.pattern())
                    .add("pattern.flags", pattern.flags())
                    .toString();
            return "Predicates.contains(" + patternString + ")";
        }
    }

    private static class ContainsPatternFromStringPredicate extends ContainsPatternPredicate {

        private static final long serialVersionUID = 0;

        private ContainsPatternFromStringPredicate(String string) {
            super(Pattern.compile(string));
        }

        @Override
        public String toString() {
            return "Predicates.containsPattern(" + pattern.pattern() + ")";
        }
    }


}
