package cn.moquan.miot.entity.statistics;

import java.io.Serializable;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/30 8:36 </b><br />
 */
public class GetUserStatistics implements Serializable {

    private String did;

    private String data_type;

    private String key;

    private Long time_start;

    private Long time_end;

    private Integer limit;

    @Override
    public String toString() {
        return "GetUserStatistics{" +
                "did='" + did + '\'' +
                ", data_type='" + data_type + '\'' +
                ", key='" + key + '\'' +
                ", time_start=" + time_start +
                ", time_end=" + time_end +
                ", limit=" + limit +
                '}';
    }

    public String getDid() {
        return did;
    }

    public GetUserStatistics setDid(String did) {
        this.did = did;
        return this;
    }

    public String getData_type() {
        return data_type;
    }

    public GetUserStatistics setData_type(String data_type) {
        this.data_type = data_type;
        return this;
    }

    public String getKey() {
        return key;
    }

    public GetUserStatistics setKey(String key) {
        this.key = key;
        return this;
    }

    public Long getTime_start() {
        return time_start;
    }

    public GetUserStatistics setTime_start(Long time_start) {
        this.time_start = time_start;
        return this;
    }

    public Long getTime_end() {
        return time_end;
    }

    public GetUserStatistics setTime_end(Long time_end) {
        this.time_end = time_end;
        return this;
    }

    public Integer getLimit() {
        return limit;
    }

    public GetUserStatistics setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }
}
