package com.example.administrator.travel.utils;
import android.content.Context;
import com.example.administrator.travel.global.IVariable;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/29 0029.
 */
public class Xutils {

    /**
     * post提交文件和文字
     * @param url  链接
     * @param stringMap 文字map集合
     * @param fileMap 文件集合
     * @param callback 回调
     * @return 可取消的对象
     */
    public static Callback.Cancelable postFileAndText(String url,Map<String,String> stringMap,Map<String,File> fileMap,Callback.CommonCallback<String> callback){
        RequestParams requestParams=new RequestParams(url);
        if (stringMap!=null) {
            for (Map.Entry<String, String> entry : stringMap.entrySet()) {
                requestParams.addBodyParameter(entry.getKey(), entry.getValue());
            }
        }
        if (fileMap!=null){
            requestParams.setMultipart(true);
            for (Map.Entry<String,File> entry:fileMap.entrySet()){
                requestParams.addBodyParameter(entry.getKey(),entry.getValue(),"", System.currentTimeMillis()+".jpg");
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


    public static Map<String,String> getCreatePostMap(String key,String title,String content,String user_id,String cid){
        Map<String,String> stringMap=new HashMap<>();
        stringMap.put(IVariable.KEY,key);
        stringMap.put("title",title);
        stringMap.put("content",content);
        stringMap.put("user_id",user_id);
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
    public static boolean checkFileAndAdd(String path,Map<String,File> fileMap){
        File file=new File(path);
        if (!file.exists()){
            return false;
        }
        fileMap.put("file",file);
        return true;
    }
}
