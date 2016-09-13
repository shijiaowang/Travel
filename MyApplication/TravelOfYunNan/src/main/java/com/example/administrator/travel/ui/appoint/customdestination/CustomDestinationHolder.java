package com.example.administrator.travel.ui.appoint.customdestination;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.view.ShowAllTextView;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class CustomDestinationHolder extends BaseHolder<CustomDestinationBean.DataBean> {
    @ViewInject(R.id.iv_spot)
    private ImageView mIvSpot;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_user)
    private TextView mTvUser;
    @ViewInject(R.id.tv_show)
    private ShowAllTextView mTvShow;
    @ViewInject(R.id.tv_add)
    private TextView mTvAdd;
    @ViewInject(R.id.tv_select)
    private TextView mTvSelect;
    @ViewInject(R.id.tv_delete)
    public TextView mTvDelete;
    public CustomDestinationHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CustomDestinationBean.DataBean datas, Context mContext, int position) {
        x.image().bind(mIvSpot, datas.getLogo_img(), getImageOptions(DensityUtil.dip2px(115), DensityUtil.dip2px(80)));
        mTvDelete.setVisibility(datas.getIs_del().equals("1") ? View.VISIBLE : View.GONE);
        mTvName.setText(datas.getTitle());
        mTvAdd.setText(datas.getProvince() + datas.getCity() + datas.getAddress());
        mTvShow.setContent(datas.getContent());
        mTvUser.setText(datas.getUser_name());
        mTvSelect.setTextColor(GlobalValue.clickPosition == position ? mContext.getResources().getColor(R.color.Ffbf75) : mContext.getResources().getColor(R.color.colorb5b5b5));
        if (GlobalValue.mSelectSpot!=null && GlobalValue.mSelectSpot.contains(datas.getId())){
            getRootView().setAlpha(0.3f);
            mTvSelect.setVisibility(View.GONE);
        }else {
            getRootView().setAlpha(1.0f);
            mTvSelect.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_custom_destination);
    }
}
