package com.robinzhou.common.base;

import com.google.common.testing.GcFinalization;
import org.junit.Test;

import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by robinzhou on 2016/2/15.
 */
public class AbstractIteratorTest {

    @Test
    public void testDefaultBehaviorOfNextAndHasNext() {
        Iterator<Integer> iter = new AbstractIterator<Integer>() {
            private int rep;

            @Override
            protected Integer computeNext() {
                switch (rep++) {
                    case 0:
                        return 0;
                    case 1:
                        return 1;
                    case 2:
                        return endOfData();
                    default:
                        fail("Should not have been invoked again");
                        return null;
                }
            }
        };

        assertTrue(iter.hasNext());
        assertEquals(0, (int) iter.next());

        assertTrue(iter.hasNext());
        assertTrue(iter.hasNext());
        assertTrue(iter.hasNext());
        assertEquals(1, (int) iter.next());

        assertFalse(iter.hasNext());

        try {
            iter.next();
            fail("no exception thrown");
        } catch (NoSuchElementException e) {
        }
    }

    @Test
    public void testSneakyThrow() throws Exception {
        Iterator<Integer> iter = new AbstractIterator<Integer>() {
            private boolean haveBeenCalled;

            @Override
            public Integer computeNext() {
                if (haveBeenCalled) {
                    fail("Should not have been called again");
                } else {
                    haveBeenCalled = true;
                    sneakyThrow(new SomeCheckedException());
                }
                return null;
            }
        };

        try {
            iter.hasNext();
            fail("No exception thrown");
        } catch (Exception e) {
            if (!(e instanceof SomeCheckedException)) {
                throw e;
            }
        }

        try {
            iter.hasNext();
            fail("No exception thrown");
        } catch (IllegalStateException expected) {
        }

    }

    @Test
    public void testException() {
        final SomeUnCheckedException exception = new SomeUnCheckedException();
        Iterator<Integer> iter = new AbstractIterator<Integer>() {
            @Override
            protected Integer computeNext() {
                throw exception;
            }
        };

        try {
            iter.hasNext();
            fail("No exception throw");
        } catch (SomeUnCheckedException expected) {
            assertSame(expected, exception);
        }
    }

    @Test
    public void testExceptionAfterEndOfData() {
        Iterator<Integer> iter = new AbstractIterator<Integer>() {
            @Override
            protected Integer computeNext() {
                endOfData();
                throw new SomeUnCheckedException();
            }
        };

        try {
            iter.hasNext();
            fail("No exception throw");
        } catch (SomeUnCheckedException e) {
        }
    }

    @Test
    public void testCantRemove() {
        Iterator<Integer> iter = new AbstractIterator<Integer>() {
            private boolean haveBeenCalled;

            @Override
            protected Integer computeNext() {
                if (haveBeenCalled) {
                    endOfData();
                }
                haveBeenCalled = true;
                return 0;
            }
        };

        assertEquals(0, (int) iter.next());

        try {
            iter.remove();
            fail("No exception throw");
        } catch (UnsupportedOperationException e) {
        }
    }

    @Test
    public void testFreesNextReference() {
        Iterator<Object> iter = new AbstractIterator<Object>() {
            @Override
            protected Object computeNext() {
                return new Object();
            }
        };
        WeakReference<Object> ref = new WeakReference<Object>(iter.next());
        GcFinalization.awaitClear(ref);
    }

    @Test
    public void testReentrantHasNext() {
        Iterator<Integer> iter = new AbstractIterator<Integer>() {
            @Override
            protected Integer computeNext() {
                hasNext();
                return null;
            }
        };

        try{
            iter.hasNext();
            fail("No Exception throw");
        }catch (IllegalStateException expected){
        }
    }


    private static void sneakyThrow(Throwable t) {
        class SneakyThrower<T extends Throwable> {
            void throwIt(Throwable t) throws T {
                throw (T) t;
            }
        }
        new SneakyThrower<Error>().throwIt(t);
    }

    private static class SomeCheckedException extends Exception {
    }

    private static class SomeUnCheckedException extends RuntimeException {
    }
}
