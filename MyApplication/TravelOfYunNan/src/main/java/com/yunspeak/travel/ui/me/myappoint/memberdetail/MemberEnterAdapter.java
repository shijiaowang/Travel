package com.yunspeak.travel.ui.me.myappoint.memberdetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 申请加入
 */
public class MemberEnterAdapter extends BaseRecycleViewAdapter<MemberDetailBean.DataBean.JoinBean> {
    public static final int TYPE_AGREE = 100;
    public static final int TYPE_RESUSE = 101;
    int okColor1 = Color.parseColor("#5cd0c2");
    int okColor2 = Color.parseColor("#Ffbf75");

    public MemberEnterAdapter(List<MemberDetailBean.DataBean.JoinBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }



    @Override
    public void onBindViewHolder(BaseRecycleViewHolder<MemberDetailBean.DataBean.JoinBean> holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        //判断数据更改是否为空，说明是新增的，直接去绑定数据
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder, position);
            return;
        }
        if (!(holder instanceof MemberDetailHolder)) {
            return;
        }
        //如果不为空，说明有部分数据发生了更改，那么只要根据数据去更新变更的UI即可
        final MemberDetailHolder memberDetailHolder = (MemberDetailHolder) holder;
        Bundle bundle = (Bundle) payloads.get(0);
        String content = bundle.getString(IVariable.DATA);
        if (content.equals("2")) {
            memberDetailHolder.tvOk.setClickable(false);
            memberDetailHolder.tvOk.setTextColor(okColor2);
            memberDetailHolder.tvOkText.setVisibility(View.VISIBLE);
            memberDetailHolder.tvDelete.setVisibility(View.GONE);
        } else {
            memberDetailHolder.tvDelete.setVisibility(View.VISIBLE);
            memberDetailHolder.tvOk.setTextColor(okColor1);
            memberDetailHolder.tvOkText.setVisibility(View.GONE);
            memberDetailHolder.tvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    event(memberDetailHolder.getAdapterPosition(), "1", TYPE_AGREE);
                }
            });
        }
    }


    private void event(int position, String type, int typeAgree) {
        MemBerDetailEvent memBerDetailEvent = new MemBerDetailEvent();
        memBerDetailEvent.setPosition(position);
        Map<String, String> end = MapUtils.Build().addKey().addUserId().addId(mDatas.get(position).getId()).addType(type).end();
        XEventUtils.postUseCommonBackJson(IVariable.MY_APPOINT_AGREE_OR_REFUSE, end, typeAgree, memBerDetailEvent);
    }

    @Override
    public BaseRecycleViewHolder<MemberDetailBean.DataBean.JoinBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MemberDetailHolder(inflateView(R.layout.item_activity_member_enter, parent));
    }




    public class MemberDetailHolder extends BaseRecycleViewHolder<MemberDetailBean.DataBean.JoinBean> {
        int okColor1 = Color.parseColor("#5cd0c2");
        int okColor2 = Color.parseColor("#Ffbf75");
        @BindView(R.id.iv_icon)
        SimpleDraweeView ivIcon;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_sex)
        FontsIconTextView tvSex;
        @BindView(R.id.tv_age)
        TextView tvAge;
        @BindView(R.id.tv_ok_text)
        TextView tvOkText;
        @BindView(R.id.tv_ok)
        FontsIconTextView tvOk;
        @BindView(R.id.tv_delete)
        FontsIconTextView tvDelete;
        @BindView(R.id.tv_discuss)
        TextView tvDiscuss;
        @BindView(R.id.tv_cat_all)
        TextView tvCatAll;
        @BindView(R.id.ll_discuss)
        LinearLayout llDiscuss;
        @BindView(R.id.tv_time)
        TextView tvTime;
        @BindView(R.id.v_hide_line)
        View mVHideLine;

        public MemberDetailHolder(View itemView) {
            super(itemView);
        }


        @Override
        public void childBindView(final int position, final MemberDetailBean.DataBean.JoinBean joinBean, final Context mContext) {
            FrescoUtils.displayIcon(ivIcon, joinBean.getUser_img());
            String age = joinBean.getAge();
            if (StringUtils.isEmpty(age) || age.equals("0")){
                tvAge.setText("保密");
            }else {
                tvAge.setText(age);
            }

            tvSex.setText(joinBean.getSex().equals("1") ? R.string.activity_member_detail_boy : R.string.activity_member_detail_girl);
            tvName.setText(joinBean.getNick_name());
            tvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss",joinBean.getAdd_time()));
            if (position==mDatas.size()-1){
                mVHideLine.setVisibility(View.GONE);
            }else {
                mVHideLine.setVisibility(View.VISIBLE);
            }
            if (StringUtils.isEmpty(joinBean.getContent())) {
                llDiscuss.setVisibility(View.GONE);
            } else {
                llDiscuss.setVisibility(View.VISIBLE);
                tvCatAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        changeShowWay(tvDiscuss, tvCatAll);
                    }
                });
            }
            if (joinBean.getState().equals("1")) {
                tvDelete.setVisibility(View.VISIBLE);
                tvOk.setTextColor(okColor1);
                tvOkText.setVisibility(View.GONE);
                tvOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        event(getAdapterPosition(), "1", TYPE_AGREE);
                    }
                });
            } else {
                tvOk.setClickable(false);
                tvOk.setTextColor(okColor2);
                tvOkText.setVisibility(View.VISIBLE);
                tvDelete.setVisibility(View.GONE);
            }
            ivIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OtherUserCenterActivity.start(mContext,ivIcon,joinBean.getUser_id());
                }
            });
            tvDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // event(position,"2", TYPE_RESUSE);
                    MemBerDetailEvent memBerDetailEvent = new MemBerDetailEvent();
                    memBerDetailEvent.setPosition(position);
                    memBerDetailEvent.setType(TYPE_RESUSE);
                    memBerDetailEvent.setIsSuccess(true);
                    EventBus.getDefault().post(memBerDetailEvent);
                }
            });
        }

    }
    /**
     * 查看全部设置
     */
    private boolean isShowAllFlag = false;

    private void changeShowWay(TextView show, TextView upOrDown) {
        if (isShowAllFlag) {
            show.setEllipsize(TextUtils.TruncateAt.END);
            show.setMaxLines(1);
            upOrDown.setText(mContext.getResources().getString(R.string.down));
        } else {
            show.setEllipsize(null);
            show.setMaxLines(Integer.MAX_VALUE);
            upOrDown.setText(mContext.getResources().getString(R.string.up));
        }
        isShowAllFlag = !isShowAllFlag;
    }


}
