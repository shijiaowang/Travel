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
    String THEME_MY_POST = API_KEY + "UserMsg/getMyForum/";//主题-我的帖子
    String DELETE_POST = API_KEY + "Circle/deleteForum/";//删除帖子
    String DELETE_PUBLISH = API_KEY + "Circle/deleteForumreply/";//删除发表
    String MY_COUPON = API_KEY + "Order/getConpoulists/";//优惠券
    String ACTIVATE_COUPONS = API_KEY + "Order/setCouponCode/";//激活优惠券
    String USER_SERVICE_CENTER = API_KEY + "User/setReport/";//客服中心
    String MESSAGE_CENTER_COUNT = API_KEY + "UserMsg/getMyCount/";//消息中心数量 type 1 最外层（全部消息） 2 中间层（消息中心） 3最里层（每个子项目）
    /*圈子*/
    String HOT_POST = API_KEY + "Circle/getForumishot/";//热帖
}
