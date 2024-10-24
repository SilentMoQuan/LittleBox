package cn.moquan.tools.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/10/11 11:21 </b><br />
 */
public class RedisTest {

    public static Jedis go() {

        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);

        JedisPool jedisPool = new JedisPool(jedisPoolConfig, "192.168.2.248", 6379);
//        jedis.auth("");

        long start = System.currentTimeMillis();

        Stream.generate(() -> "del_test_key:" + UUID.randomUUID())
                .limit(999_9999)
                .parallel()
                .forEach(id -> {
                    Jedis jedis = jedisPool.getResource();
                    jedis.select(7);
                    jedis.set(id, id);
                    jedis.close();
                });

        System.out.println("generate key value end (999_9999), use times: " + ((System.currentTimeMillis()) - start / 1000));
        return jedisPool.getResource();
    }

    public static void delKeys(Jedis jedis) {
        long start = System.currentTimeMillis();
        Set<String> keys = jedis.keys("del_test_key:*");
        for (String key : keys) {
            jedis.del(key);
        }
        System.out.println("del key value end (999_9999), use times: " + ((System.currentTimeMillis()) - start / 1000));
    }

}
