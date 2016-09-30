package com.example.administrator.travel.ui.appoint.together;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.fragment.BaseFragment;
import com.example.administrator.travel.ui.fragment.LoadBaseFragment;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/20 0020.
 */
public class AppointTogetherAdapter extends TravelBaseAdapter<AppointTogetherBean.DataBean> {
    public AppointTogetherAdapter(Context mContext, List<AppointTogetherBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final AppointTogetherBean.DataBean item, final int position) {
        AppointTogetherHolder appointTogetherHolder = (AppointTogetherHolder) baseHolder;
        appointTogetherHolder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AppointTogetherDetailActivity.class);
                intent.putExtra(IVariable.T_ID,item.getId());
                mContext.startActivity(intent);
            }
        });
        appointTogetherHolder.mTvIconLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> end = MapUtils.Build().addKey(mContext).addUserId().addType(IVariable.PLAY_TOGETHER_CLICK_TYPE).addtId(item.getId()).addRUserId(item.getUser_id()).end();
                 AppointTogetherEvent event=new AppointTogetherEvent();
                event.setClickPosition(position);
                XEventUtils.postUseCommonBackJson(IVariable.APPOINT_CLICK_ZAN,end, LoadBaseFragment.TYPE_CLICK_ZAN,event);

            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new AppointTogetherHolder(mContext);
    }
}
