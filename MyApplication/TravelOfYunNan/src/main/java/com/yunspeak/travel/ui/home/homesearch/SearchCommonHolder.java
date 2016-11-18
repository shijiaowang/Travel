package com.yunspeak.travel.ui.home.homesearch;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IState;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.find.active.activedetail.ActivateDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import java.util.Map;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/22 0022.
 */
public class SearchCommonHolder extends BaseRecycleViewHolder<SearchCommonBean.DataBean> {


    private final String type;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindString(R.string.activity_circle_detail_followed)  String followed;
    @BindString(R.string.activity_circle_detail_follow)   String unFollow;
    @BindDrawable(R.drawable.activity_home_search_follow_bg) Drawable unFollowBg;
    @BindDrawable(R.drawable.activity_home_search_followed_bg)  Drawable followedBg;
    @BindColor(R.color.otherFf7f6c) @ColorInt int unFollowColor;
    @BindColor(R.color.colorFAFAFA) @ColorInt int followColor;

    public SearchCommonHolder(View itemView, String type) {
        super(itemView);
        this.type = type;
    }
    @Override
    public void childBindView(final int position, final SearchCommonBean.DataBean data, final Context mContext) {
       tvFollow.setTextColor(unFollowColor);
        tvTitle.setText(data.getTitle());
        tvContent.setText(data.getContent());
        if (type.equals(HomeSearchActivity.SEARCH_USER)){
            FrescoUtils.displayIcon(ivIcon,data.getLogo_img());
            if (data.getCname().equals("1")){
                tvFollow.setText(followed);
                tvFollow.setTextColor(followColor);
                tvFollow.setBackgroundDrawable(followedBg);
            }else {
                tvFollow.setText(unFollow);
                tvFollow.setTextColor(unFollowColor);
                tvFollow.setBackgroundDrawable(unFollowBg);
            }
            tvFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String clickType=data.getCname().equals("1")?"2":"1";
                    Map<String, String> followMap = MapUtils.Build().addKey().addUserId().add("u_id", data.getId()).addType(clickType).end();
                    HomeSearchEvent event = new HomeSearchEvent();
                    event.setPosition(position);
                    event.setSearchType(type);
                    XEventUtils.postUseCommonBackJson(IVariable.FOLLOW_OR_CANCEL_FOLLOW, followMap, IState.TYPE_UPDATE, event);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(mContext,data.getId());
                }
            });

        }else if (type.equals(HomeSearchActivity.SEARCH_DESTINATION)){
             tvFollow.setVisibility(View.GONE);
            FrescoUtils.displayRoundIcon(ivIcon,data.getLogo_img(),150,150);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DestinationDetailActivity.start(mContext,data.getId(),data.getTitle());
                }
            });

        } else if (type.equals(HomeSearchActivity.SEARCH_CIRCLE)){
            FrescoUtils.displayIcon(ivIcon,data.getLogo_img());
            tvFollow.setText("#"+data.getCname()+"#");
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (data.getType().equals("1")){
                            CircleDetailActivity.start(mContext,data.getId());
                        }else {
                            PostActivity.start(mContext,data.getId());
                        }
                    }
                });
        }else {
            FrescoUtils.displayRoundIcon(ivIcon,data.getLogo_img(),150,150);
            tvFollow.setText("#"+data.getCname()+"#");
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (data.getType().equals("1")){
                        TravelsDetailActivity.start(mContext,data.getId(),data.getTitle());
                    }else if (data.getType().equals("2")){
                        DeliciousDetailActivity.start(mContext,data.getId(),data.getTitle());
                    }else {
                        ActivateDetailActivity.start(mContext,data.getId());
                    }
                }
            });
        }

    }
}
