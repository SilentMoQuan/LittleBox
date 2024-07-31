package cn.moquan.miot.tool;

import java.security.SecureRandom;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/12 15:10 </b><br />
 */
public class RandomUtil {

    private static SecureRandom secureRandom = new SecureRandom();

    public static byte[] getRandomByte(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("len <= 0");
        }

        byte[] bytes = new byte[len];
        secureRandom.nextBytes(bytes);

        return bytes;
    }

}
