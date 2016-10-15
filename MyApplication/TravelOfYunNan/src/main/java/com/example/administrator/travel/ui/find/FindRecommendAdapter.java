package com.example.administrator.travel.ui.find;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.FindRecommend;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.example.administrator.travel.ui.baseui.BaseRecycleViewAdapter;
import com.example.administrator.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/19 0019.
 */
public class FindRecommendAdapter extends BaseRecycleViewAdapter<FindBean.DataBean.RecommendBean> {


    public FindRecommendAdapter(List<FindBean.DataBean.RecommendBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }



    @Override
    public BaseRecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_fragment_find_recommend, parent, false);
        return new FindRecommendHolder(inflate);
    }

}
