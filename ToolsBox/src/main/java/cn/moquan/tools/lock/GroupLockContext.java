package cn.moquan.tools.lock;

import java.util.Objects;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.locks.ReentrantLock;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 */
public class GroupLockContext {

    private ReentrantLock lock;

    private ScheduledFuture<?> delayRemoveFuture;

    public GroupLockContext() {
    }

    public GroupLockContext(ReentrantLock lock) {
        this.lock = lock;
    }

    public static GroupLockContext init() {
        return new GroupLockContext(new ReentrantLock());
    }

    /**
     * synchronized 的目的为:
     * 防止方法初始化时, 同时出现两个线程同时执行 Objects.nonNull(delayRemoveFuture) 方法得到结果为 false
     * 从而造成某个 future 未能被取消而导致的延时失败
     *
     * @param future 延时删除的对象
     */
    public synchronized void setDelayRemoveFuture(ScheduledFuture<?> future){

        // 检查任务是否已经完成
        if (Objects.nonNull(delayRemoveFuture) && !delayRemoveFuture.isDone()) {
            // 未完成则取消
            delayRemoveFuture.cancel(true);
        }

        // 替换为新的延时删除
        delayRemoveFuture = future;
    }

    public void lock(){
        lock.lock();
    }

    public void unlock(){
        lock.unlock();
    }

    public ReentrantLock getLock() {
        return lock;
    }

}
