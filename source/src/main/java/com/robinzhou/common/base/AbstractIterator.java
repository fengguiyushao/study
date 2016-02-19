package com.robinzhou.common.base;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static com.robinzhou.common.base.Preconditions.checkState;

/**
 * Created by robinzhou on 2016/2/15.
 */
public abstract class AbstractIterator<T> implements Iterator<T> {

    private State state = State.NOT_READY;

    private T next;


    protected AbstractIterator(){}


    protected abstract T computeNext();

    private enum State {
        READY,
        NOT_READY,
        DONE,
        FAILED,
    }
    @Override
    public boolean hasNext() {
        checkState(state != State.FAILED);
        switch (state) {
            case READY:
                return true;
            case DONE:
                return false;
            default:
        }
        return tryToComputeNext();
    }

    private boolean tryToComputeNext() {
        state = State.FAILED;
        next = computeNext();
        if(state != State.DONE) {
            state = State.READY;
            return true;
        }
        return false;
    }

    protected final T endOfData() {
        state = State.DONE;
        return null;
    }

    @Override
    public T next() {
        if(!hasNext()) {
            throw new NoSuchElementException();
        }
        state = State.NOT_READY;
        T result = next;
        next = null;
        return result;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
