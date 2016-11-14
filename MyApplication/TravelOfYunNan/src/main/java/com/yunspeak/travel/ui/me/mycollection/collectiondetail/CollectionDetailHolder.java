package com.yunspeak.travel.ui.me.mycollection.collectiondetail;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.yunspeak.travel.ui.baseui.ActivateDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.find.findcommon.FindCommonActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.ui.me.mycollection.MyCollectionActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
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
        String cancelId="";
        if (datas instanceof TeamBean.DataBean) {
            final TeamBean.DataBean dataBean = (TeamBean.DataBean) datas;
            cancelId=dataBean.getCid();
            FrescoUtils.displayNormal(mIvIcon,dataBean.getTravel_img());
            mTvContent.setText("行程日期:" + formatData(dataBean.getStart_time(), dataBean.getEnd_time()));
            mTvName.setText(dataBean.getRoutes_title());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("¥" + dataBean.getTotal_price());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AppointTogetherDetailActivity.start(mContext,dataBean.getCid());
                }
            });
        } else if (datas instanceof DestinationBean.DataBean) {
            final DestinationBean.DataBean dataBean = (DestinationBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getLogo_img());
            cancelId=dataBean.getCid();
            mTvContent.setText(dataBean.getAdd_ress());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvIcon.setVisibility(View.VISIBLE);
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DestinationDetailActivity.start(mContext,dataBean.getCid(),dataBean.getTitle());
                }
            });
        } else if (datas instanceof ActiveBean.DataBean) {
            final ActiveBean.DataBean dataBean = (ActiveBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getActivity_img());
            cancelId=dataBean.getCid();
            mTvContent.setText("活的期限:" + formatData(dataBean.getStart_time(), dataBean.getEnd_time()));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("¥"+dataBean.getPrice());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivateDetailActivity.start(mContext,dataBean.getCid());
                }
            });

        } else if (datas instanceof PostBean.DataBean) {
            final PostBean.DataBean dataBean = (PostBean.DataBean) datas;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostActivity.start(mContext,dataBean.getCid());
                }
            });
            FrescoUtils.displayNormal(mIvIcon,dataBean.getForum_img());
            cancelId=dataBean.getCid();
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            AiteUtils.parseTextMessage(mTvContent,dataBean.getInform(),dataBean.getContent(),mContext);
            if (StringUtils.isEmpty(dataBean.getTitle())){
                mTvName.setVisibility(View.GONE);
            }else {
                mTvName.setVisibility(View.VISIBLE);
                AiteUtils.parseTextMessage(mTvName,null,dataBean.getTitle(),mContext);
            }
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("#" + dataBean.getCname() + "#");
            mTvPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CircleDetailActivity.start(mContext,dataBean.getC_id());
                }
            });

        } else if (datas instanceof TravelsBean.DataBean) {
            final TravelsBean.DataBean dataBean = (TravelsBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getLogo_img());
            cancelId=dataBean.getCid();
            mTvContent.setText(dataBean.getAuthor());
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setVisibility(View.GONE);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TravelsDetailActivity.start(mContext,dataBean.getCid(),dataBean.getTitle());
                }
            });

        } else if (datas instanceof OtherBean.DataBean) {
            final OtherBean.DataBean dataBean = (OtherBean.DataBean) datas;
            FrescoUtils.displayNormal(mIvIcon,dataBean.getLogo_img());
            mTvContent.setText(dataBean.getContent());
            cancelId=dataBean.getCid();
            mTvContent.setTextColor(mContext.getResources().getColor(R.color.color969696));
            mTvName.setText(dataBean.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm", dataBean.getAdd_time()));
            mTvPrice.setTextColor(Color.parseColor("#ff8888"));
            mTvPrice.setText("#" + dataBean.getCname() + "#");
            final String type = dataBean.getType();
            mTvPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("1")){
                        FindCommonActivity.start(mContext,FindCommonActivity.DELICIOUS_NORMAL,0);
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (type.equals("1")){//美食
                        DeliciousDetailActivity.start(mContext,dataBean.getCid(),dataBean.getTitle());
                    }

                }
            });
        }
        mTvDelete.setTag(cancelId);
        mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EnterAppointDialog.showCommonDialog(mContext,"取消收藏", "确定", "是否删除当前收藏？", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        Map<String, String> deleteMap = MapUtils.Build().addKey().addUserId().addType(tid).addId((String) mTvDelete.getTag()).end();
                        CollectionDetailEvent event = new CollectionDetailEvent();
                        event.setPosition(position);
                        XEventUtils.postUseCommonBackJson(IVariable.CANCEL_COLLECTION,deleteMap, IState.TYPE_DELETE, event);
                    }
                });
            }
        });
    }
}
