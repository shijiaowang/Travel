package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Chosen;
import com.example.administrator.travel.ui.activity.BaseActivity;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.ChosenHolder;

import java.util.List;

/**
 * Created by Administrator on 2016/7/6 0006.
 * 主页精选
 */
public class ChosenAdapter extends TravelBaseAdapter<Chosen> {

    public ChosenAdapter(Context mContext, List<Chosen> mDatas) {
        super(mContext, mDatas);


    }
   //测试数据
    @Override
    protected int testDataSize() {
        return 4;
    }

    @Override
    protected BaseHolder initHolder() {

        return new ChosenHolder(super.mContext);
    }
}
