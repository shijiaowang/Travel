package com.yunspeak.travel.ui.me.othercenter.useralbum;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.OtherAlbumBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;


/**
 * Created by wangyang on 2016/10/4.
 */

public class OtherAlbumAdapter extends BaseRecycleViewAdapter<OtherAlbumBean.DataBean.MoreBean> {

    private final LayoutInflater inflater;

    public OtherAlbumAdapter(List list, Context mContext) {
        super(list, mContext);
        inflater = LayoutInflater.from(mContext);
    }



    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.item_fragment_album_in_other, null);
        return new AlbumHolder(inflate);
    }


}
