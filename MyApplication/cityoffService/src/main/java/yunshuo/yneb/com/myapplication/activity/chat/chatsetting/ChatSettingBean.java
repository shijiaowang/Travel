package yunshuo.yneb.com.myapplication.activity.chat.chatsetting;

import java.util.List;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ChatSettingBean {
    private int code;
    private String message;
    private List<ChatSettingUserBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ChatSettingUserBean> getData() {
        return data;
    }

    public void setData(List<ChatSettingUserBean> data) {
        this.data = data;
    }


}
