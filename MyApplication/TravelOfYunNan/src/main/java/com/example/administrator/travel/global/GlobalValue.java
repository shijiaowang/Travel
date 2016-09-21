package com.example.administrator.travel.global;

import android.app.Activity;

import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.ui.appoint.choicesequipment.ChoicePropSelectBean;
import com.example.administrator.travel.ui.appoint.lineplan.LineBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/27 0027.
 */
public class GlobalValue {
    public static  String KEY_VALUE="";
    public static UserInfo userInfo;
    public static int CIRCLE_FOLLOW_LIKE_POSITION = 0;//圈子用于设置当前点击的位置
    public static List<String> mSelectImages;//选中的图
    public static List<String> mSelectSpot;//选中景点
    public static List<LineBean> mLineBeans;//景点集合
    public static  int selectTitleNumber;//选中的标签个数
    public static  int mAppointType;//发布约伴类型

    public static int clickPosition=-1;//点击的目的地

    public static int choicePropType=0;//租用道具选项
    public static Map<String,ChoicePropSelectBean> mPropSelects;//选择的道具


}
