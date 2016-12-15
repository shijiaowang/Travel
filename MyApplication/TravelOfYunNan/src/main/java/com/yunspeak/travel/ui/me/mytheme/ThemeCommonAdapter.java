package com.yunspeak.travel.ui.me.mytheme;

import android.content.Context;
import android.view.ViewGroup;

import com.yunspeak.travel.R;

import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;

import java.util.List;

/**
 * Created by wangyang on 2016/8/3 0003.
 */
public class ThemeCommonAdapter extends BaseRecycleViewAdapter<Object> {


    public ThemeCommonAdapter(List<Object> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    public BaseRecycleViewHolder<Object> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ThemeCommonHolder( inflateView(R.layout.item_fragment_my_theme,parent));
    }
}
