package com.ley.java.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Mutex implements Lock {

    private final Sync sync = new Sync();

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }


    public boolean isLocked() {
        return sync.isHeldExclusively();
    }


    public boolean hasQueuedThreads() {
        return sync.hasQueuedThreads();
    }


    static final class Sync extends AbstractQueuedSynchronizer {


        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }


        @Override
        public boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }


        @Override
        public boolean tryRelease(int arg) {

            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }


        Condition newCondition() {
            return new ConditionObject();
        }
    }


    public static void main(String[] args) {
        Mutex mutex = new Mutex();

        mutex.lock();

        try {
            System.out.println(Thread.currentThread().getName());
        } finally {
            mutex.unlock();
        }
    }
}
