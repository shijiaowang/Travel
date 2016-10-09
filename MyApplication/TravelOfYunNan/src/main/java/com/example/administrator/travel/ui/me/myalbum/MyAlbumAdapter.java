package com.example.administrator.travel.ui.me.myalbum;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.ui.me.myalbum.editalbum.EditAlbumActivity;

import org.xutils.x;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
public class MyAlbumAdapter extends BaseRecycleViewAdapter<MyAlbumBean.DataBean> {
    private static final int SHORT_ITEM = 1;
    private static final int LENGTH_ITEM = 2;


    public MyAlbumAdapter(List<MyAlbumBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    public int getItemViewType(int position) {

        if (position % 2 == 1) {
            return LENGTH_ITEM;
        }else {
            return SHORT_ITEM;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LENGTH_ITEM) {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_my_album_length, parent, false);
            return new MyAlbumLengthHolder(inflate);
        } else {
            View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_my_album_short, parent, false);
            return new MyAlbumLengthHolder(inflate);
        }
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, final int position) {
        MyAlbumBean.DataBean dataBean = mDatas.get(position);
        if (holder instanceof MyAlbumShortHolder) {
            MyAlbumShortHolder shortHolder = (MyAlbumShortHolder) holder;
            x.image().bind(shortHolder.mIvCover, dataBean.getCover_img());
            shortHolder.mTvDiscussNumber.setText(dataBean.getBrowse());
            shortHolder.mTvTitle.setText(dataBean.getTitle());
            shortHolder.mTvLoveNumber.setText(dataBean.getLike());
            shortHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startEditAlbum(position);
                }
            });
        } else {
            MyAlbumLengthHolder lengthHolder = (MyAlbumLengthHolder) holder;
            x.image().bind(lengthHolder.mIvCover, dataBean.getCover_img());
            lengthHolder.mTvDiscussNumber.setText(dataBean.getBrowse());
            lengthHolder.mTvTitle.setText(dataBean.getTitle());
            lengthHolder.mTvLoveNumber.setText(dataBean.getLike());
            lengthHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startEditAlbum(position);
                }
            });
        }
    }

    private void startEditAlbum(int position) {
        Intent intent = new Intent(mContext, EditAlbumActivity.class);
        intent.putExtra(IVariable.ID,mDatas.get(position).getId());
        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyAlbumShortHolder extends BaseRecycleViewHolder {
        @BindView(R.id.iv_cover) ImageView mIvCover;
        @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
        @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
        @BindView(R.id.tv_time) TextView mTvTitle;

        public MyAlbumShortHolder(View itemView) {
            super(itemView);
        }
    }

    public class MyAlbumLengthHolder extends BaseRecycleViewHolder {
        @BindView(R.id.iv_cover) ImageView mIvCover;
        @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
        @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
        @BindView(R.id.tv_time) TextView mTvTitle;

        public MyAlbumLengthHolder(View itemView) {
            super(itemView);
        }
    }
}
