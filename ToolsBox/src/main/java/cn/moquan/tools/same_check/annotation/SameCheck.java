package cn.moquan.tools.same_check.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 检查类字段是否相同
 * @author MoQuan
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SameCheck {

    /**
     * 会根据 value 创建组, 同一组类的字段(访问类型/修饰符/类型/字段名)必须相同
     * @return 组 id
     */
    String value() default "";

    /**
     * 额外定义的检查类型列表, 注册在该列表的类与当前类的字段(访问类型/修饰符/类型/字段名)必须相同
     * @return 类对象
     */
    Class<?>[] checkArr() default {};

}
