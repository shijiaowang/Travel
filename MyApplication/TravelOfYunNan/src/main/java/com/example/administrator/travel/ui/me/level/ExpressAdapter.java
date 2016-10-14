package com.example.administrator.travel.ui.me.level;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/14 0014.
 */

public class ExpressAdapter extends BaseRecycleViewAdapter<LevelUserBean.DataBean.LevelDescBean> {


    public ExpressAdapter(List<LevelUserBean.DataBean.LevelDescBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_level_express, parent, false);
        return new ExpressHolder(inflate);
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position, LevelUserBean.DataBean.LevelDescBean levelDescBean) {
        ExpressHolder expressHolder = (ExpressHolder) holder;
        expressHolder.tvType.setText(levelDescBean.getTitle());
        expressHolder.tvExpress.setText(levelDescBean.getScore()+"/"+levelDescBean.getMax_score());
        expressHolder.tvMax.setText(levelDescBean.getMax_score());
    }

    class ExpressHolder extends BaseRecycleViewHolder {

        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_express)
        TextView tvExpress;
        @BindView(R.id.tv_max)
        TextView tvMax;


        public ExpressHolder(View itemView) {
            super(itemView);
        }
    }
}
