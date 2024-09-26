package cn.moquan.tools.time_wheel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/9/26 16:15 </b><br />
 */
public class TimeWheelTask {

    private static final ZoneId SYSTEM_ZONE = ZoneId.systemDefault();

    private final Runnable task;

    private final LocalDateTime addTime;

    private final long delay;

    private final TimeUnit unit;

    private final long day;

    private final long hour;

    private final long minute;

    private final long second;

    public TimeWheelTask(Runnable task, long delay, TimeUnit unit) {
        this.addTime = LocalDateTime.now();
        this.task = task;

        this.delay = delay;
        this.unit = unit;

        this.day = unit.toDays(delay);
        this.hour = unit.toHours(delay) % 24;
        this.minute = unit.toMinutes(delay) % 60;
        this.second = unit.toSeconds(delay) % 60;
    }

    public TimeWheelTask(Runnable task, LocalDateTime executionTime) {
        this.addTime = LocalDateTime.now();
        this.task = task;

        this.delay = getDelaySeconds(addTime, executionTime);
        this.unit = TimeUnit.SECONDS;

        this.day = unit.toDays(delay);
        this.hour = unit.toHours(delay) % 24;
        this.minute = unit.toMinutes(delay) % 60;
        this.second = unit.toSeconds(delay) % 60;
    }

    private static long getDelaySeconds(LocalDateTime addTime, LocalDateTime executionTime) {

        long executionEpochSecond = executionTime.atZone(SYSTEM_ZONE).toInstant().getEpochSecond();
        long addEpochSecond = addTime.atZone(SYSTEM_ZONE).toInstant().getEpochSecond();

        return executionEpochSecond - addEpochSecond;
    }

    public Runnable getTask() {
        return task;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public long getDelay() {
        return delay;
    }

    public TimeUnit getUnit() {
        return unit;
    }

    public long getDay() {
        return day;
    }

    public long getHour() {
        return hour;
    }

    public long getMinute() {
        return minute;
    }

    public long getSecond() {
        return second;
    }

    @Override
    public String toString() {
        return "TimeWheelTask{" +
                "task=" + task +
                ", addTime=" + addTime +
                ", delay=" + delay +
                ", unit=" + unit +
                ", day=" + day +
                ", hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}
