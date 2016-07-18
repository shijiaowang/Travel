package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.EditAlbum;
import com.example.administrator.travel.bean.HotSpots;
import com.example.administrator.travel.ui.activity.EditAlbumActivity;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class MyAlbumAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int SHORT_ITEM = 1;
    private static final int LENGTH_ITEM = 2;
    private Context mContext;
    private List<HotSpots> mDatas;

    public MyAlbumAdapter(Context mContext, List<HotSpots> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 1) {
            return LENGTH_ITEM;
        }
        return SHORT_ITEM;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = null;
        if (viewType == LENGTH_ITEM) {
            inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_my_album_length, parent, false);
            return new MyAlbumLengthHolder(inflate);
        } else {
            inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_my_album_short, parent, false);
            return new MyAlbumLengthHolder(inflate);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyAlbumShortHolder){
            MyAlbumShortHolder shortHolder = (MyAlbumShortHolder) holder;
            shortHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, EditAlbumActivity.class));
                }
            });
        }else {
            MyAlbumLengthHolder lengthHolder = (MyAlbumLengthHolder) holder;
            lengthHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mContext.startActivity(new Intent(mContext, EditAlbumActivity.class));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyAlbumShortHolder extends RecyclerView.ViewHolder {

        ImageView mIvCover;

        public MyAlbumShortHolder(View itemView) {
            super(itemView);
            mIvCover = (ImageView) itemView.findViewById(R.id.iv_cover);
        }
    }

    public class MyAlbumLengthHolder extends RecyclerView.ViewHolder {

        ImageView mIvCover;

        public MyAlbumLengthHolder(View itemView) {
            super(itemView);
            mIvCover = (ImageView) itemView.findViewById(R.id.iv_cover);
        }
    }
}
