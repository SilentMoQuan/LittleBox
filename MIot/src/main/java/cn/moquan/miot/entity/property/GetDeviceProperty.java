package cn.moquan.miot.entity.property;

import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 11:22 </b><br />
 */
public class GetDeviceProperty {

    /**
     * did
     * 设备唯一id
     */
    private String did;

    /**
     * siid
     * 服务id
     */
    private Integer siid;

    /**
     * piid
     * 属性id
     */
    private Integer piid;



    public GetDeviceProperty(String did, Integer siid, Integer piid) {
        this.did = did;
        this.siid = siid;
        this.piid = piid;

        check();
    }

    private void check() {

        if (null == did || did.isEmpty()) {
            paramException("[ did ] :: is empty.");
        }

        if (Objects.isNull(siid)) {
            paramException("[ siid ] :: is empty.");
        }

        if (Objects.isNull(piid)) {
            paramException("[ piid ] :: is empty.");
        }

    }

    protected void paramException(String message) {
        throwException("param check", message);
    }

    protected void throwException(String title, String message) {
        throw new IllegalArgumentException(this.getClass().getSimpleName() + " :: " + title + " :: " + message);
    }

    public String getDid() {
        return did;
    }

    public Integer getSiid() {
        return siid;
    }

    public Integer getPiid() {
        return piid;
    }

}
