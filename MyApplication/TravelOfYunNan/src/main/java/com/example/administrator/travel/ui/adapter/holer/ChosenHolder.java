package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Chosen;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class ChosenHolder extends BaseHolder<Chosen> {

    private TextView mTvChosenText;
    private ImageView mIvChosenPicture;

    public ChosenHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Chosen datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View root = View.inflate(mContext,R.layout.item_fragment_home_chosen,null);
        mIvChosenPicture = (ImageView) root.findViewById(R.id.iv_chosen_picture);
        mTvChosenText = (TextView) root.findViewById(R.id.tv_chosen_text);
        return root;
    }
}
