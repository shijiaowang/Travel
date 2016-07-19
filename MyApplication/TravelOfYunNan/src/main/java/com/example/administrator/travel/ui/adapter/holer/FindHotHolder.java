package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.FindHot;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by Administrator on 2016/7/19 0019.
 */
public class FindHotHolder extends BaseHolder<FindHot>{

    private TextView mTvAdd;

    public FindHotHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(FindHot datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = View.inflate(mContext, R.layout.item_fragment_find_hot, null);
        mTvAdd = FontsIconUtil.findIconFontsById(R.id.tv_add, mContext, inflate);
        return inflate;
    }
}
