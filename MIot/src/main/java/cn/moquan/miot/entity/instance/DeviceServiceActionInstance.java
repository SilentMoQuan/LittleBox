package cn.moquan.miot.entity.instance;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:47 </b><br />
 */
public class DeviceServiceActionInstance {

    private int iid;

    private String type;

    private String description;

    private List<Object> in;

    private List<Object> out;

    @Override
    public String toString() {
        return "DeviceServiceActionInstance{" +
                "iid=" + iid +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", in=" + in +
                ", out=" + out +
                '}';
    }

    public int getIid() {
        return iid;
    }

    public DeviceServiceActionInstance setIid(int iid) {
        this.iid = iid;
        return this;
    }

    public String getType() {
        return type;
    }

    public DeviceServiceActionInstance setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DeviceServiceActionInstance setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<Object> getIn() {
        return in;
    }

    public DeviceServiceActionInstance setIn(List<Object> in) {
        this.in = in;
        return this;
    }

    public List<Object> getOut() {
        return out;
    }

    public DeviceServiceActionInstance setOut(List<Object> out) {
        this.out = out;
        return this;
    }
}
