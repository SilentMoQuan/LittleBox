package cn.moquan.miot.tool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/12 14:52 </b><br />
 */
public class MessageDigestUtil {

    private static final Logger logger = LoggerFactory.getLogger(MessageDigestUtil.class);

    public static MessageDigest getInstance(String algorithm){
        MessageDigest instance = null;
        try {
            instance = MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            logger.error("MessageDigestUtil: Not found {}.", algorithm,  e);
            throw new RuntimeException(e);
        }

        return instance;
    }

}
