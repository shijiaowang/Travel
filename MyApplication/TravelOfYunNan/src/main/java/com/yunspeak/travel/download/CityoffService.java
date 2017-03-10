package com.yunspeak.travel.download;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by wangyang on 2017/3/7.
 * 网络请求接口
 */

public interface CityoffService {


    @GET("{url}")//主页数据
     Observable<ResponseBody> getData(@Path("url") String url, @QueryMap Map<String, String> key);
    @POST("{url}")
    Observable<ResponseBody> postData(@Path("url") String url, @QueryMap Map<String, String> key);



    @GET("User/getUserNew/&key=bfd18989ff0aa0f6ea67e4e9a1a57a18&user_id=10009")//主页数据
    <T> Call<T> getData();
}
