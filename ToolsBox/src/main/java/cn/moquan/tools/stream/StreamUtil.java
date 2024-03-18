package cn.moquan.tools.stream;

import cn.moquan.tools.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Stream 工具
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/6/30 14:59 </b><br />
 */
public class StreamUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(StreamUtil.class);

    private StreamUtil() {
        throw new IllegalStateException("Utility class");
    }

    private static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

    public static <E, V> List<V> toList(Collection<E> collection, Function<E, V> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return new ArrayList<>(0);
        }

        return collection.stream()
                .map(func)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <E, T extends Collection<V>, V> List<V> flatToList(Collection<E> collection, Function<E, T> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return new ArrayList<>(0);
        }

        return collection.stream()
                .map(func)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <E, V> List<V> toDistinctList(Collection<E> collection, Function<E, V> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyList();
        }

        return collection.stream()
                .map(func)
                .distinct()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public static <E, V> Set<V> toSet(Collection<E> collection, Function<E, V> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptySet();
        }

        return collection.stream()
                .map(func)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static <E, T extends Collection<V>, V> Set<V> flatToSet(Collection<E> collection, Function<E, T> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptySet();
        }

        return collection.stream()
                .map(func)
                .flatMap(Collection::stream)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> collection,
                                            Function<E, K> keyFunc,
                                            Function<E, V> valueFunc) {

        Objects.requireNonNull(keyFunc);
        Objects.requireNonNull(valueFunc);

        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.toMap(keyFunc, valueFunc, throwingMerger()));
    }

    public static <E, K, V> Map<K, V> toMap(Collection<E> collection,
                                            Function<E, K> keyFunc,
                                            Function<E, V> valueFunc,
                                            BinaryOperator<V> margeFunc) {

        Objects.requireNonNull(keyFunc);
        Objects.requireNonNull(valueFunc);

        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.toMap(keyFunc, valueFunc, margeFunc));
    }

    public static <K, E> Map<K, List<E>> groupByKeyToList(Collection<E> collection, Function<E, K> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(func));
    }

    public static <K, E> Map<K, Long> groupByKeyLongCount(Collection<E> collection, Function<E, K> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(func, Collectors.reducing(0L, e -> 1L, Long::sum)));
    }

    public static <K, E> Map<K, Integer> groupByKeyIntegerCount(Collection<E> collection, Function<E, K> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(func, Collectors.reducing(0, e -> 1, Integer::sum)));
    }

    public static <K, E> Map<K, Set<E>> groupByKeyToSet(Collection<E> collection, Function<E, K> func) {

        Objects.requireNonNull(func);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(func, Collectors.toSet()));
    }

    public static <K, E, V> Map<K, List<V>> groupByKeyMapToList(Collection<E> collection,
                                                                Function<E, K> keyFunction,
                                                                Function<E, V> mappingFunction) {

        Objects.requireNonNull(keyFunction);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(keyFunction, Collectors.mapping(mappingFunction, Collectors.toList())));
    }

    public static <K, E, V> Map<K, Set<V>> groupByKeyMapToSet(Collection<E> collection,
                                                              Function<E, K> keyFunction,
                                                              Function<E, V> mappingFunction) {

        Objects.requireNonNull(keyFunction);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(keyFunction, Collectors.mapping(mappingFunction, Collectors.toSet())));
    }

    /**
     * 通过分组将List转为 Map< object, Map< K,V>>
     * <br />
     *
     * @param collection            待转换列表
     * @param keyFunction           key func
     * @param innerMapKeyFunction   内部map key func
     * @param innerMapValueFunction 内部map value func
     * @param margeFunction         内部map 合并 func
     * @return Map<K, Map < IK, IV>>
     * @author wyh
     * @date 2023/7/3 14:04
     */
    public static <E, K, IK, IV> Map<K, Map<IK, IV>> groupByKeyMapToMap(Collection<E> collection,
                                                                        Function<E, K> keyFunction,
                                                                        Function<E, IK> innerMapKeyFunction,
                                                                        Function<E, IV> innerMapValueFunction,
                                                                        BinaryOperator<IV> margeFunction) {

        Objects.requireNonNull(keyFunction);
        if (null == collection || collection.isEmpty()) {
            return Collections.emptyMap();
        }

        return collection.stream()
                .collect(Collectors.groupingBy(keyFunction, Collectors.toMap(innerMapKeyFunction, innerMapValueFunction, margeFunction)));
    }

    @SuppressWarnings("unchack")
    public static <T> T chooseSecond(T first, T second) {
        //log

        String firstFormat = StringUtil.toStringFormat(first);
        String secondFormat = StringUtil.toStringFormat(second);
        String stackTranceFormat = StringUtil.stackTraceElementFormat();
        LOGGER.warn("出现同key冲突, \nfirstValue: {}, \nsecondValue: {} \nStackTrance: {}", firstFormat, secondFormat, stackTranceFormat);
        return second;
    }

    @SuppressWarnings("unchack")
    public static <T> BinaryOperator<T> chooseSecond() {
        //log
        return (first, second) -> {
            String firstFormat = StringUtil.toStringFormat(first);
            String secondFormat = StringUtil.toStringFormat(second);
            String stackTranceFormat = StringUtil.stackTraceElementFormat();
            LOGGER.warn("出现同key冲突, \nfirstValue: {}, \nsecondValue: {} \nStackTrance: {}", firstFormat, secondFormat, stackTranceFormat);
            return second;
        };
    }

    @SuppressWarnings("unchack")
    public static <T> T chooseSecondNoWarning(T first, T second) {
        //log
        return second;
    }

    @SuppressWarnings("unchack")
    public static <T> BinaryOperator<T> chooseSecondNoWarning() {
        //log
        return (first, second) -> second;
    }

    public static <T, R> R stream(Collection<T> collection, Function<Stream<T>, R> function) {
        return function.apply(collection.stream());
    }

}
