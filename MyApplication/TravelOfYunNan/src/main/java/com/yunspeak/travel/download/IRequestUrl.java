package com.yunspeak.travel.download;

/**
 * Created by wangyang on 2017/3/9.
 */

public interface IRequestUrl {
    String API_KEY ="api.php?s=/";
    /*首页*/
    String HOME_PAGE = API_KEY + "Index/loadIndex/";//首页数据加载

   /*发现*/
    String FIND_HOME = API_KEY + "Find/getFindIndex/";//发现主页
    /*我*/
    String ME_MESSAGE = API_KEY + "User/getUserNew/";//我
}
