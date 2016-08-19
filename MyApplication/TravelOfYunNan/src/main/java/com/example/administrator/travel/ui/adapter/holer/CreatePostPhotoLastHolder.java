package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/7/31.
 */
public class CreatePostPhotoLastHolder extends BaseHolder {
   @ViewInject(R.id.tv_add)
    public FontsIconTextView mTvAdd;

    public CreatePostPhotoLastHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext) {

    }

    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_create_post_photo_add);
        return inflate;
    }
}
