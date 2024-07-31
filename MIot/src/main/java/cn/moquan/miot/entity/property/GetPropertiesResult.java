package cn.moquan.miot.entity.property;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 16:10 </b><br />
 */
public class GetPropertiesResult<T> extends SetPropertiesResult {

    private T value;

    private long updateTime;

    @Override
    public String toString() {
        return "GetPropertiesResult{" +
                "value=" + value +
                ", updateTime=" + updateTime +
                "} " + super.toString();
    }

    public T getValue() {
        return value;
    }

    public GetPropertiesResult<T> setValue(T value) {
        this.value = value;
        return this;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public GetPropertiesResult<T> setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}
