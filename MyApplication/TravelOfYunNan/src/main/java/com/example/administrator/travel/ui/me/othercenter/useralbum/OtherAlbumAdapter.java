package com.example.administrator.travel.ui.me.othercenter.useralbum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

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
