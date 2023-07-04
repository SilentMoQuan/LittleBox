package cn.moquan.tools.enumeration;

import java.util.Objects;

/**
 * 枚举工具类
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/5/19 15:03 </b><br />
 */
public final class EnumUtil {

    public static <T extends BaseEnum> T getEnumByCode(int code, Class<T> clazz) {

        Objects.requireNonNull(clazz);

        // 检查类是否为枚举类
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("EnumUtil :: 该类型并非枚举类, 请检查, class: " + clazz.getSimpleName());
        }

        // 获取枚举类下所有的枚举对象
        T[] enumArr = clazz.getEnumConstants();

        // 以 code 作为下标去查询是否存在相应的枚举对象
        if (0 <= code && code < enumArr.length && code == enumArr[code].getCode()) {
            return enumArr[code];
        }

        // 循环遍历每个枚举查询枚举对象
        for (T enumObj : enumArr) {

            if (code == enumObj.getCode()) {
                return enumObj;
            }

        }

        // 若所有枚举对象都匹配失败则报错
        throw new IllegalArgumentException("EnumUtil :: 枚举类: " + clazz.getSimpleName() + ",  未知的枚举 code : " + code);
    }

}
