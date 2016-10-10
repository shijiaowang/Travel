package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Chosen;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class ChosenHolder extends BaseHolder<Chosen> {
    @BindView(R.id.tv_chosen_text) TextView mTvChosenText;
    @BindView(R.id.iv_chosen_picture) ImageView mIvChosenPicture;

    public ChosenHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Chosen datas, Context mContext, int position) {

    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_home_chosen);
    }
}
