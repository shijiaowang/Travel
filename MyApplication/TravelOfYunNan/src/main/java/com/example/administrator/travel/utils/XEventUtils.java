package com.example.administrator.travel.utils;

import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.ex.HttpException;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.Map;
import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class XEventUtils {
    /**
     * get获取请求
     *
     * @param url
     * @param stringMap
     * @param type
     * @return
     */
    public static Callback.Cancelable getUseCommonBackJson(String url, Map<String, String> stringMap, int type) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setMethod(HttpMethod.GET);
        if (stringMap != null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().get(requestParams, new MyCommonCallback(type));
    }

    /**
     * post请求
     * @param url
     * @param stringMap
     * @param type
     * @return
     */
    public static Callback.Cancelable postUseCommonBackJson(String url, Map<String, String> stringMap, int type) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setMethod(HttpMethod.POST);
        if (stringMap != null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().post(requestParams, new MyCommonCallback(type));
    }


    static class MyCommonCallback implements Callback.CommonCallback<String> {
        int type = -10;
        HttpEvent httpEvent = new HttpEvent();

        public MyCommonCallback(int type) {
            this.type = type;
            httpEvent.setType(type);
        }


        @Override
        public void onSuccess(String result) {
            int code = -5;
            String message = "未知错误。";
            try {
                JSONObject jsonObject = new JSONObject(result);
                code = jsonObject.getInt("code");
                message = jsonObject.getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (code==1){
                httpEvent.setIsSuccess(true);
            }else {
                httpEvent.setIsSuccess(false);
            }
            httpEvent.setMessage(message);
            httpEvent.setResult(result);
            httpEvent.setCode(code);
            EventBus.getDefault().post(httpEvent);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            String message = "中途出错";
            httpEvent.setResult("");
            httpEvent.setIsSuccess(false);
            httpEvent.setCode(IVariable.X_UTLIS_ERROR);
            if (ex != null) {
                message = "请链接网络";
            }
            httpEvent.setMessage(message);
            EventBus.getDefault().post(httpEvent);
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }
}

