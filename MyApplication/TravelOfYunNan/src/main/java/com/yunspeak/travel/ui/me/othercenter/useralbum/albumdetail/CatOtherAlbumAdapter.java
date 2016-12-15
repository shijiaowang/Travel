package com.yunspeak.travel.ui.me.othercenter.useralbum.albumdetail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/21 0021.
 */
public class CatOtherAlbumAdapter extends BaseRecycleViewAdapter<CatOtherUserBean.DataBean.BodyBean> {



    public CatOtherAlbumAdapter(List<CatOtherUserBean.DataBean.BodyBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_cat_other_album, parent, false);
        return new CatOtherAlbumHolder(inflate);
    }


}
