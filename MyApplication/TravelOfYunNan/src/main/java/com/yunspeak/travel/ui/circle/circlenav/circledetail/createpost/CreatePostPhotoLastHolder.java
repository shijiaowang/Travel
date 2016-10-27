package com.yunspeak.travel.ui.circle.circlenav.circledetail.createpost;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/31.
 */
public class CreatePostPhotoLastHolder extends BaseHolder {
    private final int count;
    public View mTvAdd;
    @BindView(R.id.tv_count) TextView mTvCount;


    public CreatePostPhotoLastHolder(Context context, int count) {
        super(context);
        this.count = count;
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        mTvCount.setText("最多"+count+"张");
    }

    @Override
    public View initRootView(Context mContext) {
        mTvAdd = inflateView(R.layout.item_activity_create_post_photo_add);
        return mTvAdd;
    }
}
