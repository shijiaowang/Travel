package com.yunspeak.travel.ui.find.active;


import android.content.Intent;
import android.view.ViewGroup;


import com.yunspeak.travel.R;
import com.yunspeak.travel.event.DetailCommonEvent;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.ActivityBean;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.baseui.BaseBarChangeColorActivity;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import java.util.List;

/**
 * Created by wangyang on 2016/7/25 0025.
 * 活动界面
 */
public class ActiveActivity extends BaseBarChangeColorActivity<DetailCommonEvent,ActiveBean,ActivityBean> {

    @Override
    protected void initEvent() {
        super.initEvent();
        vsContent.setLayoutResource(R.layout.activity_active_content);
        vsContent.inflate();
        changeMargin(10,15);
        int margin = (int) getResources().getDimension(R.dimen.x10);
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) mRvCommon.getLayoutParams();
        layoutParams.leftMargin=margin;
        layoutParams.rightMargin=margin;
        mRvCommon.setLayoutParams(layoutParams);
    }


    @Override
    protected String initUrl() {
        return IVariable.FIND_ACTIVITY;
    }




    @Override
    protected BaseRecycleViewAdapter initAdapter(List<ActivityBean> mDatas) {
        return new ActiveAdapter(mDatas,this);
    }

    @Override
    protected String initTitle() {
        return "活动";
    }
}
