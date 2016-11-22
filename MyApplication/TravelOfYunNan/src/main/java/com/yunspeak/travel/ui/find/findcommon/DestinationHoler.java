package com.yunspeak.travel.ui.find.findcommon;

import android.content.Context;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.utils.TypefaceUtis;

import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/30.
 */
public class DestinationHoler extends BaseRecycleViewHolder<DestinationBean.DataBean.BodyBean> {
    private final boolean isDestination;
    @BindView(R.id.iv_spot) SimpleDraweeView mIvSpot;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.rb_star) RatingBar mRbStart;
    @BindView(R.id.tv_add)TextView mTvAdd;
    @BindString(R.string.item_fragment_find_add) String addressIcon;

    public DestinationHoler(View itemView, boolean isDestination) {
        super(itemView);
        this.isDestination = isDestination;
    }



    private float getStar(DestinationBean.DataBean.BodyBean datas, float star) {
        try {
            star = Float.parseFloat(datas.getStar());
        }catch (Exception e){
            e.printStackTrace();
        }
        return star;
    }


    @Override
    public void childBindView(int position, final DestinationBean.DataBean.BodyBean datas, final Context mContext) {
        FrescoUtils.displayNormal(mIvSpot,datas.getLogo_img());
        mTvName.setText(datas.getTitle());
        String text=addressIcon+datas.getAddress();
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(text);
        spannableStringBuilder.setSpan(TypefaceUtis.getTypeface(mContext),0,1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mTvAdd.setText(spannableStringBuilder);

        float star=5.0f;
        star = getStar(datas, star);
        mRbStart.setRating(star);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (isDestination) {
                    intent = new Intent(mContext,DestinationDetailActivity.class);
                } else {
                    intent = new Intent(mContext, DeliciousDetailActivity.class);
                }
                    intent.putExtra(IVariable.T_ID, datas.getId());
                    intent.putExtra(IVariable.NAME,datas.getTitle());
                    mContext.startActivity(intent);
            }
        });


    }
}
