package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.AppointTogetherDetail;
import com.example.administrator.travel.event.AppointTogetherDetailEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelDetailLineAdapter;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/2 0002.
 * 一起玩约伴详情
 */
public class AppointTogetherDetailActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.lv_route_line)
    private ToShowAllListView mLvRouteLine;
    private String tId;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_appoint_together_detail;
    }

    @Override
    protected void initEvent() {
        tId = getIntent().getStringExtra(IVariable.T_ID);

    }

    @Override
    protected void onLoad() {
        Map<String, String> travelDetailMap = MapUtils.Build().addKey(this).addUserId().add(IVariable.TID,"4").end();
        XEventUtils.getUseCommonBackJson(IVariable.PLAY_TOGETHER_DETAIL,travelDetailMap,0,new AppointTogetherDetailEvent());
    }

    @Override
    protected Activity initViewData() {
        return this;
    }

    @Override
    protected String setTitleName() {
        return "约伴详情";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
    @Subscribe
    public void onEvent(AppointTogetherDetailEvent event){
        setIsProgress(false);
        List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists=new ArrayList<>();
         if (event.isSuccess()){
             dealData(event, lists);
         }else {
             setIsError(true);
             ToastUtils.showToast(event.getMessage());
         }
    }

    /**
     * 填充数据
     * @param event
     * @param lists
     */
    private void dealData(AppointTogetherDetailEvent event, List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists) {
        List<AppointTogetherDetail.DataBean.RoutesBean> routes = dealDate(event);
        classificationDay(lists, routes);
        mLvRouteLine.setAdapter(new TravelDetailLineAdapter(this,lists));
    }

    /**
     * 处理时间
     * @param event
     * @return
     */
    @NonNull
    private List<AppointTogetherDetail.DataBean.RoutesBean> dealDate(AppointTogetherDetailEvent event) {
        AppointTogetherDetail appointTogetherDetail = GsonUtils.getObject(event.getResult(), AppointTogetherDetail.class);
        AppointTogetherDetail.DataBean data = appointTogetherDetail.getData();
        List<AppointTogetherDetail.DataBean.RoutesBean> routes = data.getRoutes();
        for (AppointTogetherDetail.DataBean.RoutesBean routesBean: routes){
            String month = FormatDateUtils.FormatLongTime("MM", routesBean.getTime());
            String day = FormatDateUtils.FormatLongTime("dd",routesBean.getTime());
            month=dealZero(month);
            day=dealZero(day);
            String date=month+"月"+day+"日";
            routesBean.setTime(date);
        }
        return routes;
    }

    /**
     * 分类目的地
     * @param lists
     * @param routes
     */
    private void classificationDay(List<List<AppointTogetherDetail.DataBean.RoutesBean>> lists, List<AppointTogetherDetail.DataBean.RoutesBean> routes) {
        List<AppointTogetherDetail.DataBean.RoutesBean> dayList=new ArrayList<>();
        String preTime=routes.get(0).getTime();
        for (AppointTogetherDetail.DataBean.RoutesBean routesBean: routes){
            if (!routesBean.getTime().equals(preTime)){
                lists.add(dayList);
                dayList=new ArrayList<>();
                 preTime=routesBean.getTime();
            }
            dayList.add(routesBean);
        }
        lists.add(dayList);//加入最后一列


    }

    private String dealZero(String month) {
        if (month.startsWith("0") && month.length()==2){
            String substring = month.substring(1, month.length());
            return substring;
        }
        return month;
    }
}
