package com.example.administrator.travel.global;

import com.example.administrator.travel.bean.Login;
import com.example.administrator.travel.ui.appoint.lineplan.LineBean;

import java.util.List;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class GlobalValue {
    public static  String KEY_VALUE="";
    public static  Login.UserInfo userInfo;
    public static int CIRCLE_FOLLOW_LIKE_POSITION = 0;//圈子用于设置当前点击的位置
    public static List<String> mSelectImages;//选中的图
    public static List<String> mSelectSpot;//选中景点
    public static List<LineBean> mLineBeans;//景点集合
    public static  int selectTitleNumber;//选中的标签个数
    public static  int mAppointType;//发布约伴类型

    public static int clickPosition=-1;//点击的目的地
}
