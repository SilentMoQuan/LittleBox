package cn.moquan.tools.time_wheel;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/9/26 17:01 </b><br />
 */
public class TimeWheel {

    private final TimeWheelTickEnum tick;

    private final int slotCount;

    private final List<TimeWheelSlot> wheel;

    private int currentSlotIndex;

    private TimeWheel nextTimeWheel;

    private final BiConsumer<List<TimeWheelSlot>, TimeWheelTask> distributeTask;

    public TimeWheel(TimeWheelTickEnum tick, BiConsumer<List<TimeWheelSlot>, TimeWheelTask> distributeTask) {
        this.tick = tick;
        this.slotCount = tick.getSlotCount();
        this.wheel = new ArrayList<>(slotCount);
        this.distributeTask = distributeTask;
    }

    public synchronized Deque<TimeWheelTask> increaseAndGetTasks() {
        Deque<TimeWheelTask> taskList = wheel.get(currentSlotIndex % slotCount).getTaskList();
        currentSlotIndex++;
        return taskList;
    }

    public void getAndDistributeTasks() {
        if (null == nextTimeWheel) {
            return;
        }

        Deque<TimeWheelTask> timeWheelTasks = nextTimeWheel.increaseAndGetTasks();

        while (!timeWheelTasks.isEmpty()) {
            TimeWheelTask poll = timeWheelTasks.poll();
            distributeTask.accept(wheel, poll);
        }

    }

}
