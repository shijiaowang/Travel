package com.yunspeak.travel.ui.me.myappoint.memberdetail;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.greenrobot.eventbus.EventBus;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/7/6 0006.
 * 申请加入
 */
public class MemberEnterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    public static final  int TYPE_AGREE=100;
    public static final  int TYPE_RESUSE=101;
    private Context mContext;
    private List<MemberDetailBean.DataBean.JoinBean> mDatas;
    int okColor1=Color.parseColor("#5cd0c2");
    int okColor2=Color.parseColor("#Ffbf75");

    public MemberEnterAdapter(Context mContext, List<MemberDetailBean.DataBean.JoinBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public void setmDatas(List<MemberDetailBean.DataBean.JoinBean> mDatas) {
        this.mDatas = mDatas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(mContext).inflate(R.layout.item_activity_member_enter, parent, false);
        return new MemberDetailHolder(inflate);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MemberDetailHolder memberDetailHolder = (MemberDetailHolder) holder;
        MemberDetailBean.DataBean.JoinBean joinBean = mDatas.get(position);
        x.image().bind(memberDetailHolder.mIvIcon,joinBean.getUser_img());
        memberDetailHolder.mTvAge.setText(joinBean.getAge());
        memberDetailHolder.mTvSex.setText(joinBean.getSex().equals("1")?R.string.activity_member_detail_boy:R.string.activity_member_detail_girl);
        memberDetailHolder.mTvName.setText(joinBean.getNick_name());
        if (StringUtils.isEmpty(joinBean.getContent())){
            memberDetailHolder.mLlDiscuss.setVisibility(View.GONE);
        }else {
            memberDetailHolder.mLlDiscuss.setVisibility(View.VISIBLE);
            memberDetailHolder.mTvCatAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeShowWay(memberDetailHolder.mTvDiscuss,memberDetailHolder.mTvCatAll);
                }
            });
        }
        if (joinBean.getState().equals("1")) {
            memberDetailHolder.mTvDelete.setVisibility(View.VISIBLE);
            memberDetailHolder.mTvOk.setTextColor(okColor1);
            memberDetailHolder.mTvOkText.setVisibility(View.GONE);
            memberDetailHolder.mTvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    event(memberDetailHolder.getAdapterPosition(),"1",TYPE_AGREE);
                }
            });
        }else {
            memberDetailHolder.mTvOk.setClickable(false);
            memberDetailHolder.mTvOk.setTextColor(okColor2);
            memberDetailHolder.mTvOkText.setVisibility(View.VISIBLE);
            memberDetailHolder.mTvDelete.setVisibility(View.GONE);
        }
        memberDetailHolder.mTvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // event(position,"2", TYPE_RESUSE);
                MemBerDetailEvent memBerDetailEvent=new MemBerDetailEvent();
                memBerDetailEvent.setPosition(position);
                memBerDetailEvent.setType(TYPE_RESUSE);
                memBerDetailEvent.setIsSuccess(true);
                EventBus.getDefault().post(memBerDetailEvent);
            }
        });

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        //判断数据更改是否为空，说明是新增的，直接去绑定数据
        if (payloads == null || payloads.isEmpty()) {
            onBindViewHolder(holder,position);
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
            memberDetailHolder.mTvOk.setClickable(false);
            memberDetailHolder.mTvOk.setTextColor(okColor2);
            memberDetailHolder.mTvOkText.setVisibility(View.VISIBLE);
            memberDetailHolder.mTvDelete.setVisibility(View.GONE);
        }else {
            memberDetailHolder.mTvDelete.setVisibility(View.VISIBLE);
            memberDetailHolder.mTvOk.setTextColor(okColor1);
            memberDetailHolder.mTvOkText.setVisibility(View.GONE);
            memberDetailHolder.mTvOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   event(memberDetailHolder.getAdapterPosition(),"1",TYPE_AGREE);
                }
            });
        }
    }

    private void event(int position, String type, int typeAgree) {
        MemBerDetailEvent memBerDetailEvent=new MemBerDetailEvent();
        memBerDetailEvent.setPosition(position);
        Map<String, String> end = MapUtils.Build().addKey().addUserId().addId(mDatas.get(position).getId()).addType(type).end();
        XEventUtils.postUseCommonBackJson(IVariable.MY_APPOINT_AGREE_OR_REFUSE,end, typeAgree,memBerDetailEvent);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class MemberDetailHolder extends RecyclerView.ViewHolder {

         FontsIconTextView mTvOk;
         TextView mTvDelete;
         TextView mTvCatAll;
         TextView mTvDiscuss;
        TextView mTvOkText;
        ImageView mIvIcon;
        TextView mTvName;
        TextView mTvSex;
        TextView mTvAge;
       LinearLayout mLlDiscuss;

        public MemberDetailHolder(View itemView) {
            super(itemView);
            mTvOk = ((FontsIconTextView) itemView.findViewById(R.id.tv_ok));
            mTvDelete = ((TextView) itemView.findViewById(R.id.tv_delete));
            mTvCatAll = (TextView) itemView.findViewById(R.id.tv_cat_all);
            mTvDiscuss = (TextView) itemView.findViewById(R.id.tv_discuss);
            mTvOkText = (TextView) itemView.findViewById(R.id.tv_ok_text);
            mIvIcon= (ImageView) itemView.findViewById(R.id.iv_icon);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvSex = (TextView) itemView.findViewById(R.id.tv_sex);
            mTvAge = (TextView) itemView.findViewById(R.id.tv_age);
            mLlDiscuss = (LinearLayout) itemView.findViewById(R.id.ll_discuss);

        }
    }
    /**
     * 查看全部设置
     */
    private boolean isShowAllFlag=false;
    private void changeShowWay(TextView show,TextView upOrDown) {
        if (isShowAllFlag){
            show.setEllipsize(TextUtils.TruncateAt.END);
            show.setMaxLines(1);
            upOrDown.setText(mContext.getResources().getString(R.string.down));
        }else {
            show.setEllipsize(null);
            show.setMaxLines(Integer.MAX_VALUE);
            upOrDown.setText(mContext.getResources().getString(R.string.up));
        }
        isShowAllFlag=!isShowAllFlag;
    }


}
