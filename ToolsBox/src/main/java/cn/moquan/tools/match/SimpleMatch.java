package cn.moquan.tools.match;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/8/30 17:30 </b><br />
 */
public class SimpleMatch<T, R> {

    private final List<MatchConditionAndOperation<T, R>> matchList = new ArrayList<>();

    private boolean toNext = true;

    private boolean returnResult = false;

    private boolean haveDefault = false;

    public static void breakFun(SimpleMatch<?, ?> match) {
        match.toNext = false;
    }

    public static void returnFun(SimpleMatch<?, ?> match) {
        match.returnResult = true;
    }

    public void add(Function<T, Boolean> condition, Supplier<R> operation) {
        if (haveDefault) {
            throw new IllegalStateException("已存在默认(default)匹配, 不允许再添加匹配条件内容.");
        }
        Objects.requireNonNull(condition, "条件fun不可为空");
        matchList.add(MatchConditionAndOperation.of(condition, operation));
    }

    public void addDefault(Supplier<R> operation) {
        matchList.add(MatchConditionAndOperation.of(any -> true, operation));
        haveDefault = true;
    }

    public R run(T target, R defaultResult) {
        R result = defaultResult;

        toNext = true;
        returnResult = false;

        for (MatchConditionAndOperation<T, R> conditionAndOperation : matchList) {
            Boolean success = conditionAndOperation.condition.apply(target);
            if ((null != success && !success) || conditionAndOperation.operation == null) {
                continue;
            }

            // run0
            result = conditionAndOperation.operation.get();

            if (returnResult) {
                return result;
            }

            if (!toNext) {
                toNext = true;
                break;
            }

        }

        return result;
    }

    private static class MatchConditionAndOperation<T, R> {

        private Function<T, Boolean> condition;

        private Supplier<R> operation;

        public MatchConditionAndOperation(Function<T, Boolean> condition, Supplier<R> operation) {
            this.condition = condition;
            this.operation = operation;
        }

        public static <T, R> MatchConditionAndOperation<T, R> of(Function<T, Boolean> condition, Supplier<R> operation) {
            return new MatchConditionAndOperation<>(condition, operation);
        }

    }

}
