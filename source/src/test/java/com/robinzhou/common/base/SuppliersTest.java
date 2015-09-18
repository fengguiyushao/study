package com.robinzhou.common.base;

import com.google.common.collect.Lists;
import com.google.common.testing.ClassSanityTester;
import com.google.common.testing.EqualsTester;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static com.google.common.testing.SerializableTester.reserialize;
import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2015/9/18.
 */
public class SuppliersTest {

    @Test
    public void testCompose() {
        Supplier<Integer> fiveSupplier = () -> 5;
        Function<Number, Integer> intValueFunction = (x) -> x.intValue();

        Supplier<Integer> squareSupplier = Suppliers.compose(intValueFunction, fiveSupplier);

        assertEquals(Integer.valueOf(5), squareSupplier.get());
    }

    @Test
    public void testComposeWithLists() {
        Supplier<ArrayList<Integer>> listSupplier = () -> Lists.newArrayList(0);
        Function<ArrayList<Integer>, ArrayList<Integer>> addElementFunction =
                (list) -> {
                    ArrayList<Integer> result = Lists.newArrayList(list);
                    result.add(1);
                    return result;
                };

        Supplier<ArrayList<Integer>> addSupplier = Suppliers.compose(addElementFunction, listSupplier);
        List<Integer> result = addSupplier.get();
        assertEquals(Integer.valueOf(0), result.get(0));
        assertEquals(Integer.valueOf(1), result.get(1));
    }

    @Test
    public void testMemoize() {
        CountingSupplier countingSupplier = new CountingSupplier();
        Supplier<Integer> memoizeSupplier = Suppliers.memoizeWithExpiration(countingSupplier);
        checkMemoize(countingSupplier, memoizeSupplier);
    }

    @Test
    public void testMemoize_redudantly() {
        CountingSupplier countingSupplier = new CountingSupplier();
        Supplier<Integer> memoizedSupplier = Suppliers.memoizeWithExpiration(countingSupplier);
        assertSame(memoizedSupplier, Suppliers.memoizeWithExpiration(memoizedSupplier));
    }

    @Test
    public void testMemoizeSerialized() {
        CountingSupplier countingSupplier = new CountingSupplier();
        Supplier<Integer> memoizeSupplier = Suppliers.memoizeWithExpiration(countingSupplier);
        checkMemoize(countingSupplier, memoizeSupplier);

        memoizeSupplier.get();
        Supplier<Integer> copy = reserialize(memoizeSupplier);
        memoizeSupplier.get();

        CountingSupplier countingCopy = (CountingSupplier) ((Suppliers.MemoizingSupplier<Integer>) copy).delegate;
        checkMemoize(countingCopy, copy);
    }

    @Test
    public void testMemoizeExceptionThrown() {
        Supplier<Integer> exceptingSupplier = () -> {
            throw new NullPointerException();
        };

        Supplier<Integer> memoizedSupplier = Suppliers.memoizeWithExpiration(exceptingSupplier);

        for (int i = 0; i < 2; i++) {
            try {
                memoizedSupplier.get();
                fail();
            } catch (NullPointerException expected) {
            }

        }
    }

    @Test
    public void testMemorizeWithExpiration() throws InterruptedException {
        CountingSupplier countingSupplier = new CountingSupplier();
        Supplier<Integer> memoizedSupplier = Suppliers.memoizeWithExpiration(countingSupplier, 75, TimeUnit.MILLISECONDS);
        checkExpiration(countingSupplier, memoizedSupplier);
    }

    @Test
    public void testMemorizedWithExpirationSerialized() throws InterruptedException {
        CountingSupplier countingSupplier = new CountingSupplier();
        Supplier<Integer> memoizedSupplier = Suppliers.memoizeWithExpiration(countingSupplier, 75, TimeUnit.MILLISECONDS);
        checkExpiration(countingSupplier, memoizedSupplier);

        memoizedSupplier.get();

        Supplier<Integer> copy = reserialize(memoizedSupplier);
        memoizedSupplier.get();

        CountingSupplier countingCopy = (CountingSupplier) ((Suppliers.ExpiringMemoizingSupplier<Integer>) copy).delegate;
        checkExpiration(countingCopy, copy);
    }

    @Test
    public void testOfInstanceSuppliersSameInstance() {
        Object toBeSupplied = new Object();
        Supplier<Object> objectSupplier = Suppliers.ofInstance(toBeSupplied);
        assertSame(toBeSupplied, objectSupplier.get());
        assertSame(toBeSupplied, objectSupplier.get());
    }

    @Test
    public void testOfInstanceSuppliersNull() {
        Supplier<Object> nullSupplier = Suppliers.ofInstance(null);
        assertNull(nullSupplier.get());
    }

    @Test
    public void testMemoizedSupplierThreadSafe() throws Throwable {
        Function<Supplier<Boolean>, Supplier<Boolean>> memoizer = object -> Suppliers.memoizeWithExpiration(object);
        testSupplierThreadSafe(memoizer);
    }

    @Test
    public void testExpiringSupplierThreadSafe() throws Throwable {
        Function<Supplier<Boolean>, Supplier<Boolean>> memoizer = object -> Suppliers.memoizeWithExpiration(object, Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        testSupplierThreadSafe(memoizer);
    }

    private void testSupplierThreadSafe(Function<Supplier<Boolean>, Supplier<Boolean>> memoizer) throws Throwable {
        final AtomicInteger count = new AtomicInteger(0);
        final AtomicReference<Throwable> thrown = new AtomicReference<>(null);
        final int numThreads = 3;
        final Thread[] threads = new Thread[numThreads];
        final long timeout = TimeUnit.SECONDS.toNanos(60);

        final Supplier<Boolean> supplier = new Supplier<Boolean>() {

            boolean isWaiting(Thread thread) {
                switch (thread.getState()) {
                    case BLOCKED:
                    case WAITING:
                    case TIMED_WAITING:
                        return true;
                    default:
                        return false;
                }
            }

            int waitingThreads() {
                int waitingThreads = 0;
                for (Thread thread : threads) {
                    if (isWaiting(thread)) {
                        waitingThreads++;
                    }
                }
                return waitingThreads;
            }

            @Override
            public Boolean get() {
                long t0 = System.nanoTime();
                while (waitingThreads() < numThreads - 1) {
                    if (System.nanoTime() - t0 > timeout) {
                        thrown.set(new TimeoutException("timeout"));
                        break;
                    }
                    Thread.yield();
                }
                count.addAndGet(1);
                return true;
            }
        };
        Supplier<Boolean> memoizedSupplier = memoizer.apply(supplier);

        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    assertSame(Boolean.TRUE, memoizedSupplier.get());
                }
            };
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        if (thrown.get() != null) {
            throw thrown.get();
        }

        assertEquals(1, count.intValue());
    }

    @Test
    public void testSynchronizedSupplierThreadSafe() throws InterruptedException {
        final Supplier<Integer> noThreadSafe = new Supplier<Integer>() {
            int count = 0;

            @Override
            public Integer get() {
                int next = count + 1;
                Thread.yield();
                count = next;
                return count;
            }
        };

        final int numThreads = 10;
        final int iterations = 1000;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            threads[i] = new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < iterations; j++) {
                        Suppliers.synchronizedSupplier(noThreadSafe).get();
                    }
                }
            };
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        assertEquals(numThreads * iterations + 1, (int) noThreadSafe.get());
    }

    @Test
    public void testSupplierFunction() {
        Supplier<Integer> supplier = Suppliers.ofInstance(14);
        Function<Supplier<Integer>, Integer> supplierFunction =
                Suppliers.supplierFunction();

        assertEquals(14, (int) supplierFunction.apply(supplier));
    }

    @Test
    public void testSerialization() {
        assertEquals(
                Integer.valueOf(5), reserialize(Suppliers.ofInstance(5)).get());
        assertEquals(Integer.valueOf(5), reserialize(Suppliers.compose(
                Functions.identity(), Suppliers.ofInstance(5))).get());
        assertEquals(Integer.valueOf(5),
                reserialize(Suppliers.memoizeWithExpiration(Suppliers.ofInstance(5))).get());
        assertEquals(Integer.valueOf(5),
                reserialize(Suppliers.memoizeWithExpiration(
                        Suppliers.ofInstance(5), 30, TimeUnit.SECONDS)).get());
        assertEquals(Integer.valueOf(5), reserialize(
                Suppliers.synchronizedSupplier(Suppliers.ofInstance(5))).get());
    }

    @Test
    public void testSuppliersNullChecks() throws Exception {
        new ClassSanityTester().forAllPublicStaticMethods(Suppliers.class)
                .testNulls();
    }

    @Test
    public void testSuppliersSerializable() throws Exception {
        new ClassSanityTester().forAllPublicStaticMethods(Suppliers.class)
                .testSerializable();
    }

    @Test
    public void testOfInstance_equals() {
        new EqualsTester()
                .addEqualityGroup(
                        Suppliers.ofInstance("foo"), Suppliers.ofInstance("foo"))
                .addEqualityGroup(Suppliers.ofInstance("bar"))
                .testEquals();
    }

    @Test
    public void testCompose_equals() {
        new EqualsTester()
                .addEqualityGroup(
                        Suppliers.compose(Functions.constant(1), Suppliers.ofInstance("foo")),
                        Suppliers.compose(Functions.constant(1), Suppliers.ofInstance("foo")))
                .addEqualityGroup(
                        Suppliers.compose(Functions.constant(2), Suppliers.ofInstance("foo")))
                .addEqualityGroup(
                        Suppliers.compose(Functions.constant(1), Suppliers.ofInstance("bar")))
                .testEquals();
    }


    private void checkExpiration(CountingSupplier countingSupplier, Supplier<Integer> memoizedSupplier) throws InterruptedException {
        assertEquals(0, countingSupplier.calls);

        assertEquals(10, (int) memoizedSupplier.get());
        assertEquals(1, countingSupplier.calls);

        assertEquals(10, (int) memoizedSupplier.get());
        assertEquals(1, countingSupplier.calls);

        Thread.sleep(150);

        assertEquals(20, (int) memoizedSupplier.get());
        assertEquals(2, countingSupplier.calls);

        assertEquals(20, (int) memoizedSupplier.get());
        assertEquals(2, countingSupplier.calls);
    }


    private void checkMemoize(CountingSupplier countingSupplier, Supplier<Integer> memoizedSupplier) {
        assertEquals(0, countingSupplier.calls);

        assertEquals(10, (int) memoizedSupplier.get());

        assertEquals(1, countingSupplier.calls);

        assertEquals(10, (int) memoizedSupplier.get());

        assertEquals(1, countingSupplier.calls);

    }

    static class CountingSupplier implements Supplier<Integer>, Serializable {

        private static final long serialVersionUID = 0l;

        transient int calls = 0;

        @Override
        public Integer get() {
            calls++;
            return calls * 10;
        }
    }
}
