package com.yunspeak.travel.global;

import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.bean.ChoicePropSelectBean;
import com.yunspeak.travel.bean.LineBean;


import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/27 0027.
 */
public class GlobalValue {
    public static  String KEY_VALUE="";
    public static UserInfo userInfo;
    public static List<String> mSelectImages;//选中的图
    public static int size=12;//选中的图,默认十二张
    public static List<LineBean> mLineBeans;//景点集合
    public static  int selectTitleNumber;//选中的标签个数
    public static  int mAppointType;//发布约伴类型


    public static int clickPosition=-1;//点击的目的地

    public static int choicePropType=0;//租用道具选项
    public static Map<String,ChoicePropSelectBean> mPropSelects;//选择的道具
   public static int count=0;

    public static String mFileName;
}
