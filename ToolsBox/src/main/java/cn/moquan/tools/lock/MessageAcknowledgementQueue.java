package cn.moquan.tools.lock;

import java.util.Objects;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/8/6 8:59 </b><br />
 */
public abstract class MessageAcknowledgementQueue<T> {

    // 运行标识
    private final AtomicBoolean running = new AtomicBoolean(false);

    // 消息队列
    private final Queue<MaxOfCycles<T>> queue = new ConcurrentLinkedDeque<>();

    // 当前工作线程
    private Thread worker = null;

    // 当前等待处理(确认)的元素
    private MaxOfCycles<T> currentElement;

    // 是否需要进行循环处理
    private boolean needCirculate = false;

    /**
     * 添加一个元素到队列, 默认最大循环次数为 Integer.MAX_VALUE
     *
     * @param obj 元素对象
     */
    public void add(T obj) {
        this.add(obj, Integer.MAX_VALUE);
    }

    /**
     * 添加一个元素到队列, 并设置最大循环次数
     *
     * @param obj 对象
     * @param maxOfCycles 最大循环次数
     */
    public void add(T obj, int maxOfCycles) {
        Objects.requireNonNull(obj, "向队列中添加元素, 元素为 null.");
        queue.add(MaxOfCycles.of(maxOfCycles, obj));
        run();
    }

    /**
     * 按顺序循环处理数据
     */
    private void run() {
        if (running.get() || !running.compareAndSet(false, true)) {
            // 如果正在运行 => 退出
            // 如果交换设置(加锁)失败 => 退出
            return;
        }

        worker = new Thread(this::run0);
        worker.start();
    }

    /**
     * 具体执行流程
     */
    private void run0() {
        while (!queue.isEmpty()) {


            MaxOfCycles<T> peek = queue.peek();
            currentElement = peek;

            boolean isEnd = process(peek.data);

            if ((isEnd && !needCirculate) || !peek.canNextCycles()) {
                queue.poll();
            }

            needCirculate = false;
        }

        clear();
    }

    /**
     * 所有元素处理完成后清除数据
     */
    private void clear() {
        currentElement = null;
        needCirculate = false;
        running.set(false);
        worker = null;
    }

    /**
     * 元素具体处理内容
     *
     * @param peek 元素对象
     * @return boolean 数据处理是否完成
     */
    protected abstract boolean process(T peek);

    /**
     * 设置当前元素是否需要循环处理, 每次循环结束前该值会被设置为 false
     *
     * @param needCirculate 是否需要循环
     */
    protected void needCirculate(boolean needCirculate) {
        this.needCirculate = needCirculate;
    }

    protected Optional<Thread> getWorker() {
        return Optional.ofNullable(worker);
    }

    protected T getCurrentElement() {
        return currentElement.data;
    }

    protected int getCurrentElementCyclesCountRemaining() {
        return currentElement.count;
    }

    private static class MaxOfCycles<T> {
        private int count;
        public final T data;

        private MaxOfCycles(int count, T data) {
            this.count = count;
            this.data = data;
        }

        public static <E> MaxOfCycles<E> of(int count, E data) {
            return new MaxOfCycles<>(count, data);
        }

        private boolean canNextCycles() {
            this.count -= 1;
            return count > 0;
        }
    }
}
