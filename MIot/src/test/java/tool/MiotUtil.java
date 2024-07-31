package tool;

import cn.moquan.miot.api.MiotApi;
import cn.moquan.miot.basic.MIotCline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Objects;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/31 14:40 </b><br />
 */
public class MiotUtil {

    private static final Logger logger = LoggerFactory.getLogger(MiotUtil.class);

    private static final String userName;

    private static final String password;

    private static final MIotCline cline;

    static {
        ClassLoader classLoader = MiotUtil.class.getClassLoader();
        URL access = classLoader.getResource("./../classes/xiaomiAccess");
        try {
            File file = new File(access.toURI());
            BufferedReader reader = new BufferedReader(new FileReader(file));

            userName = reader.readLine();
            password = reader.readLine();

            cline = MIotCline.connect(userName, password);

            logger.debug("userName: {}", userName);
            logger.debug("password: {}", password);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("读取账号密码文件失败.");
        }
    }

    public static MIotCline getClient() {
        return cline;
    }

    public static MiotApi getAPI() {

        Class<MIotCline> clazz = MIotCline.class;
        MiotApi api = null;
        Field miotApi = null;
        try {
            miotApi = clazz.getDeclaredField("miotApi");
            if (!miotApi.isAccessible()) {
                miotApi.setAccessible(true);
            }
            api = (MiotApi) miotApi.get(cline);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        Objects.requireNonNull(api, "cline 中 api 对象为 null.");
        return api;
    }

}
