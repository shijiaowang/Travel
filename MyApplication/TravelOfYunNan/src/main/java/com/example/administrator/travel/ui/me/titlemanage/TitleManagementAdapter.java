package com.example.administrator.travel.ui.me.titlemanage;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/7 0007.
 * 称号管理
 */
public class TitleManagementAdapter extends TravelBaseAdapter<OfficialLabelBean> {


    public TitleManagementAdapter(Context mContext, List<OfficialLabelBean> mDatas) {
        super(mContext, mDatas);
    }



    @Override
    protected void initListener(BaseHolder baseHolder, final OfficialLabelBean item, int position) {
        final TitleManagementHolder titleManagementHolder = (TitleManagementHolder) baseHolder;
        titleManagementHolder.mTvUse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int type=-1;
                if (item.getStatus().equals("1")){//拥有未佩戴
                    item.setStatus("2");
                    type=TitleManagementActivity.ADD_TITLE;
                }else {
                    item.setStatus("1");
                    type=TitleManagementActivity.REMOVE_TITLE;
                }
                TitleManagementBean.DataBean.UserLabelBean userLabelBean=new TitleManagementBean.DataBean.UserLabelBean();
                userLabelBean.setId(item.getId());
                userLabelBean.setName(item.getName());
                userLabelBean.setType(item.getType());
                userLabelBean.setClassX(item.getClassX());
                EventBus.getDefault().post(new TitleChangeEvent(type,userLabelBean));
                notifyDataSetChanged();
            }
        });
        titleManagementHolder.getRootView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (titleManagementHolder.mLlToggle.getVisibility()==View.VISIBLE){
                    titleManagementHolder.mLlToggle.setVisibility(View.GONE);
                    titleManagementHolder.mTvToggle.setText(mContext.getString(R.string.activity_select_cursor));
                }else {
                    titleManagementHolder.mLlToggle.setVisibility(View.VISIBLE);
                    titleManagementHolder.mTvToggle.setText(mContext.getString(R.string.activity_cursor_down));
                }
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new TitleManagementHolder(mContext);
    }
}
