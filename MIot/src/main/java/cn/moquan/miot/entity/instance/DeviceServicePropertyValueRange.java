package cn.moquan.miot.entity.instance;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:58 </b><br />
 */
public class DeviceServicePropertyValueRange {

    private int min;

    private int max;

    private int step;

    @Override
    public String toString() {
        return "DeviceServicePropertyValueRange{" +
                "min=" + min +
                ", max=" + max +
                ", step=" + step +
                '}';
    }

    public int getMin() {
        return min;
    }

    public DeviceServicePropertyValueRange setMin(int min) {
        this.min = min;
        return this;
    }

    public int getMax() {
        return max;
    }

    public DeviceServicePropertyValueRange setMax(int max) {
        this.max = max;
        return this;
    }

    public int getStep() {
        return step;
    }

    public DeviceServicePropertyValueRange setStep(int step) {
        this.step = step;
        return this;
    }
}
