package com.yunspeak.travel.ui.find.active;


import android.content.Intent;


import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.baseui.ActivateDetailActivity;
import com.yunspeak.travel.ui.baseui.BaseBarChangeColorActivity;
import com.yunspeak.travel.event.ActiveEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import java.util.List;

/**
 * Created by wangyang on 2016/7/25 0025.
 * 活动界面
 */
public class ActiveActivity extends BaseBarChangeColorActivity<ActiveEvent,ActiveBean,ActiveBean.DataBean> {




    @Override
    protected void initEvent() {
        vsContent.setLayoutResource(R.layout.activity_active_content);
        vsContent.inflate();
}

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(ActiveActivity.this, ActivateDetailActivity.class);
        intent.putExtra(IVariable.A_ID,mDatas.get(position).getId());
        startActivity(intent);
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_ACTIVITY;
    }








    @Override
    protected BaseRecycleViewAdapter initAdapter(List<ActiveBean.DataBean> mDatas) {
        return new ActiveAdapter(mDatas,this);
    }

    @Override
    protected String initTitle() {
        return "活动";
    }
}
