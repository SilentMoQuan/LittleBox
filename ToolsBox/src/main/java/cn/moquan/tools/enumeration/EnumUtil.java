package cn.moquan.tools.enumeration;

import java.util.*;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/5/19 15:03 </b><br />
 */
public final class EnumUtil {

    private static final ObjIntConsumer<Class<?>> THROW_UNKNOWN_ENUM_CODE_EXCEPTION_CONSUMER = (clazz, code) -> {
        throw new IllegalArgumentException("EnumUtil :: 枚举类: " + clazz.getSimpleName() + ",  未知的枚举 code : " + code);
    };

    private EnumUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static <T extends BaseEnum> T getEnumByCode(Integer code, Class<T> clazz) {
        T instance = getEnumInstance(code, clazz);
        if (Objects.nonNull(instance)) {
            return instance;
        }

        // 若所有枚举对象都匹配失败则报错
        THROW_UNKNOWN_ENUM_CODE_EXCEPTION_CONSUMER.accept(clazz, code);
        throw new IllegalArgumentException();
    }

    public static <T extends BaseEnum> void requestHaveCode(Integer code, Class<T> clazz) {
        if (Objects.nonNull(getEnumInstance(code, clazz))) {
            return;
        }

        THROW_UNKNOWN_ENUM_CODE_EXCEPTION_CONSUMER.accept(clazz, code);
    }

    public static <T extends BaseEnum> boolean haveCode(Integer code, Class<T> clazz) {
        return Objects.nonNull(getEnumInstance(code, clazz));
    }

    public static List<BaseEnumVo> mappingToBaseEnumVo(Collection<? extends BaseEnum> enumList) {
        if (null == enumList || enumList.isEmpty()) {
            return Collections.emptyList();
        }
        return enumList.stream()
                .map(EnumUtil::mappingToBaseEnumVo)
                .collect(Collectors.toList());
    }

    private static BaseEnumVo mappingToBaseEnumVo(BaseEnum instance) {
        return new BaseEnumVo()
                .setCode(instance.getCode())
                .setName(instance.getName())
                .setDescription(instance.getDescription());
    }

    public static List<BaseEnumVo> mappingToBaseEnumVo(Class<? extends BaseEnum> clazz) {
        requestEnum(clazz);
        return mappingToBaseEnumVo(Arrays.asList(clazz.getEnumConstants()));
    }

    public static BaseEnumVo mappingToBaseEnumVo(Class<? extends BaseEnum> clazz, int code) {
        requestEnum(clazz);
        requestNonNull(code);
        BaseEnum baseEnum = getEnumByCode(code, clazz);
        return mappingToBaseEnumVo(baseEnum);
    }

    private static <T extends BaseEnum> T getEnumInstance(Integer code, Class<T> clazz) {
        requestEnum(clazz);
        requestNonNull(code);

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

        return null;
    }

    private static void requestEnum(Class<?> clazz) {
        Objects.requireNonNull(clazz, () -> "EnumUtil :: Class 对象不可为空!");

        // 检查类是否为枚举类
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("EnumUtil :: 该类型并非枚举类, 请检查, class: " + clazz.getSimpleName());
        }
    }

    private static void requestNonNull(Integer code) {
        Objects.requireNonNull(code, () -> "EnumUtil :: code 对象不可为null!");
    }
}
