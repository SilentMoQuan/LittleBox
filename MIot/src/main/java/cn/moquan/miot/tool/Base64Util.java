package cn.moquan.miot.tool;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/12 14:56 </b><br />
 */
public class Base64Util {

    private static final Base64.Encoder encoder = Base64.getEncoder();
    private static final Base64.Decoder decoder = Base64.getDecoder();

    public static byte[] encode(String plaintext){
        return encode(plaintext.getBytes(StandardCharsets.UTF_8));
    }
    public static byte[] encode(byte[] src){
        return encoder.encode(src);
    }

    public static String encodeToString(String plaintext){
        return encodeToString(plaintext.getBytes(StandardCharsets.UTF_8));
    }

    public static String encodeToString(byte[] src){
        return new String(encode(src));
    }

    public static byte[] decode(String src){
        return decoder.decode(src);
    }

    public static byte[] decode(byte[] src){
        return decoder.decode(src);
    }

}
