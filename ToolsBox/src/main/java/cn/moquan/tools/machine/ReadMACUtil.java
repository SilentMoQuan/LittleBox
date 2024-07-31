package cn.moquan.tools.machine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * describe
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2024/1/10 9:20 </b><br />
 */
public class ReadMACUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReadMACUtil.class);

    private static final String WINDOWS = "windows";

    private static final String LINUX = "linux";

    private static final String NETWORK_DEVICE_NAME = "ens18";

    private ReadMACUtil() {
        throw new IllegalStateException("util class");
    }

    public static String getMacAddressReadSystemInfo() {
        String os = getOS();
        switch (os){
            case WINDOWS:
                return getWindowsMacAddress();
            case LINUX:
                return getLinuxMacAddress();
            default:
                throw new IllegalArgumentException("未知系统类型: " + os);
        }
    }

    private static String getLinuxMacAddress() {
        try {
            String command = "cat /sys/class/net/" + NETWORK_DEVICE_NAME + "/address";
            logger.info("command: {}", command);
            Process exec = Runtime.getRuntime().exec(command);
            exec.getOutputStream().close();

            Scanner scanner = new Scanner(exec.getInputStream());
            String address = null;
            while (scanner.hasNext()) {
                address = scanner.next();
                logger.info("address: {}", address);
            }
            return address;
        } catch (IOException e) {
            logger.error("获取linux mac 地址失败");
            throw new RuntimeException(e);
        }
    }

    private static String getWindowsMacAddress() {
        throw new UnsupportedOperationException("不支持读文件内容进行获取");
    }

    private static String getOS() {
        String property = System.getProperty("os.name").toLowerCase();
        if (property.contains("win")){
            return WINDOWS;
        }else if (property.contains(LINUX)){
            return LINUX;
        }else {
            throw new IllegalArgumentException("未知 os.name : " + property);
        }
    }

    public static String getMacAddress() {

        try {
            InetAddress localHost = InetAddress.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();

            StringBuilder builder = new StringBuilder();
            for (byte b : mac) {

                builder.append(":");
                String hexStr = String.format("%2x", b).trim();
                if (hexStr.length() < 2){
                    builder.append("0");
                }
                builder.append(hexStr);

                logger.info("{}", b);
                logger.info("{}", builder);
            }

            return builder.substring(1);
        } catch (UnknownHostException | SocketException e) {
            logger.error("方法获取mac地址失败", e);
        }

        return getMacAddressReadSystemInfo();
    }
}
