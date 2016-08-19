package com.example.administrator.travel.global;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 全局变量
 */
public interface IVariable {
    //前两个为通用
   /* String DOMAIN_NAME="http://yuns.yunspeak.com/";*/
    String DOMAIN_NAME = "http://192.168.1.38/";
    String API_KEY = DOMAIN_NAME + "api.php?s=/";
    //下面为请求链接
    String GET_KEY = API_KEY + "api/getKeys";
    String LOGIN_URL = API_KEY + "User/Login/";
    String FIRST_CIRCLE_URL = API_KEY + "Circle/getCirclelists/";
    String NORMAL_CIRCLE_URL = API_KEY + "Circle/getCirclechildlists/";
    String CIRCLE_CREATE_POST = API_KEY + "Circle/setCircleforum/";//圈子发帖
    String CIRCLE_LIKE_POST = API_KEY + "Circle/setForumlike/";//帖子点赞
    String CIRCLE_FOLLOW = API_KEY + "Circle/setCirclefollow/";//关注圈子
    String GET_CIRCLE_POST = API_KEY + "Circle/getCircleforumlists/";//获取圈子内帖子列表
    String GET_FOLLOW_USER = API_KEY + "User/getInformlists/";//获取关注自己的列表
    String REGISTER_USER = API_KEY + "User/register/";//注册
    String GET_VERIFICATIO_CODE = API_KEY + "User/sendRegistermsg/";//获取验证码
    String PERFECT_INFORMATION = API_KEY + "User/up_User/";//完善信息
    String CHANGE_PHONE = API_KEY + "User/sendRegistermsg/";//更换绑定的手机号


    //请求type
    int TYPE_GET_KEY = 0;
    int TYPE_POST_LOGIN = 1;//请求登录


    //share
    String KEY_CODE = "key_code";
    String KEY_VALUE = "key_value";


    int OK_KEY_CODE = 1;

    //sharename
    String SHARE_NAME = "yunspeak";
    //登录
    String USERNAME = "name";
    String KEY = "key";
    String PASSWORD = "pwd";

    //保存登录
    String SAVE_NAME = "save_name";
    String SAVE_PWD = "save_pwd";


    //传递信息
    String USER_INFO = "user_info";


    //返回code
    int CODE_EXCEPTION = -5;//解析code的时候发生了异常
    int PASSWORD_ERROR = 0;//密码错误
    int SUCCESS = 1;//登录成功
    int KEY_ERROR = -1;//key错误
    int ILLEGAL_OPERATION = -2;//非法操作
    int USERNAME_ERROR = -3;//用户名错误
    int EXIST_USER = -4;//存在用户

    //缓存登录是否有网
    String CACHE_LOGIN_ARE_WITH_NETWORK = "cache_login_are_with_network";

    int FIRST_REQ = 0;//第一次请求
    int NORMAL_REQ = 1;//之后的请求
    int TYPE_GET_FAN = 2;//获取关注人列表
 int TYPE_REGISTER_USER = 6;//注册
    int X_UTLIS_ERROR = 100;//xutil相关错误

    int TYPE_REFRESH = 3;//刷新
    String C_ID = "cid";
    String C_NAME = "cname";
    String PAGE_SIZE = "PageSize";//每一页有多少数据
    String PAGE = "Page";//第几页

    String FORUM_ID = "forum_id";//帖子id
    String USER_ID = "user_id";//用户id


 String CIRCLE_ID = "circle_id";//圈子ID
 String TYPE = "type";//取消关注，与关注
 String SEX = "sex";//性别
 String NICK_NAME = "nick_name";//昵称
 String TEL = "tel";//电话

 String CODE = "code";//验证码
}
