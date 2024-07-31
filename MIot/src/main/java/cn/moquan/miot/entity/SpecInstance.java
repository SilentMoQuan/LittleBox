package cn.moquan.miot.entity;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 17:15 </b><br />
 */
public class SpecInstance {

    private String status;

    private String model;

    private Integer version;

    private String type;

    private Long ts;

    @Override
    public String toString() {
        return "SpecInstance{" +
                "status='" + status + '\'' +
                ", model='" + model + '\'' +
                ", version=" + version +
                ", type='" + type + '\'' +
                ", ts=" + ts +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public SpecInstance setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getModel() {
        return model;
    }

    public SpecInstance setModel(String model) {
        this.model = model;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public SpecInstance setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getType() {
        return type;
    }

    public SpecInstance setType(String type) {
        this.type = type;
        return this;
    }

    public Long getTs() {
        return ts;
    }

    public SpecInstance setTs(Long ts) {
        this.ts = ts;
        return this;
    }
}
