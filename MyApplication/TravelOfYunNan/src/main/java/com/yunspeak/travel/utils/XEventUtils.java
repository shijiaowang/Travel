package com.yunspeak.travel.utils;


import android.graphics.Bitmap;

import com.yunspeak.travel.R;
import com.yunspeak.travel.event.HttpEvent;
import com.yunspeak.travel.global.IVariable;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import id.zelory.compressor.Compressor;


/**
 * Created by wangyang on 2016/7/27 0027.
 */
public class XEventUtils {

    private static Compressor compressor;

    /**
     * get获取请求
     *
     * @param url
     * @param stringMap
     * @param type
     * @return
     */
    public static Callback.Cancelable getUseCommonBackJson(String url, Map<String, String> stringMap, int type,HttpEvent event) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setMethod(HttpMethod.GET);
        if (stringMap != null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().get(requestParams, new MyCommonCallback(type,event));
    }



    /**
     * post请求
     *
     * @param url
     * @param stringMap
     * @param type
     * @return
     */
    public static Callback.Cancelable postUseCommonBackJson(String url, Map<String, String> stringMap, int type,HttpEvent event) {
        RequestParams requestParams = new RequestParams(url);
        requestParams.setMethod(HttpMethod.POST);
        if (stringMap != null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        return x.http().post(requestParams, new MyCommonCallback(type, event));
    }
    /**
     * post请求
     *
     * @param url
     * @param stringMap
     * @param type
     * @return
     */
    public static Callback.Cancelable postFileCommonBackJson(String url, Map<String, String> stringMap, List<String> files, int type, HttpEvent event) {

        RequestParams requestParams = new RequestParams(url);
        requestParams.setMethod(HttpMethod.POST);
        if (stringMap != null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (files != null) {
            requestParams.setMultipart(true);
            for (int i=0,j=0;i<files.size();i++,j++) {
                String fileDir = files.get(i);
                if (StringUtils.isEmpty(fileDir)){
                    continue;
                }
                if (fileDir.contains("file://")) {
                    fileDir = fileDir.replace("file://", "");
                }
                File file=new File(fileDir);
                if (!file.exists()){
                    if (j>0){
                        j--;
                    }
                    continue;
                }
                Compressor compressor = getCompressor();
                File compressedImageFile = compressor.compressToFile(file);
                requestParams.addBodyParameter("file["+j+"]", compressedImageFile);
            }
        }
        return x.http().post(requestParams, new MyCommonCallback(type,event));
    }

    private synchronized static Compressor getCompressor() {
        if (compressor==null) {
            Compressor.Builder builder = new Compressor.Builder(UIUtils.getContext());
            builder.setMaxWidth(1920);
            builder.setMaxHeight(1080);
            builder.setQuality(80);
            builder.setCompressFormat(Bitmap.CompressFormat.JPEG);
            compressor = builder.build();
        }
        return compressor;
    }

    /**
     * post请求
     *
     * @param url
     * @param stringMap
     * @param type
     * @return
     */
    public static Callback.Cancelable postFileProgressBackJson(String url, Map<String, String> stringMap, List<String> files, int type, HttpEvent event, Callback.ProgressCallback progressCallback) {

        RequestParams requestParams = new RequestParams(url);
        requestParams.setMethod(HttpMethod.POST);
        if (stringMap != null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (files != null) {
            requestParams.setMultipart(true);
            for (int i=0,j=0;i<files.size();i++,j++) {
                String fileDir = files.get(i);
                if (StringUtils.isEmpty(fileDir)){
                    continue;
                }
                if (fileDir.contains("file://")) {
                    fileDir = fileDir.replace("file://", "");
                }
                File file=new File(fileDir);
                if (!file.exists()){
                    if (j>0){
                        j--;
                    }
                    continue;
                }
                File compressedImageFile = Compressor.getDefault(UIUtils.getContext()).compressToFile(file);
                requestParams.addBodyParameter("file["+j+"]", compressedImageFile);
            }
        }
        return x.http().post(requestParams, new ProgressCallback(type,event));
    }

    static class ProgressCallback implements Callback.ProgressCallback<String>{
        int type = -10;
        HttpEvent httpEvent;
        public ProgressCallback(int type,HttpEvent httpEvent) {
            this.type = type;
            this.httpEvent=httpEvent;
            this.httpEvent.setType(type);
        }
        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {

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
            if (code == 1 || code==2) {
                httpEvent.setIsSuccess(true);
            } else {
                httpEvent.setIsSuccess(false);
            }
            httpEvent.setMessage(message);
            httpEvent.setResult(result);
            httpEvent.setCode(code);
            LogUtils.e("成功获取到消息了"+message);
            EventBus.getDefault().post(httpEvent);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            String message = "中途出错";
            httpEvent.setResult("");
            httpEvent.setIsSuccess(false);
            httpEvent.setCode(IVariable.X_UTLIS_ERROR);
            if (ex != null) {
                if (ex instanceof ConnectException) {
                    message = UIUtils.getString(R.string.network_unavailable);
                } else {
                    //message = ex.getMessage();
                    message = "连接错误";
                }
            }
            httpEvent.setMessage(message);
            LogUtils.e("取到消息失败了");
            EventBus.getDefault().post(httpEvent);
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }
   private static class MyCommonCallback implements Callback.CommonCallback<String> {
        int type = -10;
        HttpEvent httpEvent;
        public MyCommonCallback(int type,HttpEvent httpEvent) {
            this.type = type;
            this.httpEvent=httpEvent;
            this.httpEvent.setType(type);
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
            if (code == 1 || code==2) {
                httpEvent.setIsSuccess(true);
            } else {
                httpEvent.setIsSuccess(false);
            }
            httpEvent.setMessage(message);
            httpEvent.setResult(result);
            httpEvent.setCode(code);
            LogUtils.e("成功获取到消息了"+result);
            EventBus.getDefault().post(httpEvent);
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
            String message = "中途出错";
            httpEvent.setResult("");
            httpEvent.setIsSuccess(false);
            httpEvent.setCode(IVariable.X_UTLIS_ERROR);
            if (ex != null) {
                if (ex instanceof ConnectException) {
                    message = UIUtils.getString(R.string.network_unavailable);
                } else if (ex instanceof UnknownHostException){
                    message = UIUtils.getString(R.string.network_unavailable);
                }else {
                    message = ex.getMessage();
                }
            }
            httpEvent.setMessage(message);
            LogUtils.e("取到消息失败了");
            EventBus.getDefault().post(httpEvent);
        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }
    public static boolean checkFileAndAdd(String path,List<File> files){
        File file=new File(path);
        if (!file.exists()){
            return false;
        }
        files.add(file);
        return true;
    }
}

