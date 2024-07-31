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

    /*
        8 : 魔数长度
        4 : 地址长度
        4 : 功能码长度
        4 : 数据长度标志位长度
        0 : 数据量
        4 : 校验位长度
     */
    public static final int CUSTOM_PROTOCOL_MIN_HEX_LEN = 8 + 4 + 4 + 4 + 4;

    public static final int CUSTOM_PROTOCOL_MIN_BYTE_LEN = CUSTOM_PROTOCOL_MIN_HEX_LEN / 2;

    public static final int CUSTOM_PROTOCOL_DATA_MAX_HEX_LEN = 2 * 0xffff;

    public static final int CUSTOM_PROTOCOL_DATA_MAX_BYTE_LEN = 0xffff;

    public static final int CUSTOM_PROTOCOL_MAX_HEX_LEN = CUSTOM_PROTOCOL_MIN_HEX_LEN + CUSTOM_PROTOCOL_DATA_MAX_HEX_LEN;

    public static final int CUSTOM_PROTOCOL_MAX_BYTE_LEN = CUSTOM_PROTOCOL_MAX_HEX_LEN / 2;

}
