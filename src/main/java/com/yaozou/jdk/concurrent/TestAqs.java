package com.yaozou.jdk.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Description: AQS(Abstract Queued Synchronizer)即抽象队列式的同步器。AQS定义了一套多线程访问共享资源的同步器矿建。
 * 许多同步类实现都依赖于它，如常用的ReentrantLock/Semaphore/CountDownLatch。
 * AQS中的state只有两种状态：0--表示未锁定，1---表示锁定。
 *
 * @Author yao.zou
 * @Date 2019/9/19 0019
 * @Version V1.0
 **/
public class TestAqs {

}

/**
 * 独占模式 like a ReentrantLock
 */
class Mutex implements Lock, Serializable {

    private static class Sync extends AbstractQueuedSynchronizer{
        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            // state 0--表示未锁定，1---表示锁定
            assert acquires == 1;
            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            assert  releases == 1;
            if (getState() == 0){throw new IllegalMonitorStateException();}
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        Condition newCondition() { return new ConditionObject(); }

        private void readObject(ObjectInputStream s) throws IOException,ClassNotFoundException {
            s.defaultReadObject();
            setState(0);
        }
    }

    private final  Sync sync = new Sync();

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
    public boolean tryLock(long timeout, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1,unit.toNanos(timeout));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }

    public boolean hasQueuedThreads(){
        return sync.hasQueuedThreads();
    }
}

/**
 * 共享模式 like a CountDownLatch
 */
class BooleanLatch{
    private static class Sync extends AbstractQueuedSynchronizer{
        Sync(int count) {
            setState(count);
        }
        boolean isSignalled(){return getState() != 0;}

        @Override
        protected int tryAcquireShared(int ignore){
            return isSignalled()?1:-1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {
            for (;;) {
                int c = getState();
                if (c == 0) {return false;}
                int nextc = c-1;
                if (compareAndSetState(c, nextc)) { return nextc == 0;}
            }
        }
    }
    private final Sync sync;
    public BooleanLatch(int count){
        sync = new Sync(count);
    }

    public boolean isSignalled() { return sync.isSignalled(); }
    public void signal()         { sync.releaseShared(1); }
    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }
}
