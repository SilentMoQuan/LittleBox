package cn.moquan.miot.entity.property;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 16:10 </b><br />
 */
public class SetPropertiesResult {

    /**
     * did
     */
    private String did;

    /**
     * iid
     */
    private String iid;

    /**
     * siid
     */
    private Integer siid;

    /**
     * piid
     */
    private Integer piid;


    /**
     * code
     */
    private Integer code;

    /**
     * exeTime
     */
    private Long exe_time;

    @Override
    public String toString() {
        return "SetPropertiesResult{" +
                "did='" + did + '\'' +
                ", iid='" + iid + '\'' +
                ", siid=" + siid +
                ", piid=" + piid +
                ", code=" + code +
                ", exe_time=" + exe_time +
                '}';
    }

    public String getDid() {
        return did;
    }

    public SetPropertiesResult setDid(String did) {
        this.did = did;
        return this;
    }

    public String getIid() {
        return iid;
    }

    public SetPropertiesResult setIid(String iid) {
        this.iid = iid;
        return this;
    }

    public Integer getSiid() {
        return siid;
    }

    public SetPropertiesResult setSiid(Integer siid) {
        this.siid = siid;
        return this;
    }

    public Integer getPiid() {
        return piid;
    }

    public SetPropertiesResult setPiid(Integer piid) {
        this.piid = piid;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public SetPropertiesResult setCode(Integer code) {
        this.code = code;
        return this;
    }

    public Long getExe_time() {
        return exe_time;
    }

    public SetPropertiesResult setExe_time(Long exe_time) {
        this.exe_time = exe_time;
        return this;
    }
}
