package cn.moquan.tools.method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static cn.moquan.tools.method.MethodReturnEnum.CONTINUE;

/**
 * MethodReturnEnum标准流程执行包装实现类, 并行逻辑未实现
 * <br />
 * 并行逻辑 使用 Map&lt;Integer, List&lt;Supplier&lt;&gt;&gt;&gt;以 key 作为同级别可并行执行流程, 将相同优先级供给类进行并行执行, 优化执行效率, key 越小优先级越高, 0 为最高
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/3/31 9:56 </b><br />
 */
public class MethodReturnEnumWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodReturnEnumWrapper.class);

    private final List<Supplier<MethodReturnEnum>> supplierList = new ArrayList<>();

    /**
     * 为执行链添加供给函数, 执行 {@linkplain MethodReturnEnumWrapper#execution() execution}
     * 方法时会按照添加顺序进行执行并自动判断执行结果
     * <br />
     *
     * @param supplier 供给函数
     * @return MethodReturnEnumWrapper
     * @author wyh
     * @date 2023/4/18 14:08
     */
    public MethodReturnEnumWrapper addSupplier(Supplier<MethodReturnEnum> supplier) {
        supplierList.add(supplier);
        return this;
    }

    /**
     * 指定供给函数链, 若最终结果为 error 则返回 false, return 与 continue 为 true
     * <br />
     *
     * @return boolean
     * @author wyh
     * @date 2023/3/31 9:10
     */
    public boolean execution() {

        if (supplierList.isEmpty()) {
            LOGGER.warn("方法返回枚举包装类, 供给函数列表为空!");
            return true;
        }

        MethodReturnEnum returnEnum = CONTINUE.nextList(supplierList);
        switch (returnEnum) {
            case CONTINUE:
                LOGGER.warn("方法返回枚举包装类, 供给函数列表执行结果为 CONTINUE !");
                return true;
            case RETURN:
                return true;
            case ERROR:
                return false;
            default:
                String message = "" +
                        " 未知的 MethodReturnEnum 类型:" +
                        " code = " + returnEnum.getCode() +
                        " desc = " + returnEnum.getDesc();
                throw new IllegalArgumentException(message);
        }
    }

}
