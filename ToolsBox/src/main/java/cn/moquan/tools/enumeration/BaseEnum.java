package cn.moquan.tools.enumeration;

/**
 * 继承该枚举的对象应存在以下属性
 * <br />
 * private final int      code        <br />
 * private final String   name        <br />
 * private final String   description <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/5/19 15:39 </b><br />
 */
public interface BaseEnum {

    /**
     * 获取枚举 code
     * <br />
     *
     * @return int
     * @date 2023/5/19 16:13
     */
    int getCode();

    /**
     * 获取枚举对象名
     * <br />
     *
     * @return int
     * @date 2023/5/19 16:13
     */
    String getName();

    /**
     * 获取枚举对象描述
     * <br />
     *
     * @return int
     * @date 2023/5/19 16:13
     */
    String getDescription();

}
