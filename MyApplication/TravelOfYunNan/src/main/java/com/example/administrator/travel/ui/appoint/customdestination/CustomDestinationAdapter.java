package com.example.administrator.travel.ui.appoint.customdestination;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class CustomDestinationAdapter extends TravelBaseAdapter<CustomDestinationBean.DataBean> {
    private String deleteDataId="";
    public CustomDestinationAdapter(Context mContext, List<CustomDestinationBean.DataBean> mDatas) {
        super(mContext, mDatas);
    }


    @Override
    protected void initListener(BaseHolder baseHolder, final CustomDestinationBean.DataBean item, final int position) {
        CustomDestinationHolder customDestinationHolder = (CustomDestinationHolder) baseHolder;
        customDestinationHolder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteDataId.equals(item.getId())){
                    return;
                }
                deleteDataId=item.getId();//记录删除的数据，避免重复删除
                Map<String, String> deleteMap = MapUtils.Build().addKey(mContext).addUserId().addtId(deleteDataId).end();
                CustomDestinationEvent customDestinationEvent=new CustomDestinationEvent();
                customDestinationEvent.setDeletePosition(position);
                XEventUtils.postUseCommonBackJson(IVariable.DELETE_CUSTOM_SPOT, deleteMap, LoadingBarBaseActivity.TYPE_DELETE,customDestinationEvent);
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CustomDestinationHolder(mContext);
    }
}
