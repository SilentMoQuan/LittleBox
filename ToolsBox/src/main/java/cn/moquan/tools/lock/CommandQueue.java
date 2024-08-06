package cn.moquan.tools.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/8/6 9:58 </b><br />
 */
public class CommandQueue extends MessageAcknowledgementQueue<String> {

    private static final Logger logger = LoggerFactory.getLogger(CommandQueue.class);

    @Override
    protected boolean process(String peek) {

        logger.debug("process :: {}", peek);

        // 如果是超时唤醒则会进入循环
        // 如果通过其他方式唤醒需要将循环标识切换为 false
        needCirculate(true);
        LockSupport.parkNanos(this, TimeUnit.SECONDS.toNanos(3));

        return true;
    }

    public void receiveCommand(String command) throws InterruptedException {
        needCirculate(!Objects.equals(command, getCurrentElement()));
        LockSupport.unpark(getWorker().orElseThrow(() -> new IllegalStateException("没有正在运行的工作线程")));
        // 保证 unpark() 后数据处理完成
        TimeUnit.MILLISECONDS.sleep(10);
    }

}
