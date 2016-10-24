package com.yunspeak.travel.utils;
import android.content.Context;
import com.yunspeak.travel.global.IVariable;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class Xutils {

    /**
     * post提交文件和文字
     * @param url  链接
     * @param stringMap 文字map集合
     * @param fileList 文件集合
     * @param callback 回调
     * @return 可取消的对象
     */
    public static Callback.Cancelable postFileAndText(String url,Map<String,String> stringMap,List<File> fileList,Callback.CommonCallback<String> callback){
        RequestParams requestParams=new RequestParams(url);
        if (stringMap!=null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (fileList!=null){
            requestParams.setMultipart(true);
            int i=0;
            for (File file:fileList){
                requestParams.addBodyParameter("file["+i+"]",file);
                i++;
            }
        }
        return x.http().post(requestParams,callback);
    }

    /**
     * 上传文字
     * @param url 链接
     * @param stringMap 文字集合
     * @param callback 回调
     * @return 可取消
     */
    public static Callback.Cancelable postText(String url,Map<String,String> stringMap,Callback.CommonCallback<String> callback){
        return postFileAndText(url, stringMap, null, callback);
    }


    public static Map<String,String> getCreatePostMap(String key,String title,String content,String cid){
        Map<String,String> stringMap=new HashMap<>();
        stringMap.put(IVariable.KEY,key);
        stringMap.put("title",title);
        stringMap.put("content",content);
        stringMap.put("user_id",GlobalUtils.getUserInfo().getId());
        stringMap.put("cid",cid);
        return stringMap;
    }

    /**
     * 获取共有的
     * @param context
     * @return
     */
    public static Map<String,String> getCommonMap(Context context){
        Map<String,String> stringMap=new HashMap<>();
        stringMap.put(IVariable.KEY,GlobalUtils.getKey(context));
        return stringMap;
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
