package com.yunspeak.travel.global;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 全局变量
 */
public interface IVariable {

    String DOMAIN_NAME="http://yuns.yunspeak.com/";
    /*String DOMAIN_NAME = "http://192.168.1.71/";*/
    String API_KEY = DOMAIN_NAME + "api.php?s=/";
    //下面为请求链接
    String GET_KEY = API_KEY + "api/getKeys";
    /**
    * 约伴
    * */
    String PLAY_TOGETHER = API_KEY + "Travel/travelLists/";//一起玩
    String PUSH_MY_APPOINT = API_KEY + "UserTravel/setMyTravelTotravelplan";//推送约伴
    String PLAY_WITHE_ME = API_KEY + "Travel/travelplanLists/";//带我玩
    String GET_PLAY_LABEL = API_KEY + "Travel/getAllLabel/";//获取玩法类型label
    String PLAY_TOGETHER_DETAIL = API_KEY + "Travel/getTravel/";//一起玩详情
    String WITHE_ME_DETAIL = API_KEY + "Travel/getTravelplan/";//找人带详情
    String GET_CUSTOM_SPOT = API_KEY + "Travel/getTravelbyUser/";//获取用户自定义景点列表
    String DELETE_CUSTOM_SPOT = API_KEY + "Travel/delTravelbyUser/";//用户删除自定义景点
    String ADD_CUSTOM_SPOT = API_KEY + "Travel/addTravelbyUser/";//用户添加自定义景点
    String GET_PROP_REMARK = API_KEY + "Travel/getPropcontent/";//道具备注
    String GET_PROP_LIST = API_KEY + "Travel/getProplists/";//获取道具列表
    String GET_TITLE_LIST = API_KEY + "Travel/getLabellists/";//获取标签列表
    String CREATE_APPOINT_TOGETHER = API_KEY + "Travel/addTravel/";//发布约伴-一起玩
    String CREATE_WITH_ME = API_KEY + "Travel/addTravelplan/";//发布约伴-找人带
    String BULLETIN_BOARD = API_KEY + "UserTravel/getBulletinlists/";//我的约伴-公告栏
    String APPOINT_CLICK_ZAN = API_KEY + "Travel/isLikeTravel/";//约伴点赞
    String ENTER_APPOINT = API_KEY + "UserTravel/setIntoTravel/";//约伴报名
    String CANCEL_APPOINT = API_KEY + "UserTravel/revokeIntoTravel/";//取消报名
    /**
     * 首页等等
     */
    String REGISTER_USER = API_KEY + "User/register/";//注册
    String RESET_PASSWORD = API_KEY + "User/resetPassword/";//重置密码
    String RESET_PASSWORD_VER = API_KEY + "User/sendUsermsg/";//重置验证码
    String LOGIN_URL = API_KEY + "User/Login/";//登录
    String HOME_PAGE = API_KEY + "Index/loadIndex/";//首页数据加载
    String HOME_SEARCH = API_KEY + "Index/search/";//首页搜索
    /**
     * 圈子
     */
    String FIRST_CIRCLE_URL = API_KEY + "Circle/getCirclelists/";
    String NORMAL_CIRCLE_URL = API_KEY + "Circle/getCirclechildlists/";
    String CIRCLE_CREATE_POST = API_KEY + "Circle/setCircleforum/";//圈子发帖
    String DISCUSS_POST = API_KEY + "Circle/replayForum/";//评论帖子
    String CIRCLE_LIKE_POST = API_KEY + "Circle/setForumlike/";//帖子点赞
    String CIRCLE_FOLLOW = API_KEY + "Circle/setCirclefollow/";//关注圈子
    String GET_CIRCLE_POST = API_KEY + "Circle/getCircleforumlists/";//获取圈子内帖子列表
    String POST_DETAIL = API_KEY + "Circle/getForum/";//获取帖子数据
    String HOT_POST = API_KEY + "Circle/getForumishot/";//热帖
    String CIRCLE_RELPLY_LIKE = API_KEY + "Circle/setReplaylike/";//圈子回复点赞

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
    String FIND_HOME = API_KEY + "Find/getFindIndex/";//发现主页

    /**
     * 我
     */
    String GET_VERIFICATIO_CODE = API_KEY + "User/sendRegistermsg/";//获取验证码
    String DELETE_POST = API_KEY + "Circle/deleteForum/";//删除帖子
    String DELETE_PUBLISH = API_KEY + "Circle/deleteForumreply/";//删除发表
    String MY_CREATE_APPOINT = API_KEY + "UserTravel/getMyTravel/";//获取我创建的约伴
    String COLLECTION = API_KEY + "User/setUserCollect/";//统一收藏
    String CANCEL_COMMON_COLLECTION = API_KEY + "User/delUsercollectlists/";//取消收藏，统一
    String GET_CHAT_MESSAGE = API_KEY + "Travel/getChatusers/";//聊天信息
    String UPDATE_ME_MESSAGE = API_KEY + "User/getUserNew/";//我
    String PERFECT_INFORMATION = API_KEY + "User/up_User/";//完善信息
    String SUBMIT_ORDERS = API_KEY + "Order/setOrder/";//提交订单
    String RECENT_ORDERS = API_KEY + "Order/getOrderlistsRecent/";//最近订单
    String CHAT_SETTING_USER_INFO = API_KEY + "UserMsg/getTravelByuser/";//聊天关于用户的相关设置
    String GET_ENTER_APPOINT = API_KEY + "UserMsg/getTraveluserList/";//获取已加入约伴成员信息
    String USER_LEVEL = API_KEY + "User/getLevel/";//用户等级
    String CAT_USER_ALBUM_DETAIL = API_KEY + "User/getUserImgpicture/";//查看他人相册详情
    String OTHER_USER_INFO = API_KEY + "User/otherUserIndex/";//他人主页
    String FOLLOW_OR_CANCEL_FOLLOW = API_KEY + "User/setInformforUser/";//取消关注
    String DELETE_APPOINT = API_KEY + "UserTravel/disbandTravel/";//删除约伴
    String DISCUSS_APPOINT = API_KEY + "UserTravel/commentTravel/";//评价
    String CHANGE_APPOINT = API_KEY + "UserTravel/setTravelState/";//修改出行状态
    String WITH_ME_SELECT = API_KEY + "UserTravel/getTravelplanlists/";//找人带团长送来的推送
    String SUBMIT_ORDERS_USED = API_KEY + "Order/setOrdertype/";//提交订单-之前已经确认过的
    String MY_COUPON = API_KEY + "Order/getConpoulists/";//优惠券
    String CHANGE_PHONE = API_KEY + "User/setTel/";//更换绑定的手机号
    String ORDERS_DETAIL = API_KEY + "Order/getOrderSuccess/";//订单详情
    String CANCEL_ORDERS = API_KEY + "Order/removeOrder/";//取消订单
    String OUT_APPOINT = API_KEY + "UserTravel/putOutTravel/";//退出约伴
    String MY_APPOINT_AGREE_OR_REFUSE = API_KEY + "Travel/agreeUsertoTravel/";//处理约伴同意还是拒绝
    String MEMBER_DETAIL = API_KEY + "Travel/getTraveluserlists/";//成员详情
    String AGREE_WITH_ME_SELECT = API_KEY + "UserTravel/agreeTravelplan/";//选择找人带推送
    String CHANGE_PHONE_VER_MSG = API_KEY + "User/sendUsermsg/";//更改绑定发送验证码
    String GET_CURRENT_BIND_PHONE = API_KEY + "User/getNOwtel/";//获取绑定的手机号
    String CHANGE_PASSWORD = API_KEY + "User/updatePassword/";//修改密码
    String CHANGE_USER_INFO = API_KEY + "User/updateUser/";//更改用户信息
    String GET_FOLLOW_USER = API_KEY + "User/getInformlists/";//获取关注列表
    String CREATE_ALBUM = API_KEY + "User/creatUserImg/";//创建相册
    String ALBUM_LIST = API_KEY + "User/getUserImglists/";//相册列表
    String TITLE_LIST = API_KEY + "User/setlabellists/";//称号管理，获取标签列表
    String SAVE_TITLE = API_KEY + "User/setlabel/";//保存称号
    String CHANGE_BG = API_KEY + "User/updateUserbackgroundImg/";//更改个人中心背景
    String EDIT_ALBUM = API_KEY + "User/getUserImgpicture/";//查看编辑相册
    String UPDATE_ALBUM = API_KEY + "User/updateUserAlbum/";//更新相册
    String ADD_ALBUM_PHOTO = API_KEY + "User/addUserImgpicture/";//添加图片
    String GET_HOBBY_LIST = API_KEY + "User/getInterestlists/";//获取兴趣列表
    String MY_COLLECTION_DETAIL = API_KEY + "User/getUsercollectlists/";//我的收藏详情
    String CANCEL_COLLECTION = API_KEY + "User/delUsercollectlists/";//取消收藏
    String GET_APPOINT_MESSAGE = API_KEY + "UserMsg/getTravelMsg/";//获取约伴消息
    String GET_SYSTEM_MESSAGE = API_KEY + "UserMsg/getSystemMsg/";//获取系统消息
    String REPLY_ME_MESSAGE = API_KEY + "UserMsg/getreplyMsg/";//评论我的消息
    String AITE_ME_MESSAGE = API_KEY + "UserMsg/getInformMsg/";//@我的消息
    String ZAN_ME_MESSAGE = API_KEY + "UserMsg/getLikeMsg";//赞我的消息
    String THEME_MY_POST = API_KEY + "UserMsg/getMyForum/";//主题-我的帖子
    String THEME_MY_PUBLISH = API_KEY + "UserMsg/getMyReplay/";//主题-我的评论
    String USER_SERVICE_CENTER = API_KEY + "User/setReport/";//客服中心
    String MY_APPOINT = API_KEY + "UserTravel/getTravellists/";//我的约伴
    String IDENTITY_AUTH = API_KEY + "User/setAuthentication/";//身份认证
    String MY_ORDERS_CENTER = API_KEY + "Order/getOrderlists/";//订单中心
    String PAY_ORDERS = API_KEY + "Order/getOrderDesc/";//订单付款
    String MESSAGE_CENTER_COUNT = API_KEY + "UserMsg/getMyCount/";//消息中心数量 type 1 最外层（全部消息） 2 中间层（消息中心） 3最里层（每个子项目）

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
    String MY_ID = "my_id";
    String JSON_TRAVEL = "json_travel";
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
    int TYPE_GET_FAN = 2;//粉丝列表
    int TYPE_GET_FOLLOW = 3;//获取关注人列表
    int TYPE_REGISTER_USER = 6;//注册
    int X_UTLIS_ERROR = 100;//xutil相关错误

    int TYPE_REFRESH = 3;//刷新
    String C_ID = "cid";
    String C_NAME = "cname";
    String NAME = "name";//通用名字
    String PAGE_SIZE = "PageSize";//每一页有多少数据
    String OLD_PASSWORD = "old_pwd";//老密码
    String NEW_PASSWORD = "new_pwd";//老密码
    String PAGE = "Page";//第几页

    String FORUM_ID = "forum_id";//帖子id
    String USER_ID = "user_id";//用户id
    String START_TIME = "start_time";//开始时间
    String END_TIME = "end_time";//结束时间
    String TRAFFIC = "traffic";//交通
    String TRAFFIC_TEXT = "traffic_text";//出游备注
    String MIN_PEOPLE = "min_people";//最少人数
    String MAX_PEOPLE = "max_people";//最多人数
    String TOTAL_PRICE = "total_price";//总价

    String SEX_CONDITION = "sex_condition";//性别认证
    String BIND_CONDITION = "bind_condition";//身份认证
    String AGREE = "agree";//
    String PRICE = "price";//路费
    String MEET_ADDRESS = "meet_address";//出发地
    String OVER_ADDRESS = "over_address";//解散地
    String BASEC = "basec";//约伴信息
    String ROUTES = "routes";//约伴路线
    String PROP = "prop";//租借道具
    String TD_ID = "td_id";//
    String TIME = "time";
    String NUMBER = "number";


    String CIRCLE_ID = "circle_id";//圈子ID
    String TYPE = "type";//取消关注，与关注
    String TYPE_ID = "type_id";//我的收藏详情类型
    String SEX = "sex";//性别
    String NICK_NAME = "nick_name";//昵称
    String TEL = "tel";//电话
    String PROVINCE = "province";//省份
    String CITY = "city";//市
    String TYPELIST = "typelist";//类型
    String STAR = "star";//星级
    String SCORE = "score";//评分
    String CODE = "code";//验证码
    String MONEY = "money";//钱
    String COUPON = "coupon";//优惠券
    String TP_ID = "tp_id";//id
    String INFORM = "inform";//aite
    String CONTENT = "content";//个人简介
    String IMAGE_FOLDER = "image_folder";//图片选择器用到

    String T_ID = "t_id";//目的地id
    String TID = "tid";//约伴
    String F_ID = "f_id";//发现ID
    String R_ID = "r_id";//发现评论id


 String TYPE_DESTINATION = "3";//类型，type  所属类型 1 游记 2美食 3 目的地
 String TYPE_TRAVELS = "1";//类型，type  所属类型 1 游记 2美食 3 目的地
 String TYPE_DELICIOUS = "2";//类型，type  所属类型 1 游记 2美食 3 目的地
 
 String PID = "pid";

 String NEXT_PAGE = "nextpage";//发现评论，是否有下一页
 String COUNT = "count";//发现评论，当前拥有数据数量
    String A_ID = "a_id";//活动id
    String R_USER_ID = "r_user_id";//活动id
    String ID = "id";//活动id
    int NO_MORE = 2;//没有跟多数据了

    int TYPE_TOGETHER=0;//一起玩
    int TYPE_WITH_ME=1;//带我玩
    String DATA = "data";
    String ITEM_DATA = "item_data";
      int SEND_PICTURE = 2;//发送图片
      int CREATE_POST = 3;//创建帖子


    String POSITION = "position";
    String TITLE = "title";//name
    String LABEL = "label";//

    String ADDRESS = "address";//地址
    String USER="user";
    String FILE_NAME = "file_name";

    String TRUE = "1";//正确
    String FAILE = "0";//错误
    String PICTURE_ID ="picture_id";//更新相册
    String URL="url";
    String PLAY_TOGETHER_CLICK_TYPE = "1";
    String WITH_ME_CLICK_TYPE = "2";
    String CLASS = "class";
    int pageCount = 20;


    ///年月日格式
    String Y_M_D="yyyy-MM-dd";
    String Y_M_DHms="yyyy-MM-dd HH:mm:ss";
    String YMD="yyyy.MM.dd";
    String YMDHms="yyyy.MM.dd HH:mm:ss";
    String RMB ="¥" ;
    String CHAT_ID = "chat_id";


    String ICON = "icon";
    String FLOOR="floor";
    String REPLAY_ID="replay_id";
}
