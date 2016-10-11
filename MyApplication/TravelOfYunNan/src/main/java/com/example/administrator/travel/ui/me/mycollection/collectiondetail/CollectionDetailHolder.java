package com.example.administrator.travel.ui.me.mycollection.collectiondetail;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/14.
 */
public class CollectionDetailHolder extends BaseHolder<Object> {
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.iv_icon)
    ImageView mIvIcon;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_icon)
    TextView mTvIcon;

    public CollectionDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(Object datas, Context mContext, int position) {
        if (datas instanceof TeamBean.DataBean) {
            TeamBean.DataBean dataBean = (TeamBean.DataBean) datas;
            x.image().bind(mIvIcon, dataBean.getTravel_img());
            mTvContent.setText("行程日期:" + formatData(dataBean.getStart_time(), dataBean.getEnd_time()));
            mTvName.setText(dataBean.getRoutes_title());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("¥" + dataBean.getTotal_price());
        } else if (datas instanceof DestinationBean.DataBean) {
            DestinationBean.DataBean dataBean = (DestinationBean.DataBean) datas;
            x.image().bind(mIvIcon, dataBean.getLogo_img());
            mTvContent.setText(dataBean.getAdd_ress());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvIcon.setVisibility(View.VISIBLE);
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setVisibility(View.GONE);
        } else if (datas instanceof ActiveBean.DataBean) {
            ActiveBean.DataBean dataBean = (ActiveBean.DataBean) datas;
            x.image().bind(mIvIcon, dataBean.getActivity_img());
            mTvContent.setText("活的期限:" + formatData(dataBean.getStart_time(), dataBean.getEnd_time()));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));

        } else if (datas instanceof PostBean.DataBean) {
            PostBean.DataBean dataBean = (PostBean.DataBean) datas;
            x.image().bind(mIvIcon, dataBean.getForum_img());
            mTvContent.setText(dataBean.getContent());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("#" + dataBean.getCname() + "#");
        } else if (datas instanceof TravelsBean.DataBean) {
            TravelsBean.DataBean dataBean = (TravelsBean.DataBean) datas;
            x.image().bind(mIvIcon, dataBean.getLogo_img());
            mTvContent.setText(dataBean.getAuthor());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setVisibility(View.GONE);
        } else if (datas instanceof OtherBean.DataBean) {
            OtherBean.DataBean dataBean = (OtherBean.DataBean) datas;
            x.image().bind(mIvIcon, dataBean.getLogo_img());
            mTvContent.setText(dataBean.getContent());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("#" + dataBean.getCname() + "#");
        }
    }
    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_collection_detail);
    }

    private String formatData(String start, String end) {
        return FormatDateUtils.FormatLongTime("yyyy.MM.dd", start) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", end);
    }
}
