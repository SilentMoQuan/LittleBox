package cn.moquan.tools.lock;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/8/6 10:13 </b><br />
 */
class CommandQueueTest {

    private static final Logger logger = LoggerFactory.getLogger(CommandQueueTest.class);

    private final CommandQueue queue = new CommandQueue();

    @Test
    void basicTest() {

        System.out.println(logger);
        System.out.println(logger.isDebugEnabled());

        logger.debug("123123123213123");

        try {
            queue.add("1");
            queue.add("2");
            queue.add("3");
            queue.add("4");

            TimeUnit.MILLISECONDS.sleep(1L);

            queue.receiveCommand("1");
            queue.receiveCommand("1");
            queue.receiveCommand("1");
            queue.receiveCommand("2");
            queue.receiveCommand("3");
            queue.add("5");
            queue.add("6");
            queue.receiveCommand("4");
            queue.receiveCommand("5");
            queue.receiveCommand("5");
            queue.receiveCommand("5");
            queue.receiveCommand("6");

            queue.add("10", 3);

//            TimeUnit.MILLISECONDS.sleep(30000L);

//            TimeUnit.MILLISECONDS.sleep(100000000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
