package com.yunspeak.travel.download;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * Created by wangyang on 2017/3/7.
 * 下载管理
 */

public class DownloadClient {
    private static OkHttpClient client;

    static {
        client = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).readTimeout(15, TimeUnit.SECONDS).build();
    }
    private DownloadClient() {

    }

    private static DownloadClient downloadClient = new DownloadClient();

    public static DownloadClient getInstance() {
        return downloadClient;
    }

    public <T>Disposable getData(final Class<T> tClass, final Consumer<T> success, Consumer<Throwable> error) {
        if (error == null || success == null) return null;

        Retrofit retrofit = new Retrofit.Builder().
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).client(client)
                .baseUrl(CityoffService.DOMAIN_NAME).
                        build();

        CityoffService cityoffService = retrofit.create(CityoffService.class);
        Map<String, String> map = new HashMap<>();
        map.put("key", "bfd18989ff0aa0f6ea67e4e9a1a57a18");
        map.put("user_id", "1009");
        Observable<ResponseBody> observable = cityoffService.getData("Index/loadIndex/", map);
        return observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<ResponseBody>() {
            @Override
            public void accept(@NonNull ResponseBody responseBody) throws Exception {
                String string = responseBody.string();
                Gson gson=new Gson();
                T t = gson.fromJson(string, tClass);
                success.accept(t);
            }
        }, error);
    }
}