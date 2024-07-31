package cn.moquan.miot.entity.property;

import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 11:22 </b><br />
 */
public class SetDeviceProperty extends GetDeviceProperty {

    /**
     * value
     * 值
     */
    private Object value;

    public SetDeviceProperty(String did, Integer siid, Integer piid, Object value) {
        this(did, siid, piid, value, true);
    }

    public SetDeviceProperty(String did, Integer siid, Integer piid, Object value, boolean checkValue) {
        super(did, siid, piid);
        this.value = value;

        check(checkValue);
    }

    private void check(boolean checkValue) {

        if (checkValue && Objects.isNull(value)) {
            paramException("[ value ] :: is empty.");
        }

    }

    public Object getValue() {
        return value;
    }
}
