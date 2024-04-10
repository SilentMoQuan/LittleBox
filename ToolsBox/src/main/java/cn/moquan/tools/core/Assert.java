package cn.moquan.tools.core;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/3 15:31 </b><br />
 */
public class Assert {

    private Assert() {
        throw new IllegalStateException("util class");
    }

    public static <E extends Throwable> boolean isTrue(boolean expression, Supplier<E> exception) throws E {
        if (!expression) {
            supplierIsNonnull(exception);
            throw exception.get();
        }
        return true;
    }

    public static <E extends Throwable> boolean isFalse(boolean expression, Supplier<E> exception) throws E {
        if (expression) {
            supplierIsNonnull(exception);
            throw exception.get();
        }
        return false;
    }

    public static <T, E extends Throwable> T nonNull(T obj, Supplier<E> exception) throws E {
        if (Objects.isNull(obj)) {
            supplierIsNonnull(exception);
            throw exception.get();
        }

        return obj;
    }

    public static <T, E extends Throwable> T isNull(T obj, Supplier<E> exception) throws E {
        if (Objects.nonNull(obj)) {
            supplierIsNonnull(exception);
            throw exception.get();
        }
        return null;
    }

    public static <T extends CharSequence, E extends Throwable> T nonEmpty(T text, Supplier<E> exception) throws E {
        if (Objects.isNull(text) || text.length() == 0) {
            throw exception.get();
        }
        return text;
    }

    public static <T extends Iterable<?>, E extends Throwable> T nonEmpty(T collection, Supplier<E> exception) throws E {
        if (Objects.isNull(collection) || !collection.iterator().hasNext()) {
            throw exception.get();
        }
        return collection;
    }

    private static void supplierIsNonnull(Supplier<?> supplier) {
        Objects.requireNonNull(supplier, "Assert 类中使用的 supplier 对象不可为 null");
    }
}
