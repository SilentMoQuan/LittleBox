package cn.moquan.miot.entity;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 17:21 </b><br />
 */
public class Pair<F, S> {

    private final F first;

    private final S second;

    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }

}
