package cn.moquan.miot.entity.instance;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:47 </b><br />
 */
public class DeviceServiceInstance {

    private int iid;

    private String type;

    private String description;

    private List<DeviceServicePropertyInstance> properties;

    private List<DeviceServiceActionInstance> actions;

    private List<DeviceServiceEventInstance> events;

    @Override
    public String toString() {
        return "DeviceServiceInstance{" +
                "iid=" + iid +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", properties=" + properties +
                ", actions=" + actions +
                ", events=" + events +
                '}';
    }

    public int getIid() {
        return iid;
    }

    public DeviceServiceInstance setIid(int iid) {
        this.iid = iid;
        return this;
    }

    public String getType() {
        return type;
    }

    public DeviceServiceInstance setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DeviceServiceInstance setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<DeviceServicePropertyInstance> getProperties() {
        return properties;
    }

    public DeviceServiceInstance setProperties(List<DeviceServicePropertyInstance> properties) {
        this.properties = properties;
        return this;
    }

    public List<DeviceServiceActionInstance> getActions() {
        return actions;
    }

    public DeviceServiceInstance setActions(List<DeviceServiceActionInstance> actions) {
        this.actions = actions;
        return this;
    }

    public List<DeviceServiceEventInstance> getEvents() {
        return events;
    }

    public DeviceServiceInstance setEvents(List<DeviceServiceEventInstance> events) {
        this.events = events;
        return this;
    }
}
