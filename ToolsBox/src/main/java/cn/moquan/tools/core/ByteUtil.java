package cn.moquan.tools.core;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/4/10 17:25 </b><br />
 */
public class ByteUtil {

    /**
     * 16 进制 String 转 bytes
     * <br />
     *
     * @param str 字符串
     * @return byte
     * @author wyh
     * @date 2023/5/12 8:56
     */
    public static byte[] hexStringToBytes(String str) {

        int len = str.length() / 2;
        byte[] bytes = new byte[len];

        for (int i = 0; i < len; i++) {

            bytes[i] = Integer.valueOf(str.substring(i * 2, i * 2 + 2), 16).byteValue();

        }

        return bytes;
    }

    private ByteUtil() {
        throw new IllegalStateException("Utility class");
    }

}
