package com.example.administrator.travel.ui.me.othercenter.useralbum.albumdetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/21 0021.
 */
public class CatOtherAlbumAdapter extends BaseRecycleViewAdapter<CatOtherUserBean.DataBean.BodyBean> {




    public CatOtherAlbumAdapter(List<CatOtherUserBean.DataBean.BodyBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position, CatOtherUserBean.DataBean.BodyBean bodyBean) {
        CatOtherAlbumHolder catOtherAlbumHolder = (CatOtherAlbumHolder) holder;
        FrescoUtils.displayNormal(catOtherAlbumHolder.ivBg,bodyBean.getPath());
        catOtherAlbumHolder.tvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_D,bodyBean.getAdd_time()));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_cat_other_album, parent, false);
        return new CatOtherAlbumHolder(inflate);
    }

    class CatOtherAlbumHolder extends BaseRecycleViewHolder {
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.iv_bg)
        SimpleDraweeView ivBg;
        public CatOtherAlbumHolder(View itemView) {
            super(itemView);
        }
    }
}
