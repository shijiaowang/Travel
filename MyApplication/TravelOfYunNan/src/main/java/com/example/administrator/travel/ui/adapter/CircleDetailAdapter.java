package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleDetail;
import com.example.administrator.travel.event.CircleDetailEvent;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.CircleDetailActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleDetailHolder;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CircleDetailAdapter extends TravelBaseAdapter<CircleDetail.DataBean.BodyBean> {
    public CircleDetailAdapter(Context mContext, List<CircleDetail.DataBean.BodyBean> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected void initListener(BaseHolder baseHolder, final CircleDetail.DataBean.BodyBean item, final int position) {
        if (baseHolder instanceof CircleDetailHolder) {
            final CircleDetailHolder circleDetailHolder = (CircleDetailHolder) baseHolder;
            circleDetailHolder.mTvIconLove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!item.getIs_like().equals("1")) {
                        circleDetailHolder.mTvIconLove.setTextColor(mContext.getResources().getColor(R.color.otherFf7f6c));
                        GlobalValue.CIRCLE_FOLLOW_LIKE_POSITION=position;
                        //点赞
                        Map<String, String> likeMap = MapUtils.Build().addKey(mContext).addFroumId(item.getId()).addUserId().addRUserId(item.getUser_id()).end();
                        XEventUtils.postUseCommonBackJson(IVariable.CIRCLE_LIKE_POST, likeMap, CircleDetailActivity.TYPE_LIKE_POST, new CircleDetailEvent());
                    }else {
                        ToastUtils.showToast("你已经点过赞了");
                    }

                }
            });
        }
    }


    @Override
    protected BaseHolder initHolder(int position) {
        return new CircleDetailHolder(super.mContext);
    }
}
