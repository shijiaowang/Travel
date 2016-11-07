package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.me.mycollection.MyCollectionActivity;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.x;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/14.
 */
public class CollectionDetailHolder extends BaseRecycleViewHolder<Object> {
    private final String tid;
    @BindView(R.id.tv_delete)
    TextView mTvDelete;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    @BindView(R.id.iv_icon)
    SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.tv_icon)
    TextView mTvIcon;

    public CollectionDetailHolder(View itemView, String tid) {
        super(itemView);
        this.tid = tid;
    }


    private String formatData(String start, String end) {
        return FormatDateUtils.FormatLongTime("yyyy.MM.dd", start) + "至" + FormatDateUtils.FormatLongTime("yyyy.MM.dd", end);
    }

    @Override
    public void childBindView(final int position, Object datas, final Context mContext) {
        String id="";
        if (datas instanceof TeamBean.DataBean) {
            TeamBean.DataBean dataBean = (TeamBean.DataBean) datas;
            id=dataBean.getId();
            FrescoUtils.displayNormal(mIvIcon,dataBean.getTravel_img());
            mTvContent.setText("行程日期:" + formatData(dataBean.getStart_time(), dataBean.getEnd_time()));
            mTvName.setText(dataBean.getRoutes_title());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("¥" + dataBean.getTotal_price());
        } else if (datas instanceof DestinationBean.DataBean) {
            DestinationBean.DataBean dataBean = (DestinationBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getLogo_img());
            id=dataBean.getId();
            mTvContent.setText(dataBean.getAdd_ress());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvIcon.setVisibility(View.VISIBLE);
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setVisibility(View.GONE);
        } else if (datas instanceof ActiveBean.DataBean) {
            ActiveBean.DataBean dataBean = (ActiveBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getActivity_img());
            id=dataBean.getId();
            mTvContent.setText("活的期限:" + formatData(dataBean.getStart_time(), dataBean.getEnd_time()));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
        } else if (datas instanceof PostBean.DataBean) {
            PostBean.DataBean dataBean = (PostBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getForum_img());
            mTvContent.setText(dataBean.getContent());
            id=dataBean.getId();
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("#" + dataBean.getCname() + "#");
        } else if (datas instanceof TravelsBean.DataBean) {
            TravelsBean.DataBean dataBean = (TravelsBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getLogo_img());
            id=dataBean.getId();
            mTvContent.setText(dataBean.getAuthor());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setVisibility(View.GONE);
        } else if (datas instanceof OtherBean.DataBean) {
            OtherBean.DataBean dataBean = (OtherBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getLogo_img());
            mTvContent.setText(dataBean.getContent());
            id=dataBean.getId();
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("#" + dataBean.getCname() + "#");
        }
        mTvDelete.setTag(id);
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showCommonDialog("取消收藏", "确定", "是否删除当前收藏？", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        Map<String, String> deleteMap = MapUtils.Build().addKey().addUserId().addTypeId(tid).addId((String) mTvDelete.getTag()).end();
                        CollectionDetailEvent event = new CollectionDetailEvent();
                        event.setPosition(position);
                        XEventUtils.postUseCommonBackJson(IVariable.CANCEL_COLLECTION,deleteMap, MyCollectionActivity.COLLECTION_CANCEL, event);
                    }
                });
            }
        });
    }
}
