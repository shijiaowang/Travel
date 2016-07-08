package com.example.administrator.travel.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.bean.CircleHot;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.adapter.holer.CircleHotHolder;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleHotAdapter extends TravelBaseAdapter<CircleHot> {


    public CircleHotAdapter(Context mContext, List<CircleHot> mDatas) {
        super(mContext, mDatas);
    }

    @Override
    protected int testDataSize() {
        return 2;
    }

    @Override
    protected BaseHolder initHolder() {
        return new CircleHotHolder(super.mContext);
    }


}
