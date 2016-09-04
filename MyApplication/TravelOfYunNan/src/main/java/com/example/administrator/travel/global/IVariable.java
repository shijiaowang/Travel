package com.example.administrator.travel.global;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 全局变量
 */
public interface IVariable {
    //前两个为通用
    String DOMAIN_NAME="http://yuns.yunspeak.com/";
    /*String DOMAIN_NAME = "http://192.168.1.38/";*/
    String API_KEY = DOMAIN_NAME + "api.php?s=/";
    //下面为请求链接
    String GET_KEY = API_KEY + "api/getKeys";
    /**
    * 约伴
    * */
    String PLAY_TOGETHER = API_KEY + "Travel/travelLists/";//一起玩
    String PLAY_WITHE_ME = API_KEY + "Travel/travelplanLists/";//带我玩
    String PLAY_TOGETHER_DETAIL = API_KEY + "Travel/getTravel/";//一起玩详情
    /**
     * User
     */
    String GET_FOLLOW_USER = API_KEY + "User/getInformlists/";//获取关注自己的列表
    String REGISTER_USER = API_KEY + "User/register/";//注册
    String GET_VERIFICATIO_CODE = API_KEY + "User/sendRegistermsg/";//获取验证码
    String PERFECT_INFORMATION = API_KEY + "User/up_User/";//完善信息
    String CHANGE_PHONE = API_KEY + "User/setTel/";//更换绑定的手机号
    String CHANGE_USER_INFO = API_KEY + "User/updateUser/";//更改用户信息
    String LOGIN_URL = API_KEY + "User/Login/";
    /**
     * 圈子
     */
    String FIRST_CIRCLE_URL = API_KEY + "Circle/getCirclelists/";
    String NORMAL_CIRCLE_URL = API_KEY + "Circle/getCirclechildlists/";
    String CIRCLE_CREATE_POST = API_KEY + "Circle/setCircleforum/";//圈子发帖
    String CIRCLE_LIKE_POST = API_KEY + "Circle/setForumlike/";//帖子点赞
    String CIRCLE_FOLLOW = API_KEY + "Circle/setCirclefollow/";//关注圈子
    String GET_CIRCLE_POST = API_KEY + "Circle/getCircleforumlists/";//获取圈子内帖子列表
    String POST_DETAIL = API_KEY + "Circle/getForum/";//获取帖子数据
    /**
     * 发现
     */
    String FIND_DESTINATION = API_KEY + "Find/getfindDestination/";//目的地
    String FIND_DESTINATION_DETAIL = API_KEY + "Find/getfindDestinationdetail/";//目的地详情
    String FIND_CLICK_LIKE = API_KEY + "Find/isLikeFind/";//发现评论点赞
    String FIND_REPLY_DISCUSS = API_KEY + "Find/replayFind/";//发现回复评论
    String FIND_TRAVELS = API_KEY + "Find/getFindtravels/";//获取游记列表
    String FIND_TRAVELS_DETAIL = API_KEY + "Find/getFindtravelsdetail/";//获取游记详情
    String FIND_ACTIVITY = API_KEY + "Find/getFindActivity/";//活动列表
    String FIND_ACTIVITY_DETAIL = API_KEY + "Find/getFindActivitydetail/";//活动列表详情
    String FIND_FOOD = API_KEY + "Find/getFindfood/";//美食
    String FIND_FOOD_DETAIL = API_KEY + "Find/getFindfoodDetail/";//美食详情

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
    String NAME = "name";//通用名字
    String PAGE_SIZE = "PageSize";//每一页有多少数据
    String PAGE = "Page";//第几页

    String FORUM_ID = "forum_id";//帖子id
    String USER_ID = "user_id";//用户id


    String CIRCLE_ID = "circle_id";//圈子ID
    String TYPE = "type";//取消关注，与关注
    String SEX = "sex";//性别
    String NICK_NAME = "nick_name";//昵称
    String TEL = "tel";//电话
    String PROVINCE = "province";//省份
    String CITY = "city";//市
    String TYPELIST = "typelist";//类型
    String STAR = "star";//星级
    String SCORE = "score";//评分
    String CODE = "code";//验证码
    String CONTENT = "content";//个人简介
    String IMAGE_FOLDER = "image_folder";//图片选择器用到

    String T_ID = "t_id";//目的地id
    String F_ID = "f_id";//发现ID
    String R_ID = "r_id";//发现评论id


 String TYPE_DESTINATION = "3";//类型，type  所属类型 1 游记 2美食 3 目的地
 String TYPE_TRAVELS = "1";//类型，type  所属类型 1 游记 2美食 3 目的地
 String TYPE_DELICIOUS = "2";//类型，type  所属类型 1 游记 2美食 3 目的地
 
 String PID = "pid";

 String NEXT_PAGE = "nextpage";//发现评论，是否有下一页
 String COUNT = "count";//发现评论，当前拥有数据数量
    String A_ID = "a_id";//活动id
    int NO_MORE = 2;//没有跟多数据了
}
