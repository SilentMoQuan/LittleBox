package cn.moquan.miot.basic.login;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONUtil;
import cn.moquan.miot.entity.MiotBasicInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.HttpCookie;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/10 15:24 </b><br />
 */
public class LoginMIotCloud {

    private static final Logger logger = LoggerFactory.getLogger(LoginMIotCloud.class);

    private static final Random RANDOM = new Random();

    private static final HashMap<String, Object> content = new HashMap<>();

    static {
        content.put("_json", "true");
        content.put("sid", "xiaomiio");
        content.put("userAgent", "APP/com.xiaomi.mihome APPV/6.0.103 iosPassportSDK/3.9.0 iOS/14.4 miHSTS");
    }

    @SuppressWarnings("unchecked")
    public static void loginCloud(MiotBasicInfo basicInfo) {
        // 获取基础信息
        String jsonStr = loginCloudStepGetBasicInfo();
        Map<String, Object> basicInfoMap = JSONUtil.toBean(jsonStr, HashMap.class);

        // 基础数据
        basicInfoMap.put("user", basicInfo.userName());
        basicInfoMap.put("password", basicInfo.password());

        // 获取 token
        jsonStr = loginCloudStepGetToKen(basicInfoMap);
        basicInfoMap = JSONUtil.toBean(jsonStr, HashMap.class);

        content.put("userId", basicInfoMap.get("userId").toString());
        content.put("passToken", basicInfoMap.get("passToken").toString());
        content.put("ssecurity", basicInfoMap.get("ssecurity").toString());

        // 通过 token 换取 服务器秘钥
        String serviceToken = loginCloudStepSwitchToken(basicInfoMap);
        content.put("serviceToken", serviceToken);

        basicInfo.sid("xiaomiio");
        basicInfo.userId(getNotBlankParam(content, "userId").toString());
        basicInfo.deviceId(getNotBlankParam(content, "deviceId").toString());
        basicInfo.clientId(getNotBlankParam(content, "clientId").toString());
        basicInfo.ssecurity(getNotBlankParam(content, "ssecurity").toString());
        basicInfo.serviceToken(getNotBlankParam(content, "serviceToken").toString());

    }

    private static Object getNotBlankParam(Map<String, Object> map, String key) {

        Object value = map.get(key);
        Assert.notNull(value, () -> new IllegalArgumentException("参数 " + key + " 不存在, 请检查."));
        logger.debug("获取参数: {} 其值为: {}", key, value);

        return value;
    }

    /**
     * 登陆到云服务器第一步
     * 这次请求必定会失败, 目的只是获取一些基础信息
     *
     * @return 接口返回值
     */
    private static String loginCloudStepGetBasicInfo() {

        // 生成 clientId , 原因未知
        String clientId = getRandomStr(16).toUpperCase();
        content.put("clientId", clientId);
        content.put("deviceId", clientId);
        logger.debug("loginCloudStepGetBasicInfo clientId is : \n{}\n", clientId);

        // 硬编码 userAgent 参数
        String userAgent = content.get("userAgent").toString();
        logger.debug("loginCloudStepGetBasicInfo userAgent is : \n{}\n", userAgent);

        // 设置 cookies
        List<HttpCookie> cookies = new ArrayList<>();
        cookies.add(new HttpCookie("sdkVersion", "3.9"));
        cookies.add(new HttpCookie("deviceId", clientId));

        // 地址
        String accountUrl = "https://account.xiaomi.com/pass/serviceLogin?sid=xiaomiio&_json=true";

        // 设置请求参数
        HttpRequest request = HttpRequest.of(accountUrl);
        request.header("User-Agent", userAgent);
        request.cookie(cookies);
        request.method(Method.GET);

        // 发送请求并返回数据
        String body = request.execute().body();
        logger.debug("loginCloudStepGetBasicInfo result is : \n{}\n", body);

        // 仔细看 body 的输出, 返回值会以 &&&START&&& 开头, 需要去除
        return body.substring(11);
    }

    private static String loginCloudStepGetToKen(Map<String, Object> basicInfoMap) {

        String user = getNotBlankParam(basicInfoMap, "user").toString();
        String password = getNotBlankParam(basicInfoMap, "password").toString();

        String cypherPassword = encryptPassword(password);
        logger.debug("loginCloudStepGetToKen cypherPassword is : {}", cypherPassword);

        HashMap<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("_json", "true");
        bodyMap.put("qs", basicInfoMap.get("qs"));
        bodyMap.put("sid", basicInfoMap.get("sid"));
        bodyMap.put("_sign", basicInfoMap.get("_sign"));
        bodyMap.put("callback", basicInfoMap.get("callback"));
        bodyMap.put("user", user);
        bodyMap.put("hash", cypherPassword);

        String bodyJsonStr = JSONUtil.toJsonStr(bodyMap);
        logger.debug("loginCloudStepGetToKen bodyJsonStr is : {}", bodyJsonStr);

        // 设置 cookies
        List<HttpCookie> cookies = new ArrayList<>();
        cookies.add(new HttpCookie("sdkVersion", "3.9"));
        cookies.add(new HttpCookie("deviceId", content.get("deviceId").toString()));

        // 地址
        String accountUrl = "https://account.xiaomi.com/pass/serviceLoginAuth2";

        // 设置请求参数
        HttpRequest request = HttpRequest.of(accountUrl);
        request.method(Method.POST);
        request.header("User-Agent", content.get("userAgent").toString());
        request.cookie(cookies);
        request.form(bodyMap);

        // 发送请求并返回数据
        String body = request.execute().body();
        logger.debug("loginCloudStepGetToKen result is : \n{}\n", body);

        // 仔细看 body 的输出, 返回值会以 &&&START&&& 开头, 需要去除
        return body.substring(11);
    }

    private static String encryptPassword(String password) {

        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            logger.error("encryptPassword: Not found MD5.", e);
            throw new RuntimeException(e);
        }

        byte[] digest = md5.digest(password.getBytes(StandardCharsets.UTF_8));

        // 将字节数组转换为十六进制字符串
        StringBuilder builder = new StringBuilder();
        for (byte b : digest) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                builder.append('0');
            }
            builder.append(hex);
        }

        return builder.toString().toUpperCase();
    }

    private static String loginCloudStepSwitchToken(Map<String, Object> infoMap) {

        String paramStr = "nonce=" + infoMap.get("nonce").toString() + "&" + infoMap.get("ssecurity");
        logger.debug("loginCloudStepSwitchToken paramStr is : \n{}\n", paramStr);

        String cypherClientSign = encryptClientSign(paramStr);
        logger.debug("loginCloudStepSwitchToken cypherClientSign is : \n{}\n", cypherClientSign);

        try {
            cypherClientSign = URLEncoder.encode(cypherClientSign, "UTF-8");
            logger.debug("loginCloudStepSwitchToken cypherClientSign_afterURLEncode is : \n{}\n", cypherClientSign);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        // 地址
        String switchUrl = infoMap.get("location").toString() + "&clientSign=" + cypherClientSign;
        logger.debug("loginCloudStepSwitchToken switchUrl is : \n{}\n", switchUrl);

        // 设置请求参数
        HttpRequest request = HttpRequest.of(switchUrl);
        request.method(Method.GET);

        // 发送请求并返回数据
        HttpResponse response = request.execute();
        logger.debug("loginCloudStepSwitchToken cookiesStr is : {}", response.getCookieStr());

        String serviceToken = Optional.ofNullable(response.getCookie("serviceToken")).map(HttpCookie::getValue).orElse("");
        logger.debug("loginCloudStepSwitchToken serviceToken is : \n{}\n", serviceToken);

        return serviceToken;
    }

    private static String encryptClientSign(String plaintext) {

        MessageDigest sha1 = null;
        try {
            sha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            logger.error("encryptPassword: Not found SHA-1.", e);
            throw new RuntimeException(e);
        }

        byte[] digest = sha1.digest(plaintext.getBytes(StandardCharsets.UTF_8));

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(digest);

        return new String(encode);
    }

    private static String byteArrToHexString(byte[] byteArr) {
        // 将字节数组转换为十六进制字符串
        StringBuilder builder = new StringBuilder();
        for (byte b : byteArr) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                builder.append('0');
            }
            builder.append(hex);
        }

        return builder.toString().toUpperCase();
    }

    private static String getRandomStr(int len) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < len; i++) {
            int index = RANDOM.nextInt(36);
            char letter = "abcdefghijklmnopqrstuvwxyz1234567890".charAt(index);
            builder.append(letter);
        }

        return builder.toString();
    }

}