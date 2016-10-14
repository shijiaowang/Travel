package com.example.administrator.travel.ui.me.othercenter.useralbum;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
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
    protected void childBindView(RecyclerView.ViewHolder holder, int position, OtherAlbumBean.DataBean.MoreBean t) {
        AlbumHolder albumHolder = (AlbumHolder) holder;
        OtherAlbumBean.DataBean.MoreBean moreBean = mDatas.get(position);
        FrescoUtils.displayNormal(albumHolder.ivCover,moreBean.getCover_img());
        albumHolder.tvName.setText(moreBean.getTitle());
        albumHolder.tvWatchNumber.setText(moreBean.getBrowse());
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = inflater.inflate(R.layout.item_fragment_album_in_other, null);
        return new AlbumHolder(inflate);
    }

    class AlbumHolder extends BaseRecycleViewHolder {
        @BindView(R.id.iv_cover)
        SimpleDraweeView ivCover;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_watch_number)
        TextView tvWatchNumber;

        public AlbumHolder(View itemView) {
            super(itemView);
        }
    }
}
