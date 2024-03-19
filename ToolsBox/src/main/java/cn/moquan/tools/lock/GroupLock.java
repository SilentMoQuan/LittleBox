package cn.moquan.tools.lock;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 分组锁
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 */
public class GroupLock<T> {

    private static final AtomicLong THREAD_COUNT = new AtomicLong(0);

    private static final Integer CORE_THREAD_COUNT = 1000;

    private static final ScheduledExecutorService DEFAULT_SCHEDULED_THREAD_POOL = new ScheduledThreadPoolExecutor(
            CORE_THREAD_COUNT,
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("GroupLock-DefaultScheduledThreadPool-Thread-" + THREAD_COUNT.incrementAndGet());
                return thread;
            }
    );

    private final Map<T, GroupLockContext> map = new ConcurrentHashMap<>();

    public void lock(T key) throws Exception {
        keyCheck(key);
        GroupLockContext groupLockContext = map.computeIfAbsent(key, keyItem -> GroupLockContext.init());
        groupLockContext.lock();
    }

    public void unlock(T key) {
        keyCheck(key);
        GroupLockContext groupLockContext = map.getOrDefault(key, null);
        if (Objects.nonNull(groupLockContext)) {
            groupLockContext.unlock();
        }
    }

    public void remove(T key) {
        keyCheck(key);
        map.remove(key);
    }

    public void delayRemove(T key, long delayMilliSeconds, ScheduledThreadPoolExecutor threadPool) {
        delayRemove(key, delayMilliSeconds, TimeUnit.MILLISECONDS, threadPool);
    }


    public void delayRemove(T key, long delayMilliSeconds) {
        delayRemove(key, delayMilliSeconds, TimeUnit.MILLISECONDS, DEFAULT_SCHEDULED_THREAD_POOL);
    }

    public void delayRemove(T key, long time, TimeUnit unit) {
        delayRemove(key, time, unit, DEFAULT_SCHEDULED_THREAD_POOL);
    }

    public void delayRemove(T key, long time, TimeUnit unit, ScheduledExecutorService threadPool) {
        keyCheck(key);
        ScheduledFuture<?> future = threadPool.schedule(() -> remove(key), time, unit);
        GroupLockContext groupLockContext = map.getOrDefault(key, null);
        if (Objects.isNull(groupLockContext)) {
            // 数据已经被删除, 取消任务
            future.cancel(true);
        } else {
            // 数据存在, 替换旧延时删除
            groupLockContext.setDelayRemoveFuture(future);
        }
    }

    public void unlockAndRemove(T key) {
        keyCheck(key);
        unlock(key);
        remove(key);
    }

    public ReentrantLock getLock(T key) {
        keyCheck(key);
        GroupLockContext groupLockContext = map.getOrDefault(key, null);
        return Objects.isNull(groupLockContext) ? null : groupLockContext.getLock();
    }

    public void keyCheck(T key) {
        Objects.requireNonNull(key, "分组锁: key 参数不可为 null, 请检查");
    }

}
