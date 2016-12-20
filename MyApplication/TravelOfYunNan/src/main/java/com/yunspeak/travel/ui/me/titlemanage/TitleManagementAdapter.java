package com.yunspeak.travel.ui.me.titlemanage;

import android.content.Context;
import android.view.View;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.OfficialLabelBean;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.bean.UserLabelBean;
import com.yunspeak.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

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
                if (item.getStatus().equals("0")){
                    return;
                }
                if (item.getStatus().equals("1")){//拥有未佩戴
                    if (GlobalValue.count>=7){
                        ToastUtils.showToast("最多佩戴7个称号");
                        return;
                    }
                    item.setStatus("2");
                    type=TitleManagementActivity.ADD_TITLE;
                }else {
                    item.setStatus("1");
                    type=TitleManagementActivity.REMOVE_TITLE;
                }
                UserLabelBean userLabelBean=new UserLabelBean();
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
