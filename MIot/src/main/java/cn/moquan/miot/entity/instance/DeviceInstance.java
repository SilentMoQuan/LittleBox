package cn.moquan.miot.entity.instance;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:47 </b><br />
 */
public class DeviceInstance {

    private String type;

    private String description;

    private List<DeviceServiceInstance> services;

    @Override
    public String toString() {
        return "DeviceInstance{" +
                "type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", services=" + services +
                '}';
    }

    public String getType() {
        return type;
    }

    public DeviceInstance setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DeviceInstance setDescription(String description) {
        this.description = description;
        return this;
    }

    public List<DeviceServiceInstance> getServices() {
        return services;
    }

    public DeviceInstance setServices(List<DeviceServiceInstance> services) {
        this.services = services;
        return this;
    }
}
