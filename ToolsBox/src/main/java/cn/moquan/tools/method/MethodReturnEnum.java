package cn.moquan.tools.method;

import java.util.List;
import java.util.function.Supplier;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/3/20 8:54 </b><br />
 */
public enum MethodReturnEnum {

    /**
     * 正确退出
     */
    RETURN(0, "正确退出"),
    /**
     * 继续执行
     */
    CONTINUE(1, "继续执行"),
    /**
     * 结束执行
     */
    ERROR(2, "结束执行");

    /**
     * 返回代码
     */
    private final int code;

    /**
     * 描述
     */
    private final String desc;

    MethodReturnEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    /**
     * 获取 MethodReturnEnumWrapper 实例
     * <br />
     *
     * @return MethodReturnEnumWrapper
     * @author wyh
     * @date 2023/3/31 9:40
     */
    public static MethodReturnEnumWrapper getWrapper(){
        return new MethodReturnEnumWrapper();
    }

    /**
     * 使用该枚举时自动将数据进行判断, 是否继续执行
     * <br />
     *
     * @param supplier 函数
     * @return MethodReturnEnum
     * @author wyh
     * @date 2023/3/29 17:50
     */
    public MethodReturnEnum next(Supplier<MethodReturnEnum> supplier) {
        switch (this) {
            case CONTINUE:
                return supplier.get();
            case ERROR:
                return ERROR;
            case RETURN:
                return RETURN;
            default:
                String builder = "" +
                        " 未知的 MethodReturnEnum 类型:" +
                        " code = " + this.code +
                        " desc = " + this.desc;
                throw new IllegalArgumentException(builder);
        }
    }

    /**
     * 使用供给函数接口列表进行判断
     * <br />
     *
     * @param supplierList 供给函数列表
     * @return MethodReturnEnum
     * @author wyh
     * @date 2023/3/29 17:50
     */
    public MethodReturnEnum nextList(List<Supplier<MethodReturnEnum>> supplierList) {

        MethodReturnEnum result = this;

        for (Supplier<MethodReturnEnum> supplier : supplierList) {

            result = result.next(supplier);

        }

        return result;
    }
}
