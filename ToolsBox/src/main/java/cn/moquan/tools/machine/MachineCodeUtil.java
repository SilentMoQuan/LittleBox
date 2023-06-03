package cn.moquan.tools.machine;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;
import java.util.Scanner;

/**
 * 计算机器码
 * <br />
 *
 * @author :<b> wangYuanHong </b><br />
 * @date :<b> 2023/2/25 10:04 </b><br />
 */
public class MachineCodeUtil {

    /**
     * 计算机器硬件码并进行MD5运算
     * <br />
     *
     * @return String
     * @author wyh
     * @date 2023/6/3 11:04
     */
    public static String getMachineCodeMd5() throws IOException, NoSuchAlgorithmException {
        String s = getWindowsCpuCode() + getWindowsBiosUUID();
        String sysName = getSystemPropertyOsName();

        if(null == sysName || !sysName.toUpperCase(Locale.ROOT).contains("WINDOWS")){
            return "";
        }

        MessageDigest md = MessageDigest.getInstance("md5");

        md.update(s.getBytes(StandardCharsets.UTF_8));

        byte[] digest = md.digest();

        return new BigInteger(1, digest).toString(16);
    }

    /**
     * 获取系统参数
     * <br />
     *
     * @return String
     * @author wyh
     * @date 2023/6/3 11:04
     */
    public static String getSystemPropertyOsName(){
        return System.getProperty("os.name");
    }

    /**
     * 获取 Windows 系统 cpu 的相关参数
     * <br />
     *
     * @return String
     * @author wyh
     * @date 2023/6/3 11:03
     */
    public static String getWindowsCpuCode() throws IOException {

        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "cpu", "get", "ProcessorId"});

        process.getOutputStream().close();

        Scanner sc = new Scanner(process.getInputStream());

        String serial = null;

        while (sc.hasNext()){
            serial = sc.next();
        }

        return serial;
    }

    /**
     * 获取 Windows 系统 BIOS 的 UUID
     * <br />
     *
     * @return String
     * @author wyh
     * @date 2023/6/3 11:02
     */
    public static String getWindowsBiosUUID() throws IOException {

        Process process = Runtime.getRuntime().exec(new String[]{"wmic", "path", "win32_computersystemproduct", "get", "uuid"});
        process.getOutputStream().close();

        Scanner sc = new Scanner(process.getInputStream());

        String serial = null;
        while (sc.hasNext()){
            serial = sc.next();
        }

        return serial;
    }

}
