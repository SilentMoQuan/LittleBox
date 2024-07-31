package cn.moquan.miot.basic;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.Method;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import cn.moquan.miot.api.MiotApi;
import cn.moquan.miot.basic.login.LoginMIotCloud;
import cn.moquan.miot.entity.*;
import cn.moquan.miot.entity.property.GetDeviceProperty;
import cn.moquan.miot.entity.property.GetPropertiesResult;
import cn.moquan.miot.entity.property.SetDeviceProperty;
import cn.moquan.miot.entity.statistics.GetUserStatistics;

import java.util.*;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/10 11:59 </b><br />
 */
public class MIotCline {

    private final MiotBasicInfo basicInfo;

    private final MiotApi miotApi;

    private MIotCline(String userName, String password) {
        basicInfo = new MiotBasicInfo();
        basicInfo.userName(userName);
        basicInfo.password(password);

        LoginMIotCloud.loginCloud(basicInfo);
        miotApi = MiotApi.getInstance(basicInfo);
    }

    public static MIotCline connect(String userName, String password) {
        return new MIotCline(userName, password);
    }

    public void reconnect() {
        LoginMIotCloud.loginCloud(basicInfo);
    }

    @SuppressWarnings("unchecked")
    public ApiResult<Object> listDevice() {
        return JSONUtil.toBean(miotApi.deviceList(), ApiResult.class);
    }

    public String setDeviceProperties(List<SetDeviceProperty> list) {
        return miotApi.setDeviceProperty(list);
    }

    public boolean setDeviceProperty(String did, Integer siid, Integer piid, Object value) {

        List<SetDeviceProperty> setDeviceProperties = new ArrayList<>();
        SetDeviceProperty setDeviceProperty = new SetDeviceProperty(did, siid, piid, value);
        setDeviceProperties.add(setDeviceProperty);

        String resultStr = miotApi.setDeviceProperty(setDeviceProperties);
        ApiResult<?> apiResult = JSONUtil.toBean(resultStr, ApiResult.class);
        return apiResult.isSuccess();
    }

    public String getDeviceProperties(List<GetDeviceProperty> list) {
        return miotApi.getDeviceProperty(list);
    }

    public <T> List<GetPropertiesResult<T>> getDeviceProperty(String did, Integer siid, Integer piid) {
        List<GetDeviceProperty> setDeviceProperties = new ArrayList<>();
        GetDeviceProperty setDeviceProperty = new GetDeviceProperty(did, siid, piid);
        setDeviceProperties.add(setDeviceProperty);

        // get json result
        String resultStr = miotApi.getDeviceProperty(setDeviceProperties);
        ApiResult<?> apiResult = JSONUtil.toBean(resultStr, ApiResult.class);

        JSONArray array = (JSONArray) apiResult.getResult();
        return typeCache(array.toList(GetPropertiesResult.class));
    }

    @SuppressWarnings("unchecked")
    public <T> T typeCache(Object obj){
        return (T) obj;
    }

    public String listHome() {
        String homeList = miotApi.homeList();
        return homeList;
    }

    public String beaconkey() {
        String result = miotApi.beaconkey();
        return result;
    }

    public String userDevices() {
        String result = miotApi.userDevices();
        return result;
    }

    /**
     * 获取所有的 Spec Info 信息
     * 服务开启时进行初始化(非常耗时)
     *
     * @return String
     */
    public String getUserStatistics(GetUserStatistics body) {
        Objects.requireNonNull(body);
        return miotApi.getUserStatistics(body);
    }

    public String getUserDeviceData() {
        return miotApi.getUserDeviceData();
    }
}
