package cn.moquan.miot.entity.instance;

import java.util.List;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 9:47 </b><br />
 */
public class DeviceServicePropertyInstance {

    private int iid;

    private String type;

    private String description;

    private String format;

    private List<String> access;

    private DeviceServicePropertyValueRange valueRange;

    private DeviceServicePropertyValue valueList;

    private String unit;

    @Override
    public String toString() {
        return "DeviceServicePropertyInstance{" +
                "iid=" + iid +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", format='" + format + '\'' +
                ", access=" + access +
                ", valueRange=" + valueRange +
                ", valueList=" + valueList +
                ", unit='" + unit + '\'' +
                '}';
    }

    public int getIid() {
        return iid;
    }

    public DeviceServicePropertyInstance setIid(int iid) {
        this.iid = iid;
        return this;
    }

    public String getType() {
        return type;
    }

    public DeviceServicePropertyInstance setType(String type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public DeviceServicePropertyInstance setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getFormat() {
        return format;
    }

    public DeviceServicePropertyInstance setFormat(String format) {
        this.format = format;
        return this;
    }

    public List<String> getAccess() {
        return access;
    }

    public DeviceServicePropertyInstance setAccess(List<String> access) {
        this.access = access;
        return this;
    }

    public DeviceServicePropertyValueRange getValueRange() {
        return valueRange;
    }

    public DeviceServicePropertyInstance setValueRange(DeviceServicePropertyValueRange valueRange) {
        this.valueRange = valueRange;
        return this;
    }

    public DeviceServicePropertyValue getValueList() {
        return valueList;
    }

    public DeviceServicePropertyInstance setValueList(DeviceServicePropertyValue valueList) {
        this.valueList = valueList;
        return this;
    }

    public String getUnit() {
        return unit;
    }

    public DeviceServicePropertyInstance setUnit(String unit) {
        this.unit = unit;
        return this;
    }
}
