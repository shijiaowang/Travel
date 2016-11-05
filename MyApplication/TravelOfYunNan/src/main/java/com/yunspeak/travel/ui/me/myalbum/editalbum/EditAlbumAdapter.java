package com.yunspeak.travel.ui.me.myalbum.editalbum;



import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.TravelBaseAdapter;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/7/18 0018.
 */
public class EditAlbumAdapter extends BaseRecycleViewAdapter<EditAlbumBean.DataBean.BodyBean> {
    private boolean canDelete=false;

    public EditAlbumAdapter(List<EditAlbumBean.DataBean.BodyBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }




    @Override
    public BaseRecycleViewHolder<EditAlbumBean.DataBean.BodyBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EditAlbumHolder(inflateView(R.layout.item_activity_edit_album,parent),mDatas);
    }


}
