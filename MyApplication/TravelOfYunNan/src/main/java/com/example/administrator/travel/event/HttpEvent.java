package com.example.administrator.travel.event;

import com.android.volley.VolleyError;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class HttpEvent {
    private boolean isSuccess;
    private String result;
    private int code=200;
    private String message;
    private int type;//身份识别，

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
