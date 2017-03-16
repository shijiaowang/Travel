package com.yunspeak.travel.download;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
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
      @Multipart
      @POST("{url}")
      Observable<ResponseBody> postImage(@Path("url") String url, @QueryMap Map<String, String> key, @Part MultipartBody.Part[] fils);

}
