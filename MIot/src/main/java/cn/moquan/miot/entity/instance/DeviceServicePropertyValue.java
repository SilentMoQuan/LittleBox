package cn.moquan.miot.entity.instance;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:47 </b><br />
 */
public class DeviceServicePropertyValue {

    private int value;

    private String description;

    @Override
    public String toString() {
        return "DeviceServicePropertyValue{" +
                "value=" + value +
                ", description='" + description + '\'' +
                '}';
    }

    public int getValue() {
        return value;
    }

    public DeviceServicePropertyValue setValue(int value) {
        this.value = value;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DeviceServicePropertyValue setDescription(String description) {
        this.description = description;
        return this;
    }
}
