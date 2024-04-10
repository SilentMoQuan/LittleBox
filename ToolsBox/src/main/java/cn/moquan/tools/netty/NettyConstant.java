package cn.moquan.tools.netty;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:41 </b><br />
 */
public class NettyConstant {

    private NettyConstant() {
        throw new IllegalStateException("constant class");
    }

    public static final String CUSTOM_PROTOCOL_PREFIX = "04121012";

    public static final int MAGICAL_HEX_LEN = 8;

    public static final int MAGICAL_BYTE_LEN = MAGICAL_HEX_LEN / 2;

}
