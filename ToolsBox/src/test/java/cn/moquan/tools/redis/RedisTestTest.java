package cn.moquan.tools.redis;

import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static org.junit.jupiter.api.Assertions.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/10/11 11:34 </b><br />
 */
class RedisTestTest {

    @Test
    void batchDelKeys() {
        RedisTest.delKeys(RedisTest.go());
    }

}