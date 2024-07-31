package cn.moquan.tools.enumeration;

import java.io.Serializable;

/**
 * 枚举信息 vo
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/12/4 13:26 </b><br />
 */
public class BaseEnumVo implements Serializable {

    private static final long serialVersionUID = 9084875977142682269L;

    private Integer code;

    private String name;

    private String description;

    @Override
    public String toString() {
        return "BaseEnumVo{" +
                "code=" + code +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public BaseEnumVo setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getName() {
        return name;
    }

    public BaseEnumVo setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public BaseEnumVo setDescription(String description) {
        this.description = description;
        return this;
    }
}
