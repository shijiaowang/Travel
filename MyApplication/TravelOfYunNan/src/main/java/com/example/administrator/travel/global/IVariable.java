package com.example.administrator.travel.global;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 全局变量
 */
public interface IVariable {
    //前两个为通用
    String DOMAIN_NAME="http://192.168.1.158/";
    String API_KEY=DOMAIN_NAME+"api.php?s=/";
    //下面为请求链接
    String GET_KEY="api/getKeys";
    String LOGIN_URL=API_KEY+"User/Login/";
    String FIRST_CIRCLE_URL=API_KEY+"Circle/getCirclelists/";
    String NORMAL_CIRCLE_URL=API_KEY+"Circle/getCirclechildlists/";





    //请求type
    int TYPE_GET_KEY=0;
    int TYPE_POST_LOGIN=1;//请求登录



    //share
    String KEY_CODE="key_code";
    String KEY_VALUE="key_value";


    int OK_KEY_CODE=1;

    //sharename
    String SHARE_NAME="yunspeak";
    //登录
    String USERNAME="name";
    String KEY="key";
    String PASSWORD="pwd";

    //保存登录
    String SAVE_NAME ="save_name";
    String SAVE_PWD ="save_pwd";


    //传递信息
    String USER_INFO="user_info";




    //返回code
    int CODE_EXCEPTION=-5;//解析code的时候发生了异常
    int PASSWORD_ERROR=0;//密码错误
    int SUCCESS=1;//登录成功
    int KEY_ERROR=-1;//key错误
    int ILLEGAL_OPERATION=-2;//非法操作
    int USERNAME_ERROR=-3;//用户名错误
    int EXIST_USER=-4;//存在用户

    //缓存登录是否有网
    String CACHE_LOGIN_ARE_WITH_NETWORK="cache_login_are_with_network";

    int  FIRST_REQ_CIRCLE =0;//第一次请求
    int NORMAL_REQ_CIRCLE =1;//之后的请求

}
