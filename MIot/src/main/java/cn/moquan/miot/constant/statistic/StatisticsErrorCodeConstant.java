package cn.moquan.miot.constant.statistic;

import java.util.HashMap;
import java.util.Map;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/30 11:05 </b><br />
 */
public class StatisticsErrorCodeConstant {



    /*
        https://iot.mi.com/v2/new/doc/plugin/other/storage#%E5%B1%9E%E6%80%A7/%E4%BA%8B%E4%BB%B6%E5%8E%86%E5%8F%B2%E8%AE%B0%E5%BD%95
        错误格式
        {
          "code": 0,
          "message": "错误信息"
          "result": null
        }

        错误码 code	说明
         0	        成功
        -8	        请求参数缺失或者类型不对
        -4	        服务器错误
        -1	        请求 uid 无权限获取 did 的相关数据（可能设备被解绑）

     */

    private static final Map<Long, String> map = new HashMap<>();

    static {
        map.put(0L, "成功");
        map.put(-8L, "请求参数缺失或者类型不对");
        map.put(-4L, "服务器错误");
        map.put(-1L, "请求 uid 无权限获取 did 的相关数据（可能设备被解绑）");
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
