package cn.moquan.miot;

import cn.moquan.miot.scanner.XiaoMiDeviceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/10 11:20 </b><br />
 */
public class MIotApplication {

    private static final Logger logger = LoggerFactory.getLogger(MIotApplication.class);

    public static void main(String[] args) {
        logger.info("Hello miot!\n");

        XiaoMiDeviceLoader.loadXiaoMiDevice("Miot/src/main/java/cn/moquan/miot/scanner/device");
    }
}