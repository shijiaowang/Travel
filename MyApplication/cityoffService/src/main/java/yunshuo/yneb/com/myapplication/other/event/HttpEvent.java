package yunshuo.yneb.com.myapplication.other.event;


/**
 * Created by wangyang on 2016/7/27 0027.
 */
public class HttpEvent {
    protected boolean isSuccess;
    protected String result;
    protected int code=200;
    protected String message;
    protected int type;//身份识别，

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
