package com.example.administrator.travel.ui.me.collectiondetail;

import android.content.Context;
import android.view.View;

import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.TravelBaseAdapter;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.me.mycollection.MyCollectionActivity;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/14.
 */
public class CollectionDetailAdapter extends TravelBaseAdapter {

    private  String tid;

    public CollectionDetailAdapter(Context mContext, List mDatas, String tid) {
        super(mContext, mDatas);
        this.tid = tid;
    }


    @Override
    protected void initListener(BaseHolder baseHolder, final Object item, final int position) {
        CollectionDetailHolder collectionDetailHolder = (CollectionDetailHolder) baseHolder;
        collectionDetailHolder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id="";
                if (item instanceof TeamBean.DataBean) {
                    TeamBean.DataBean dataBean = (TeamBean.DataBean) item;
                    id=dataBean.getId();
                } else if (item instanceof DestinationBean.DataBean) {
                    DestinationBean.DataBean dataBean = (DestinationBean.DataBean) item;
                    id=dataBean.getId();
                } else if (item instanceof ActiveBean.DataBean) {
                    ActiveBean.DataBean dataBean = (ActiveBean.DataBean) item;
                    id=dataBean.getId();
                } else if (item instanceof PostBean.DataBean) {
                    PostBean.DataBean dataBean = (PostBean.DataBean) item;
                    id=dataBean.getId();
                } else if (item instanceof TravelsBean.DataBean) {
                    TravelsBean.DataBean dataBean = (TravelsBean.DataBean) item;
                    id=dataBean.getId();
                } else if (item instanceof OtherBean.DataBean) {
                    OtherBean.DataBean dataBean = (OtherBean.DataBean) item;
                    id=dataBean.getId();
                }
                Map<String, String> deleteMap = MapUtils.Build().addKey(mContext).addUserId().addTypeId(tid).addId(id).end();
                CollectionDetailEvent event = new CollectionDetailEvent();
                event.setPosition(position);
                XEventUtils.postUseCommonBackJson(IVariable.CANCEL_COLLECTION,deleteMap, MyCollectionActivity.COLLECTION_CANCEL, event);
            }
        });
    }

    @Override
    protected BaseHolder initHolder(int position) {
        return new CollectionDetailHolder(mContext);
    }
}
