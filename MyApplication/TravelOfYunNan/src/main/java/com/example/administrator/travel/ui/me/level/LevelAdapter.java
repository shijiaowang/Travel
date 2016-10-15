package com.example.administrator.travel.ui.me.level;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
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
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_level, parent, false);
        return new LevelHolder(inflate);
    }

     class LevelHolder extends BaseRecycleViewHolder<LevelUserBean.DataBean.LevelBean> {
        @BindView(R.id.tv_level)
        TextView tvLevel;
        @BindView(R.id.tv_express)
        TextView tvExpress;


         public LevelHolder(View itemView) {
             super(itemView);
         }

         @Override
         public void childBindView(int position, LevelUserBean.DataBean.LevelBean t, Context mContext) {
             tvLevel.setText("Lv."+t.getId());
             tvExpress.setText(t.getContent());
         }
     }
}
