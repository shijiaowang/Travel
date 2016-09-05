package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Destination;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by android on 2016/7/30.
 */
public class DestinationHoler extends BaseHolder<Destination.DataBean.BodyBean> {
    @ViewInject(R.id.iv_spot)
    private ImageView mIvSpot;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.rb_star)
    private RatingBar mRbStart;
    @ViewInject(R.id.tv_add)
    private TextView mTvAdd;


    public DestinationHoler(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Destination.DataBean.BodyBean datas, Context mContext, int position) {
        x.image().bind(mIvSpot, datas.getLogo_img(), getImageOptions(115, 80));
        mTvName.setText(datas.getTitle());
        mTvAdd.setText(datas.getAddress());
        float star=5.0f;
        star = getStar(datas, star);
        mRbStart.setRating(star);
    }

    private float getStar(Destination.DataBean.BodyBean datas, float star) {
        try {
            star = Float.parseFloat(datas.getStar());
        }catch (Exception e){
            e.printStackTrace();
        }
        return star;
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_destination);
    }
}
