package cn.moquan.miot.entity.instance;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:47 </b><br />
 */
public class DeviceServiceEventInstance {

    private int iid;

    private String type;

    private String description;

    private List<Object> arguments;

    @Override
    public String toString() {
        return "DeviceServiceEventInstance{" +
                "iid=" + iid +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", arguments=" + arguments +
                '}';
    }

    public int getIid() {
        return iid;
    }

    public DeviceServiceEventInstance setIid(int iid) {
        this.iid = iid;
        return this;
    }

    public String getType() {
        return type;
    }

    public DeviceServiceEventInstance setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DeviceServiceEventInstance setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Object> getArguments() {
        return arguments;
    }

    public DeviceServiceEventInstance setArguments(List<Object> arguments) {
        this.arguments = arguments;
        return this;
    }
}
