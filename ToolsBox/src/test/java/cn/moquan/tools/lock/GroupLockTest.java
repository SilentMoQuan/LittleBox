package cn.moquan.tools.lock;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 */

class GroupLockTest {

    private static final Logger log = LoggerFactory.getLogger(GroupLockTest.class);

    public GroupLock<String> groupLock = new GroupLock<>();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("AB锁测试")
    @Timeout(1)
    void abLockTest() {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        Runnable massageRunA = () -> {
            try {
                groupLock.lock("a");
                for (int i = 0; i < 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    log.info("A sleep for " + (i + 1) + " milliseconds");
                }
                log.info("massageRunA");
                countDownLatch.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                groupLock.unlock("a");
            }
        };

        Runnable massageRunB = () -> {
            try {
                groupLock.lock("b");
                for (int i = 0; i < 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    log.info("B sleep for " + (i + 1) + " milliseconds");
                }
                log.info("massageRunB");
                countDownLatch.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                groupLock.unlock("b");
            }
        };

        new Thread(massageRunA).start();
        new Thread(massageRunB).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("AA锁测试")
    void aaLockTest() {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        Runnable massageRunA1 = () -> {
            try {
                groupLock.lock("a");
                for (int i = 0; i < 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    log.info("A1 sleep for " + (i + 1) + " milliseconds");
                }
                log.info("massageRunA1");
                countDownLatch.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                groupLock.unlock("a");
            }
        };

        Runnable massageRunA2 = () -> {
            try {
                groupLock.lock("a");
                for (int i = 0; i < 5; i++) {
                    TimeUnit.MILLISECONDS.sleep(100);
                    log.info("A2 sleep for " + (i + 1) + " milliseconds");
                }
                log.info("massageRunA2");
                countDownLatch.countDown();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                groupLock.unlock("a");
            }
        };

        new Thread(massageRunA1).start();
        new Thread(massageRunA2).start();

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("锁删除测试")
    void lockRemoveTest() {

        String lockKey = "a";

        try {
            groupLock.lock(lockKey);
            log.info("use a group lock");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            groupLock.unlock(lockKey);
        }

        assertNotNull(groupLock.getLock(lockKey));

        groupLock.remove(lockKey);
        assertNull(groupLock.getLock(lockKey));

    }

    @Test
    @DisplayName("锁延时删除测试")
    void lockDelayRemoveTest() {

        String lockKey = "a";

        try {
            groupLock.lock(lockKey);
            log.info("use [ a ] group lock");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            groupLock.unlock(lockKey);
        }

        assertNotNull(groupLock.getLock(lockKey));

        groupLock.delayRemove(lockKey, 900);
        assertNotNull(groupLock.getLock(lockKey));

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        assertNull(groupLock.getLock(lockKey));

    }

    @Test
    @DisplayName("锁更新延时删除测试")
    void lockUpdateDelayRemoveTest() {

        String lockKey = "a";

        try {
            groupLock.lock(lockKey);
            log.info("use [ a ] group lock");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            groupLock.unlock(lockKey);
        }

        assertNotNull(groupLock.getLock(lockKey));

        try {

            long timeMillis = System.currentTimeMillis();

            for (int i = 0; i < 5; i++) {
                groupLock.delayRemove(lockKey, 100);
                TimeUnit.MILLISECONDS.sleep(50);
                assertNotNull(groupLock.getLock(lockKey));

                log.info("第 [ " + (i + 1) + " ] 次延时, 已延时 [ " + (System.currentTimeMillis() - timeMillis) + " ] 毫秒");
            }

            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        assertNull(groupLock.getLock(lockKey));

    }

}