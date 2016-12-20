package com.yunspeak.travel.ui.me.level;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.LevelUserBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

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
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_level_express, parent, false);
        return new ExpressHolder(inflate);
    }



    class ExpressHolder extends BaseRecycleViewHolder<LevelUserBean.DataBean.LevelDescBean> {

        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_express)
        TextView tvExpress;
        @BindView(R.id.tv_max)
        TextView tvMax;


        public ExpressHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(int position, LevelUserBean.DataBean.LevelDescBean levelDescBean, Context mContext) {
            tvType.setText(levelDescBean.getTitle());
            tvExpress.setText(levelDescBean.getScore()+"/"+levelDescBean.getMax_score());
            tvMax.setText(levelDescBean.getMax_score());
        }


    }
}
