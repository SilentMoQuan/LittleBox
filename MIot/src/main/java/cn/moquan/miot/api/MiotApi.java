package cn.moquan.miot.api;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.moquan.miot.entity.MiotBasicInfo;
import cn.moquan.miot.entity.Pair;
import cn.moquan.miot.entity.SpecInstance;
import cn.moquan.miot.entity.instance.DeviceInstance;
import cn.moquan.miot.entity.property.GetDeviceProperty;
import cn.moquan.miot.entity.property.SetDeviceProperty;
import cn.moquan.miot.entity.statistics.GetUserStatistics;
import cn.moquan.miot.log.SimpleLog;
import cn.moquan.miot.tool.Base64Util;
import cn.moquan.miot.tool.MessageDigestUtil;
import cn.moquan.miot.tool.RandomUtil;
import cn.moquan.miot.tool.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.HttpCookie;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/12 13:54 </b><br />
 */
public class MiotApi implements SimpleLog {

    private static final Logger logger = LoggerFactory.getLogger(MiotApi.class);

    private static final String SERVICE = "https://api.io.mi.com/app";

    private static Map<String, String> SPEC_INFO_MAP = Collections.emptyMap();

    static {
        HttpGlobalConfig.setTimeout(3000);
    }

    private final MiotBasicInfo basicInfo;

    private MiotApi() {
        throw new UnsupportedOperationException("api class");
    }

    private MiotApi(MiotBasicInfo basicInfo) {
        Objects.requireNonNull(basicInfo, "Get MiotApi instant, basicInfo is null");
        this.basicInfo = basicInfo;
    }

    public static MiotApi getInstance(MiotBasicInfo basicInfo) {
        return new MiotApi(basicInfo);
    }

    /**
     * 获取所有的 Spec Info 信息
     * 服务开启时进行初始化(非常耗时)
     *
     * @return String
     */
    public String getUserStatistics(GetUserStatistics body) {
        String uri = "/v2/user/statistics";
        String url = SERVICE + uri;
        HttpRequest request = HttpRequest.of(url);
        request.method(Method.POST);

        Map<String, Object> param = new HashMap<>();
        param.put("did", body.getDid());
        param.put("data_type", body.getData_type());
        param.put("key", body.getKey());
        param.put("time_start", body.getTime_start());
        param.put("time_end", body.getTime_end());
        param.put("limit", body.getLimit());

        Map<String, Object> data = signData(uri, JSONUtil.toJsonStr(param), basicInfo.ssecurity());

        return sendPost(url, data);
    }
    /**
     * 获取所有的 Spec Info 信息
     * 服务开启时进行初始化(非常耗时)
     *
     * @return String
     */
    public String _deviceGetSettingV2(GetUserStatistics body) {
        String uri = "/v2/device/getsettingv2";
        String url = SERVICE + uri;
        HttpRequest request = HttpRequest.of(url);
        request.method(Method.POST);

        Map<String, Object> param = new HashMap<>();
//        param.put("did", body.getDid());
        param.put("did", "731823722");
        param.put("last_id", null);
        param.put("prefix_filter", "stat_");
        param.put("settings", new String[]{"11.1"});

        Map<String, Object> data = signData(uri, JSONUtil.toJsonStr(param), basicInfo.ssecurity());

        return sendPost(url, data);
    }
    /**
     * 获取所有的 Spec Info 信息
     * 服务开启时进行初始化(非常耗时)
     *
     * @return String
     */
    public String getUserDeviceData() {

        /*
            did	            string	设备id。 必选参数
            key	            string	属性或事件名，可选参数。(注意：如果设备是蓝牙设备，传入的是object id， 且为十进制数据；如果是wifi设备，才传入自定义属性或事件名，可以在开发者平台-产品-功能定义中查看)，如果是miot-spec设备，请传入（siid.piid或者siid.eiid）
            type	        string	必选参数[prop/event], 如果是查询上报的属性则type为prop，查询上报的事件则type为event,
            time_start	    number	数据起点，单位是秒。必选参数
            time_end	    number	数据终点，单位是秒。必选参数，time_end必须大于time_start,
            group	        string	返回数据的方式，默认raw,可选值为hour、day、week、month。可选参数.
            limit	        string	返回数据的条数，默认20，最大1000。可选参数.
            uid	            number	要查询的用户id 。可选参数
         */

        String uri = "/v2/user/get_user_device_data";
        String url = SERVICE + uri;
        HttpRequest request = HttpRequest.of(url);
        request.method(Method.POST);

        Map<String, Object> param = new HashMap<>();
//        param.put("did", body.getDid());
        // must
        param.put("did", "731823722");
        param.put("type", "event");
        param.put("time_start", System.currentTimeMillis() / 1000 - 3600 * 7 * 24);
        param.put("time_end", System.currentTimeMillis() / 1000);

        // other
        param.put("key", null);
        param.put("group", null);
        param.put("limit", null);
        param.put("uid", null);

        Map<String, Object> data = signData(uri, JSONUtil.toJsonStr(param), basicInfo.ssecurity());

        return sendPost(url, data);
    }

    /**
     * 获取所有的 Spec Info 信息
     * 服务开启时进行初始化(非常耗时)
     *
     * @return String
     */
    public static Map<String, String> getAllSpecInfo() {
        HttpRequest request = HttpRequest.of("https://miot-spec.org/miot-spec-v2/instances?status=all");
        request.method(Method.GET);

        String jsonStrResult = request.execute().body();
        Map<String, Object> resutlMap = JSONUtil.toBean(jsonStrResult, Map.class);
        JSONArray instanceArr = (JSONArray) resutlMap.get("instances");
        List<SpecInstance> instanceList = instanceArr.toList(SpecInstance.class);

        Map<String, String> specMap = instanceList.parallelStream()
                .collect(Collectors.toMap(
                        SpecInstance::getModel,
                        instance -> new Pair<>(instance.getType(), instance.getVersion()),
                        (pair1, pair2) -> pair1.getSecond() >= pair2.getSecond() ? pair1 : pair2)
                ).entrySet().parallelStream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getFirst()));

        SPEC_INFO_MAP = specMap;
        return specMap;
    }

    /**
     * 获取单个设备的 spec 信息
     *
     * @param type 参数类型 (model 数据)
     * @return String
     */
    public static Optional<DeviceInstance> getSpecInfoByType(String type) {

        // 没有进行过初始化则自动初始化
        if (SPEC_INFO_MAP.isEmpty()) {
            synchronized (MiotApi.class) {
                if (SPEC_INFO_MAP.isEmpty()) {
                    getAllSpecInfo();
                }
            }
        }

        String urn = SPEC_INFO_MAP.getOrDefault(type, null);

        // 使用 urn 获取 spec info
        return getSpecInfoByURN(urn);
    }

    /**
     * 获取单个设备的 spec 信息
     *
     * @param urn 小米设备 urn
     * @return String
     */
    public static Optional<DeviceInstance> getSpecInfoByURN(String urn) {
        // 参数校验
        if (null == urn || urn.isEmpty()) {
            return Optional.empty();
        }

        // 发送请求
        String url = "http://miot-spec.org/miot-spec-v2/instance?type=" + urn;
        HttpRequest request = HttpRequest.of(url);
        request.method(Method.GET);

        String jsonStrResult = request.execute().body();
        // 转
        DeviceInstance bean = JSONUtil.toBean(jsonStrResult, DeviceInstance.class);
        return Optional.ofNullable(bean);
    }

    /**
     * 获取设备参数
     *
     * @param list 参数对象列表
     * @return String 结果 json String
     */
    public String getDeviceProperty(List<GetDeviceProperty> list) {

        if (null == list || list.isEmpty()) {
            return "";
        }

        String uri = "/prop/get";
        return sendMiotSpecRequest(uri, list);
    }

    /**
     * 设置设备参数
     *
     * @param list 参数对象列表
     * @return String 结果 json String
     */
    public String setDeviceProperty(List<SetDeviceProperty> list) {

        if (null == list || list.isEmpty()) {
            return "";
        }

        String uri = "/prop/set";

        return sendMiotSpecRequest(uri, list);
    }

    /**
     * 发送 miot spec 请求
     *
     * @param list 参数对象列表
     * @return String 结果 json String
     */
    public String sendMiotSpecRequest(String uri, List<? extends GetDeviceProperty> list) {

        uri = "/miotspec" + uri;
        String url = SERVICE + uri;

        // 固定格式
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("params", list);

        // 计算摘要
        dataMap = signData(uri, JSONUtil.toJsonStr(dataMap), basicInfo.ssecurity());

        return sendPost(url, dataMap);
    }

    /**
     * 获取当前用户下的设备列表
     *
     * @return String json String
     */
    public String beaconkey() {

        String uri = "/v2/device/blt_get_beaconkey";
        String url = SERVICE + uri;

        // 参数
        Map<String, Object> prarmMap = new HashMap<>();
        prarmMap.put("did", "731823722");
        prarmMap.put("pdid", 1);

        // 计算摘要
        prarmMap = signData(uri, JSONUtil.toJsonStr(prarmMap), basicInfo.ssecurity());

        return sendPost(url, prarmMap);
    }


    public String userDevices() {

        String uri = "/user/get_user_device_data";
        String url = SERVICE + uri;

        long currentTime = System.currentTimeMillis() / 1000;

        // 固定格式
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("did", "731823722");
        dataMap.put("key", "device_log");
        dataMap.put("type", "prop");
        dataMap.put("time_start", currentTime - 864000 * 7);
        dataMap.put("time_end", currentTime + 60);
        dataMap.put("limit", 1);

        // 计算摘要
        dataMap = signData(uri, JSONUtil.toJsonStr(dataMap), basicInfo.ssecurity());

        return sendPost(url, dataMap);
    }

    /**
     * 获取当前用户下的设备列表
     *
     * @return String json String
     */
    public String deviceList() {

        String uri = "/home/device_list";
        String url = SERVICE + uri;

        // 参数
        Map<String, Object> prarmMap = new HashMap<>();
        prarmMap.put("getVirtualModel", false);
        prarmMap.put("getHuamiDevices", 0);

        // 计算摘要
        prarmMap = signData(uri, JSONUtil.toJsonStr(prarmMap), basicInfo.ssecurity());

        return sendPost(url, prarmMap);
    }

    /**
     * 获取当前用户下的房间列表
     *
     * @return String json String
     */
    public String homeList() {

        String uri = "/homeroom/gethome";
        String url = SERVICE + uri;

        // 参数
        Map<String, Object> prarmMap = new HashMap<>();
        prarmMap.put("fetch_share_dev", true);

        // 计算摘要
        prarmMap = signData(uri, JSONUtil.toJsonStr(prarmMap), basicInfo.ssecurity());

        return sendPost(url, prarmMap);
    }

    private String signNonce(String ssecurity, String nonce) {
        MessageDigest digestInstance = MessageDigestUtil.getInstance("SHA-256");

        digestInstance.update(Base64Util.decode(ssecurity));
        digestInstance.update(Base64Util.decode(nonce));

        return Base64Util.encodeToString(digestInstance.digest());
    }

    private Map<String, Object> signData(String uri, String dataJsonStr, String ssecurity) {

        // log
        debug("signData :: uri          :: {}", uri);
        debug("signData :: dataJsonStr  :: {}", dataJsonStr);
        debug("signData :: ssecurity    :: {}", ssecurity);


        byte[] randomByte = RandomUtil.getRandomByte(8);
        byte[] timeRandomByte = ByteBuffer.allocate(4)
                .putInt((int) System.currentTimeMillis() / 1000 / 60)
                .array();

        byte[] combinedBytes = new byte[randomByte.length + timeRandomByte.length];
        System.arraycopy(randomByte, 0, combinedBytes, 0, randomByte.length);
        System.arraycopy(timeRandomByte, 0, combinedBytes, randomByte.length, timeRandomByte.length);

        String nonce = Base64Util.encodeToString(combinedBytes);
        debug("signData :: nonce        :: {}", nonce);
        String signNonce = signNonce(ssecurity, nonce);
        debug("signData :: signNonce    :: {}", signNonce);

        String msg = String.join("&", uri, signNonce, nonce, "data=" + dataJsonStr);
        debug("signData :: msg          :: {}", msg);

        byte[] signNonceByteArr = Base64Util.decode(signNonce);
        SecretKeySpec hmacSHA256Key = new SecretKeySpec(signNonceByteArr, "HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(hmacSHA256Key);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        byte[] msgByteArr = msg.getBytes(StandardCharsets.UTF_8);
        byte[] hmacDigest = mac.doFinal(msgByteArr);
        String signature = Base64Util.encodeToString(hmacDigest);
        debug("signData :: signature    :: {}", signature);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("_nonce", nonce);
        resultMap.put("signature", signature);
        resultMap.put("data", dataJsonStr);

        debug("signData :: resultMap    :: {}", StringUtil.toStringFormat(resultMap));

        return resultMap;
    }

    private Object getNotBlankParam(Map<String, Object> map, String key) {

        Object value = map.get(key);
        Assert.notNull(value, () -> new IllegalArgumentException("参数 " + key + " 不存在, 请检查."));
        logger.debug("获取参数: {} 其值为: {}", key, value);

        return value;
    }

    private String sendPost(String url, Map<String, Object> data) {

        String userAgent = "iOS-14.4-6.0.103-iPhone12,3--D7744744F7AF32F0544445285880DD63E47D9BE9-8816080-84A3F44E137B71AE-iPhone";
        String xiaomiProtocalFlag = "PROTOCAL-HTTP2";

        // cookies
        List<HttpCookie> cookies = new ArrayList<>();
        cookies.add(new HttpCookie("PassportDeviceId", basicInfo.deviceId()));
        cookies.add(new HttpCookie("userId", basicInfo.userId()));
        cookies.add(new HttpCookie("serviceToken", basicInfo.serviceToken()));

        // http request
        HttpRequest request = HttpRequest.of(url);
        request.cookie(cookies);
        request.method(Method.POST);
        request.form(data);
        request.header("User-Agent", userAgent);
        request.header("x-xiaomi-protocal-flag-cli", xiaomiProtocalFlag);

        // request log
        debug("sendPost :: url      ::                                :: {}", request.getUrl());
        debug("sendPost :: method   ::                                :: {}", request.getMethod());
        debug("sendPost :: cookie   ::                                :: {}", StringUtil.toStringFormat(cookies));
        debug("sendPost :: header   :: User-Agent                     :: {}", userAgent);
        debug("sendPost :: header   :: x-xiaomi-protocal-flag-cli     :: {}", xiaomiProtocalFlag);
        debug("sendPost :: form     ::                                :: {}", StringUtil.toStringFormat(data));

        HttpResponse response = request.execute();
        String body = response.body();

        // response log
        debug("sendPost :: status   ::                                :: {}", response.getStatus());
        debug("sendPost :: cookie   ::                                :: {}", StringUtil.toStringFormat(response.getCookies()));
        debug("sendPost :: body     ::                                :: {}", body);
        debug("sendPost :: body     :: format                         :: {}", StringUtil.toStringFormat(body));

        return body;
    }

    @Override
    public String getPrefix() {
        return "[ MiotApi ] :: ";
    }

    @Override
    public Logger getLog() {
        return logger;
    }

}



