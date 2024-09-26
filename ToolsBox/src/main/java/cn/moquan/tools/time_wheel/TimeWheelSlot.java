package cn.moquan.tools.time_wheel;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/9/26 17:04 </b><br />
 */
public class TimeWheelSlot {

    private final Deque<TimeWheelTask> taskList = new ConcurrentLinkedDeque<>();

    public void addTask(TimeWheelTask task) {
        taskList.add(task);
    }

    public Deque<TimeWheelTask> getTaskList() {
        return taskList;
    }

}
