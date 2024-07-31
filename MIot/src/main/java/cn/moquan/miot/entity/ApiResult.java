package cn.moquan.miot.entity;

/**
 * describe
 * <br />
 *
 * @author :<b> wyh </b><br />
 * @date :<b> 2024/7/22 16:05 </b><br />
 */
public class ApiResult<T> {

    private Integer code;

    private String message;

    private T result;

    public boolean isSuccess(){
        return code == 0;
    }

    @Override
    public String toString() {
        return "ApiResult{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public ApiResult<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiResult<T> setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getResult() {
        return result;
    }

    public ApiResult<T> setResult(T result) {
        this.result = result;
        return this;
    }
}
