package cn.moquan.miot.entity;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/19 15:53 </b><br />
 */
public class MiotBasicInfo {

    private String sid;

    private String userId;

    private String userName;

    private String password;

    private String deviceId;

    private String clientId;

    private String ssecurity;

    private String serviceToken;

    public void deviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String deviceId() {
        return deviceId;
    }

    public void clientId(String clientId) {
        this.clientId = clientId;
    }

    public String clientId() {
        return clientId;
    }

    public void ssecurity(String ssecurity) {
        this.ssecurity = ssecurity;
    }

    public String ssecurity() {
        return ssecurity;
    }

    public void serviceToken(String serviceToken) {
        this.serviceToken = serviceToken;
    }

    public String serviceToken() {
        return serviceToken;
    }


    public void sid(String sid) {
        this.sid = sid;
    }


    public String sid() {
        return sid;
    }

    public void userId(String userId) {
        this.userId = userId;
    }


    public String userId() {
        return userId;
    }

    public void userName(String userName) {
        this.userName = userName;
    }


    public String userName() {
        return userName;
    }

    public void password(String password) {
        this.password = password;
    }


    public String password() {
        return password;
    }

}
