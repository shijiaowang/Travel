package com.example.administrator.travel.ui.activity;

import android.app.Activity;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.AppointTogetherDetailEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

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
        Map<String, String> travelDetailMap = MapUtils.Build().addKey(this).addUserId().addTId(tId).end();
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
         if (event.isSuccess()){
             GsonUtils.getObject(event.getResult(),)
         }else {
             setIsError(true);
             ToastUtils.showToast(event.getMessage());
         }
    }
}
