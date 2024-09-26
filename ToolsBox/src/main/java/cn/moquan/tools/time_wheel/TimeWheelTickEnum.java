package cn.moquan.tools.time_wheel;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/9/26 17:10 </b><br />
 */
public enum TimeWheelTickEnum {

    SECOND(60),

    MINUTE(60),

    HOUR(24),

    DAY(365),

    ;

    private final int slotCount;

    TimeWheelTickEnum(int slotCount) {
        this.slotCount = slotCount;
    }

    public int getSlotCount() {
        return slotCount;
    }

    public TimeWheel initTimeWheel() {
        return new TimeWheel(this);
    }

}
