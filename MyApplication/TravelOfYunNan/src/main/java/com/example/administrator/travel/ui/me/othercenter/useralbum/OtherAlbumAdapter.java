package com.example.administrator.travel.ui.me.othercenter.useralbum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.LoadMoreRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/10/4.
 */

public class OtherAlbumAdapter extends LoadMoreRecycleViewAdapter {
    public OtherAlbumAdapter(List list, Context mContext) {
        super(list,mContext);
    }

    @Override
    protected RecyclerView.ViewHolder normalHolder(LayoutInflater inflater, ViewGroup parent, int viewType) {
        return new AlbumAdapter(inflater.inflate(R.layout.item_fragment_album_in_other,null));
    }


    @Override
    protected void bindNormal(RecyclerView.ViewHolder holder, int position) {

    }

    class AlbumAdapter extends ItemViewHolder{

        public AlbumAdapter(View itemView) {
            super(itemView);
        }
    }
}
