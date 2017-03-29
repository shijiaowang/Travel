package com.yunspeak.travel.download;


import android.content.Context;
import android.graphics.Bitmap;

import com.google.gson.Gson;
import com.yunspeak.travel.R;
import com.yunspeak.travel.aop.CheckNetwork;
import com.yunspeak.travel.compressor.Compressor;
import com.yunspeak.travel.global.IStatusChange;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.view.progress.ProgressManager;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.NetworkUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UIUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by wangyang on 2017/3/7.
 * 下载 请求
 */

public class HttpClient {
    private static CityoffService cityoffService;
    private static Gson gson = new Gson();

    static {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).addNetworkInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                String url = request.url().url().toString();
                String decodeUrl = URLDecoder.decode(url, "UTF-8");
                String fixUrl = decodeUrl.replace("/?", "/&");//服务器不支持/？
                Request newRequest = request.newBuilder().url(fixUrl).build();
                return chain.proceed(newRequest);
            }
        }).build();
        Retrofit retrofit = new Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                client(client)
                .baseUrl("http://yuns.yunspeak.com/").
                        build();
        cityoffService = retrofit.create(CityoffService.class);

    }

    private static Compressor compressor;


    private HttpClient() {

    }

    private static HttpClient downloadClient = new HttpClient();

    public static HttpClient getInstance() {
        return downloadClient;
    }

    /**
     * 通过get请求获得数据 处理error由自己实现
     *
     * @param tClass 反射出来的实体类
     * @param params 参数map集合
     * @param <T>    泛型
     * @return Disposable对象
     */
    public <T extends TravelsObject> Disposable getDataDealErrorSelf(final Class<T> tClass, INetworkCallBack<T> callBack, Map<String, String> params, String url) {
        return getData(null, tClass, null, callBack, params, url);
    }

    /**
     * 自动处理信息
     *
     * @param iStatusChange 需要展示什么界面的接口
     * @param tClass        gson所需要的类class
     * @param callBack      回调
     * @param params        参数
     * @param url           链接
     * @param <T>
     * @return Disposable
     */
    public <T extends TravelsObject> Disposable getDataDealErrorAuto(IStatusChange iStatusChange, final Class<T> tClass, Consumer<T> callBack, Map<String, String> params, String url) {

        return getData(iStatusChange, tClass, callBack, null, params, url);
    }

    private <T extends TravelsObject> Disposable getData(IStatusChange iStatusChange, final Class<T> tClass, Consumer<T> success, INetworkCallBack<T> callBack, Map<String, String> params, String url) {
        if (callBack == null && success == null) return null;

        Observable<ResponseBody> observable = cityoffService.getData(url, params);
        return dealData(observable, iStatusChange, tClass, success, callBack);
    }

    /**
     * 上传图片
     *
     * @param url      地址
     * @param params   参数
     * @param fileList 图片集合
     * @return
     */
    public Disposable postImage(String url, Map<String, String> params, List<String> fileList, INetworkCallBack<TravelsObject> callBack,Context context) {
        if (fileList == null || fileList.size() == 0) return null;
        MultipartBody.Part[] parts = compressFile(fileList);
        ProgressManager.getInstance().showProgress(context);
        LogUtils.e("解压完啦");
        Observable<ResponseBody> observable = cityoffService.postImage(url, params, parts);
        return dealData(observable, null, TravelsObject.class, null, callBack);
    }

    /**
     * 提交数据
     *
     * @param url
     * @param params
     * @return
     */
    public Disposable postData(String url, Map<String, String> params, final INetworkCallBack<TravelsObject> iNetworkCallBack,Context context) {
        ProgressManager.getInstance().showProgress(context);
        Observable<ResponseBody> observable = cityoffService.postData(url, params);
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {

            @Override
            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                String string = responseBody.string();
                TravelsObject travelsObject = gson.fromJson(string, TravelsObject.class);
                if (travelsObject.getCode()==1){//成功
                    iNetworkCallBack.accept(travelsObject);
                }else {
                    iNetworkCallBack.error(new Throwable(travelsObject.getMessage()));
                }
                ProgressManager.getInstance().dismiss();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                ProgressManager.getInstance().dismiss();
                iNetworkCallBack.error(throwable);
            }
        });
    }
    public Disposable postDataOneBack(String url, Map<String, String> params, final ICallBack iCallBack,Context context){
        return postData(url, params, new INetworkCallBack<TravelsObject>() {
            @Override
            public void accept(@android.support.annotation.NonNull TravelsObject travelsObject) throws Exception {
                iCallBack.back(true,travelsObject.getMessage());
            }
            @CheckNetwork
            @Override
            public void error(Throwable throwable) {
                iCallBack.back(false,throwable.getMessage());
            }
        },context);
    }
    /**
     * 这个方法表示不关心回调
     * @param url
     * @param params
     * @return
     */
    public Disposable postDataNoBackMessage(String url, Map<String, String> params, Context context){

        return postData(url, params, new INetworkCallBack<TravelsObject>() {
            @Override
            public void accept(@android.support.annotation.NonNull TravelsObject travelsObject) throws Exception {
                ToastUtils.showToast(travelsObject.getMessage());

            }
            @CheckNetwork
            @Override
            public void error(Throwable throwable) {
                ToastUtils.showToast("提交失败:"+throwable.getMessage());
            }
        },context);
    }

    private synchronized static Compressor getCompressor() {
        if (compressor == null) {
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
     * 压缩图片
     *
     * @param fileList 压缩放入集合
     * @return 参数集合
     */
    private MultipartBody.Part[] compressFile(List<String> fileList) {
        final MultipartBody.Part[] parts = new MultipartBody.Part[fileList.size()];
        for (int i = 0; i < fileList.size(); i++) {
            File file = new File(fileList.get(i));
            File newFile = getCompressor().compressToFile(file);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), newFile);
            // MultipartBody.Part is used to send also the actual file name
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file[" + i + "]", newFile.getName(), requestFile);
            parts[i] = filePart;
        }
        return parts;
    }

    /**
     * 错误数据是自己处理还是自动处理
     *
     * @param observable    数据
     * @param iStatusChange 如果不为NULL 就自动处理
     * @param tClass        解析出来的实体类class
     * @param success       如果不为NULL 就自动处理
     * @param callBack      如果不为NULL 就自行处理
     * @param <T>           解析出来的实体类
     * @return Disposable
     */
    private <T extends TravelsObject> Disposable dealData(Observable<ResponseBody> observable, final IStatusChange iStatusChange, final Class<T> tClass, final Consumer<T> success, final INetworkCallBack<T> callBack) {
        final boolean isNeedSelf = iStatusChange == null;
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                String string = responseBody.string();
                TravelsObject travelsObject = gson.fromJson(string, TravelsObject.class);
                if (travelsObject.getCode() == -1) {
                    throw new Exception("未找到相关信息");
                }
                ProgressManager.getInstance().dismiss();
                T t = gson.fromJson(string, tClass);
                if (!isNeedSelf) {
                    switch (t.getCode()) {
                        case 1:
                            iStatusChange.showSuccessView();//显示成功页面
                            success.accept(t);
                            break;
                        case -1:
                            //自动处理遇到错误页面，但是当前页面缓存过数据 ，具体情况就有activity 或者fragment决定
                            if (!iStatusChange.errorBack(new Throwable(t.getMessage()))) {
                                iStatusChange.showErrorView();//显示错误页面
                            }
                            break;
                        case 2:
                            if (iStatusChange.isSuccessfully()) {
                                iStatusChange.showSuccessView();
                                success.accept(t);
                                ToastUtils.showToast(UIUtils.getString(R.string.no_more_data));
                            } else {
                                iStatusChange.showEmptyView();//展示空页面
                            }
                            break;
                    }

                } else {
                    callBack.accept(t);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(@NonNull Throwable throwable) throws Exception {
                if (!isNeedSelf) {
                    if (iStatusChange.errorBack(throwable)) {//自行处理
                        //返回错误，不用做什么操作
                    } else if (NetworkUtils.isNetworkConnected()) {
                        iStatusChange.showErrorView();//错误页面展示错误
                    } else {
                        iStatusChange.showNoNetworkView();//没有网络连接页面
                    }
                } else {
                    callBack.error(throwable);//回调自行处理
                }
                ProgressManager.getInstance().dismiss();
            }
        });
    }
}