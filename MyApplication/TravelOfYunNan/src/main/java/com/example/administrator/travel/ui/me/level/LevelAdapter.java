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

public class LevelAdapter extends BaseRecycleViewAdapter<LevelUserBean.DataBean.LevelBean> {


    public LevelAdapter(List<LevelUserBean.DataBean.LevelBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected void childBindView(RecyclerView.ViewHolder holder, int position, LevelUserBean.DataBean.LevelBean t) {
        LevelHolder levelHolder = (LevelHolder) holder;
        levelHolder.tvLevel.setText("Lv."+t.getId());
        levelHolder.tvExpress.setText(t.getContent());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_level, parent, false);
        return new LevelHolder(inflate);
    }

     class LevelHolder extends BaseRecycleViewHolder {
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_express)
        TextView tvExpress;


         public LevelHolder(View itemView) {
             super(itemView);
         }
     }
}
