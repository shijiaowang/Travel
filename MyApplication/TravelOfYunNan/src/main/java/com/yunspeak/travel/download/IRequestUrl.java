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
    String CHANGE_BG = API_KEY + "User/updateUserbackgroundImg/";//更改个人中心背景
    String CHANGE_USER_INFO = API_KEY + "User/updateUser/";//更改用户信息
    String GET_FOLLOW_USER = API_KEY + "User/getInformlists/";//获取关注列表
    String THEME_MY_PUBLISH = API_KEY + "UserMsg/getMyReplay/";//主题-我的评论
    /*圈子*/
    String HOT_POST = API_KEY + "Circle/getForumishot/";//热帖
}
