import cn.moquan.miot.MIotApplication;
import cn.moquan.miot.api.MiotApi;
import cn.moquan.miot.basic.MIotCline;
import cn.moquan.miot.entity.instance.DeviceInstance;
import cn.moquan.miot.entity.property.GetDeviceProperty;
import cn.moquan.miot.entity.property.GetPropertiesResult;
import cn.moquan.miot.entity.property.SetDeviceProperty;
import cn.moquan.miot.entity.statistics.GetUserStatistics;
import cn.moquan.miot.tool.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/23 13:37 </b><br />
 */
public class ApiTest {

    private static final Logger logger = LoggerFactory.getLogger(MIotApplication.class);

    private static final String userName;
    private static final String password;

    static {
        ClassLoader classLoader = ApiTest.class.getClassLoader();
        URL access = classLoader.getResource("../classes/xiaomiAccess");
        try {
            File file = new File(access.toURI());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            userName = reader.readLine();
            password = reader.readLine();

            logger.debug("userName: {}", userName);
            logger.debug("password: {}", password);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("读取账号密码文件失败.");
        }
    }

    public static void main(String[] args) {
        logger.info("Hello miot!\n");

        Date from = Date.from(Instant.ofEpochSecond(1721750400));
        System.out.println(from);

        MIotCline cline = testLogin();

        cline.userDevices();

//        testDeviceList(cline);

//        testSetProperty(cline);

//        testGetProperty(cline);

//        cline.reconnect();

//        testDeviceList(cline);

//        testSetProperty(cline);

//        testSetProperties(cline);

//        testGetProperty(cline);
//        testGetProperties(cline);

//        testGatAllSpec();

//        testGatSpecInfo();

//        testGetHomeList(cline);

        testGetUserStatistics(cline);

//        testGetUserDeviceData(cline);
    }

    private static void testGetHomeList(MIotCline cline) {

        String homeList = cline.listHome();
        String format = StringUtil.toStringFormat(homeList);
        debug("device list", format);
    }


    public static MIotCline testLogin() {
        // login
        return MIotCline.connect(userName, password);
    }

    public static void testDeviceList(MIotCline cline) {
        // get device list
        String deviceList = cline.listDevice().getResult().toString();
        String format = StringUtil.toStringFormat(deviceList);
        debug("device list", format);
    }

    public static void testSetProperty(MIotCline cline) {
        // set Properties
        String did = "731823722";
        Integer siid = 2;
        Integer piid = 1;
        Object value = true;

        boolean isSuccess = cline.setDeviceProperty(did, siid, piid, value);
        debug("set properties isSuccess?", String.valueOf(isSuccess));
    }

    public static void testSetProperties(MIotCline cline) {
        // set Properties
        String did = "731823722";
        Integer siid = 2;
        Integer piid = 1;
        Object value = true;

        List<SetDeviceProperty> list = new ArrayList<>();
        list.add(new SetDeviceProperty(did, siid, piid, value));
        // 执行一样的会报错
//        list.add(new SetDeviceProperty(did, siid, piid, value));
        list.add(new SetDeviceProperty(did, 13, 1, value));

        ;
        debug("set properties result: ", cline.setDeviceProperties(list));
    }

    public static void testGetProperty(MIotCline cline) {
        // set Properties
        String did = "731823722";
        Integer siid = 2;
        Integer piid = 1;

        List<GetPropertiesResult<Boolean>> isOpen = cline.getDeviceProperty(did, siid, piid);
        debug("get properties, isOpen?", isOpen.get(0).getValue().toString());
    }

    private static void testGetProperties(MIotCline cline) {
        // set Properties
        String did = "731823722";
        Integer siid = 2;
        Integer piid = 1;

        List<GetDeviceProperty> list = new ArrayList<>();
        list.add(new GetDeviceProperty(did, siid, piid));
        list.add(new GetDeviceProperty(did, siid, piid));
        list.add(new GetDeviceProperty(did, 13, 1));

        debug("get properties result: ", cline.getDeviceProperties(list));
    }


    public static void testGatAllSpec() {
        MiotApi.getAllSpecInfo();
    }

    public static void testGatSpecInfo() {
        Optional<DeviceInstance> info = MiotApi.getSpecInfoByType("cuco.plug.v3");
        String format = StringUtil.toStringFormat(info.toString());
        debug("testGatSpecInfo", format);
    }

    public static void testGetUserStatistics(MIotCline cline) {

        long start = System.currentTimeMillis() / 1000 - 3600 * 24 * 7;
        long end = System.currentTimeMillis() / 1000 + 60;

        GetUserStatistics statistics = new GetUserStatistics();
        statistics.setDid("731823722");
        statistics.setData_type("stat_day_v3");
        statistics.setKey("11.1");
        statistics.setTime_start(start);
        statistics.setTime_end(end);
        statistics.setLimit(7);
        String format = StringUtil.toStringFormat(cline.getUserStatistics(statistics));
        debug("testGetUserStatistics", format);
    }

    public static void testGetUserDeviceData(MIotCline cline) {
        String format = StringUtil.toStringFormat(cline.getUserDeviceData());
        debug("testDeviceGetSettingV2", format);
    }

    public static void debug(String title, String result) {
        logger.debug(
                "===============================================================\n" +
                        ">>> {}: \n" +
                        "{}",
                title,
                result
        );
    }

}
