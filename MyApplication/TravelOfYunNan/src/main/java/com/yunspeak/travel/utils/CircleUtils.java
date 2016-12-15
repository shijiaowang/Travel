package com.yunspeak.travel.utils;

import com.yunspeak.travel.ui.circle.Circle;
import com.yunspeak.travel.ui.circle.circlenav.CircleNavRight;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangyang on 2016/7/27 0027.
 */
public class CircleUtils {
    public static List<CircleNavRight.RightCircle> circleRightBean2RightCircleList(List<Circle.DataBean.CircleRightBean> list){
        List<CircleNavRight.RightCircle> rightCircles=new ArrayList<>();
        if (list==null){
            return rightCircles;
        }
        for (int i=0;i<list.size();i++){
            Circle.DataBean.CircleRightBean circleRightBean = list.get(i);
            CircleNavRight.RightCircle rightCircle=new CircleNavRight.RightCircle();
            rightCircle.setCid(circleRightBean.getCid());
            rightCircle.setCircle_ico(circleRightBean.getCircle_ico());
            rightCircle.setCircle_img(circleRightBean.getCircle_img());
            rightCircle.setCname(circleRightBean.getCname());
            rightCircle.setCount_follow(circleRightBean.getCount_follow());
            rightCircle.setCount_forum(circleRightBean.getCount_forum());
            rightCircles.add(rightCircle);
        }
        return rightCircles;
    }
}
