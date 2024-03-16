package cn.moquan.tools.lock;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
 * todo 添加测试内容进行测试
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/16 16:16 </b><br />
 */
public class GroupLock<T> {

    private static final AtomicLong THREAD_COUNT = new AtomicLong(0);

    private static final Integer CORE_THREAD_COUNT = 1000;

    private static final ScheduledThreadPoolExecutor DEFAULT_SCHEDULED_THREAD_POOL = new ScheduledThreadPoolExecutor(
            CORE_THREAD_COUNT,
            runnable -> {
                Thread thread = new Thread(runnable);
                thread.setName("GroupLock-DefaultScheduledThreadPool-Thread-" + THREAD_COUNT.incrementAndGet());
                return thread;
            }
    );

    private final Map<T, ReentrantLock> map = new ConcurrentHashMap<>();

    public void lock(T key) throws Exception {
        keyCheck(key);
        ReentrantLock lock = map.computeIfAbsent(key, keyItem -> new ReentrantLock());
        lock.lock();
    }

    public void unlock(T key) {
        keyCheck(key);
        ReentrantLock lock = map.getOrDefault(key, null);
        if (Objects.nonNull(lock)) {
            lock.unlock();
        }
    }

    public void remove(T key) {
        keyCheck(key);
        map.remove(key);
    }

    public void delayRemove(T key, long delayMilliSeconds, ScheduledThreadPoolExecutor threadPool) {
        delayRemove(key, delayMilliSeconds, TimeUnit.SECONDS, threadPool);
    }


    public void delayRemove(T key, long delayMilliSeconds) {
        delayRemove(key, delayMilliSeconds, TimeUnit.SECONDS, DEFAULT_SCHEDULED_THREAD_POOL);
    }

    public void delayRemove(T key, long time, TimeUnit unit) {
        delayRemove(key, time, unit, DEFAULT_SCHEDULED_THREAD_POOL);
    }

    public void delayRemove(T key, long time, TimeUnit unit, ScheduledThreadPoolExecutor threadPool) {
        keyCheck(key);
        threadPool.schedule(() -> remove(key), time, unit);
    }

    public void unlockAndRemove(T key) {
        keyCheck(key);
        unlock(key);
        remove(key);
    }

    public ReentrantLock getLock(T key) {
        keyCheck(key);
        return map.getOrDefault(key, null);
    }

    public void keyCheck(T key) {
        Objects.requireNonNull(key, "分组锁: key 参数不可为 null, 请检查");
    }

}
