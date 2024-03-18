package cn.moquan.tools.lock;

import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/3/18 8:38 </b><br />
 */
class GroupLockTest {

    public GroupLock<String> groupLock = new GroupLock<>();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("AB锁测试")
    void abLockTest() {

        CountDownLatch countDownLatch = new CountDownLatch(2);

        Runnable massageRunA = () -> {
            try {
                groupLock.lock("a");
                for (int i = 0; i < 5; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("A sleep for " + (i + 1) + " seconds");
                }
                System.out.println("massageRunA");
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
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("B sleep for " + (i + 1) + " seconds");
                }
                System.out.println("massageRunB");
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
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("A1 sleep for " + (i + 1) + " seconds");
                }
                System.out.println("massageRunA1");
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
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("A2 sleep for " + (i + 1) + " seconds");
                }
                System.out.println("massageRunA2");
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
            System.out.println("use a group lock");
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
            System.out.println("use [ a ] group lock");
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

}