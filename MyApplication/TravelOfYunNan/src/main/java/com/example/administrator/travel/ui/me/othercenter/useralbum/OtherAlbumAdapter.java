package com.example.administrator.travel.ui.me.othercenter.useralbum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.LoadMoreRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/10/4.
 */

public class OtherAlbumAdapter extends LoadMoreRecycleViewAdapter {
    public OtherAlbumAdapter(List list) {
        super(list);
    }

    @Override
    protected RecyclerView.ViewHolder normalHolder(LayoutInflater inflater) {
        return new AlbumAdapter(inflater.inflate(R.layout.item_fragment_album_in_other,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
    class AlbumAdapter extends ItemViewHolder{

        public AlbumAdapter(View itemView) {
            super(itemView);
        }
    }
}
