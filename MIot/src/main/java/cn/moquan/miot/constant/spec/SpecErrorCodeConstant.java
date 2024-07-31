package cn.moquan.miot.constant.spec;

import java.util.HashMap;
import java.util.Map;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/29 13:49 </b><br />
 */
public class SpecErrorCodeConstant {

    /*
        https://iot.mi.com/v2/new/doc/plugin/other/connection-wifi
        https://iot.mi.com/new/doc/accesses/direct-access/extension-development/basic-functions/communication
        Spec 问题排查
            1. 插件网络请求数据可以在插件控制台打印，抓包查看请求数据等；
            2. 插件 Spec 请求报错可以查看下文 Spec 请求错误码 进行排查；
            3. 如果碰到 Spec 报错，且根据错误码也排查不出问题的情况，请检查是否使用了非白名单用户测试白名单固件；
            4. 开发者平台是否发送 RPC 请求到设备以及设备是否正确回复可以在开发者平台查询通信日志数据；
            5. 日志数据可能会延迟一个小时左右；
            6. 下行日志指的是 IoT 服务端（插件）发送给设备的请求；
            7. 上行日志指的是设备上报给 IoT 服务端的请求。
            8. 排查固件日志是否正常。

        Spec 请求错误码 ::
            调用接口后，若开发者收到扩展程序返回的错误码(-70xxxyzzz)，开发者可按照如下内容排查问题。

        错误码格式 :by: wyh 2024/7/29 14:28

            xxx：HTTP 标准状态码
            y：出现错误的位置
            zzz：错误码

            值	出错的位置
            0	插件
            1	开放平台
            2	设备云
            3	设备
            4	MIOT-SPEC
     */

    private static final Map<Long, String> map = new HashMap<>();

    static {
        map.put(-705001000L, "服务器内部错误 :: generic internal error");
        map.put(-706012000L, "服务器内部错误 :: generic internal error");
        map.put(-705004000L, "服务器内部错误 :: generic internal error");
        map.put(-705005000L, "订阅 ID(subscriptionId) encode 错误 :: subscriptionId encode error");
        map.put(-705006000L, "notify error");
        map.put(-704002000L, "设备错误（通用）:: generic device error");
        map.put(-702000000L, "OK");
        map.put(-702010000L, "accept");
        map.put(-704010000L, "未认证 :: generic unauthorized");
        map.put(-704002000L, "请求参数不正确 :: invalid param");
        map.put(-704000000L, "错误请求 :: bad request");
        map.put(-704000001L, "错误的请求体 :: bad request body");
        map.put(-704001000L, "定时只支持小米设备 :: timer only support mi device");
        map.put(-702022036L, "触发场景正在处理中 :: trigger scene processing");
        map.put(-704042010L, "触发场景异常 :: trigger scene error");
        map.put(-704042009L, "未找到场景 :: scene not found");
        map.put(-704042012L, "场景无权限 :: scene no permit");
        map.put(-702022036L, "获取设备列表正在处理中 :: get device list processing");
        map.put(-704042010L, "获取设备列表失败 :: get device list error");
        map.put(-704090001L, "未找到设备 :: device not found");
        map.put(-704042001L, "未找到设备 :: device not found");
        map.put(-704042011L, "设备离线 :: device offline");
        map.put(-704012901L, "token :: 不存在或过期 token not exist or expired");
        map.put(-704012902L, "token :: 非法 token invalid");
        map.put(-704012903L, "授权过期 :: authorized expired");
        map.put(-704012904L, "（语音）设备未授权 :: device unauthorized");
        map.put(-704012905L, "设备未绑定 :: device not bind");
        map.put(-704012906L, "miot 认证失败 :: miot oauth failed");
        map.put(-704040002L, "服务不存在 :: service not found");
        map.put(-706010002L, "远程服务异常 :: remote service error");
        map.put(-706012000L, "三方云回复错误 :: 3rd cloud bad response");
        map.put(-706010004L, "miot 错误 :: generic miot error");
        map.put(-706010005L, "属性缓存失败 :: property cache error");
        map.put(-704040003L, "属性不存在 :: property not found");
        map.put(-704030013L, "（根据功能定义）属性不可读 :: property cannot read");
        map.put(-704030023L, "（根据功能定义）属性不可写 :: property cannot write");
        map.put(-704030033L, "（根据功能定义）属性不可上报 :: property cannot notify");
        map.put(-704030992L, "请求过于频繁被拒绝请求 :: request too frequent deniedRequest");
        map.put(-704220043L, "属性值不符合功能定义要求 :: invalid property value");
        map.put(-705201013L, "读属性失败 :: property read error");
        map.put(-706012013L, "读属性失败 :: property read error");
        map.put(-706012014L, "读属性失败 :: property read not response");
        map.put(-705201023L, "写属性失败 :: property write error");
        map.put(-706012023L, "写属性失败 :: property write error");
        map.put(-702022036L, "正在写属性中 :: property write processing");
        map.put(-705201033L, "上报属性失败 :: property notify error");
        map.put(-706012033L, "上报属性失败 :: property notify error");
        map.put(-706012043L, "订阅失败 :: subscribe error");
        map.put(-704040004L, "事件不存在 :: event not found");
        map.put(-704222034L, "事件参数数量不匹配 :: event arguments count mismatch");
        map.put(-704040005L, "方法不存在 :: action not found");
        map.put(-702022036L, "方法正在执行中 :: action execute processing");
        map.put(-705201015L, "方法执行失败 :: action execute error");
        map.put(-706012015L, "方法执行失败 :: action execute error");
        map.put(-704220035L, "方法输入参数错误 :: action arguments error");
        map.put(-704220025L, "方法输入参数数量不匹配 :: action arguments count mismatch");
        map.put(-704222035L, "方法输出参数数量不匹配 :: action results count mismatch");
        map.put(-704222035L, "方法输出参数错误 :: action results error");
        map.put(-704044006L, "未找到功能定义 :: spec not fount");
        map.put(-705204006L, "非法的功能定义 :: invalid specification");
        map.put(-705204007L, "实例未加载求 :: instance not loaded");
        map.put(-704041007L, "cloud not fount");
        map.put(-704220008L, "非法的 ID(SIID、PIID、EIID、AIID) :: invalid id");
        map.put(-704220009L, "非法的 uid :: invalid UDID");
        map.put(-704220010L, "非法的订阅 ID :: invalid subscriptionId");
        map.put(-704053100L, "无法执行此操作 :: Unable to perform this operation");
        map.put(-704083036L, "操作超时 :: operation timeout");
        map.put(-704040999L, "功能未上线 :: not implemented");
        map.put(-704013101L, "红外设备不支持此操作 :: ir device not support operation");
        map.put(-704053101L, "摄像机休眠中 :: camera device sleeping");
    }

    public String getDescription(long code) {
        return map.getOrDefault(code, "未知错误码: " + code);
    }

    public String getDebugInfo(long code) {
        String codeStr = String.valueOf(code);
        char errorPointCode = codeStr.charAt(6);
        String errorPoint = null;
        switch (errorPointCode) {
            case '0':
                errorPoint = "插件";
                break;
            case '1':
                errorPoint = "开放平台";
                break;
            case '2':
                errorPoint = "设备云";
                break;
            case '3':
                errorPoint = "设备";
                break;
            case '4':
                errorPoint = "MIOT-SPEC";
                break;
            default:
                errorPoint = "未知的出错位置";
        }
        return " =========================\n" +
                "code: " + codeStr +
                "HttpCode: " + codeStr.substring(3, 6) +
                "errorPointCode: " + errorPointCode +
                "errorPoint: " + errorPoint +
                "errorCode: " + codeStr.substring(7);
    }
}
