package com.example.administrator.travel.ui.me.bulltetinboard;

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

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class BulletinBoardAdapter extends BaseRecycleViewAdapter<BulletinBoardBean.DataBean> {


    public BulletinBoardAdapter(List<BulletinBoardBean.DataBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }


    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position, BulletinBoardBean.DataBean t) {
        BulletinBoardHolder bulletinBoardHolder = (BulletinBoardHolder) holder;
        BulletinBoardBean.DataBean datas = mDatas.get(position);
        bulletinBoardHolder.mTvTop.setVisibility(datas.getIs_top().equals(IVariable.TRUE)?View.VISIBLE:View.GONE);
        bulletinBoardHolder.mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd HH:mm:ss",datas.getAdd_time()));
        bulletinBoardHolder.mTvContent.setText(datas.getContent());
        bulletinBoardHolder.mTvTitle.setText(datas.getTitle());
        bulletinBoardHolder.mVDot.setVisibility(datas.getIs_top().equals(IVariable.TRUE)?View.GONE:View.VISIBLE);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_bulletin_board, parent, false);
        return new BulletinBoardHolder(inflate);
    }
    class BulletinBoardHolder extends BaseRecycleViewHolder{
        @BindView(R.id.tv_top) TextView mTvTop;
        @BindView(R.id.tv_title) TextView mTvTitle;
        @BindView(R.id.tv_content) TextView mTvContent;
        @BindView(R.id.tv_time) TextView mTvTime;
        @BindView(R.id.v_dot) View mVDot;
        public BulletinBoardHolder(View itemView) {
            super(itemView);
        }
    }
}
