package com.yunspeak.travel.ui.me.othercenter.userdynamic;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.DynamicBean;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.appoint.together.togetherdetail.AppointTogetherDetailActivity;
import com.yunspeak.travel.ui.appoint.withme.withmedetail.AppointWithMeDetailActivity;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.AiteTextClick;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.find.findcommon.FindCommonActivity;
import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.DeliciousDetailActivity;
import com.yunspeak.travel.ui.find.findcommon.destinationdetail.DestinationDetailActivity;
import com.yunspeak.travel.ui.find.travels.TravelsActivity;
import com.yunspeak.travel.ui.find.travels.travelsdetail.TravelsDetailActivity;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.StringUtils;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public class UserDynamicAdapter extends BaseRecycleViewAdapter<DynamicBean> {


    public UserDynamicAdapter(List<DynamicBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }




    private void textClick(TextView text,CharSequence content,String name,String userId,Context context){
        if (content==null)return;
        int clickLength=name.trim().length()+1;
        String all="@"+name.trim()+":"+content;
        text.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannable = new SpannableStringBuilder(all);
        spannable.setSpan(new AiteTextClick(context,userId), 0,clickLength
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannable);

    }

    @Override
    public BaseRecycleViewHolder<DynamicBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserDynamicHolder(inflateView(R.layout.item_other_user_fragment_dynamic, parent));
    }


    class UserDynamicHolder extends BaseRecycleViewHolder<DynamicBean> {
        @BindView(R.id.iv_user_icon)SimpleDraweeView ivUserIcon;
        @BindView(R.id.tv_user_name) TextView tvUserName;
        @BindView(R.id.tv_time) TextView tvTime;
        @BindView(R.id.tv_reply_type) TextView tvReplyType;
        @BindView(R.id.tv_type) TextView tvType;
        @BindView(R.id.tv_discuss) TextView tvDiscuss;
        @BindView(R.id.tv_other_user) TextView tvOtherUser;
        @BindView(R.id.tv_icon) FontsIconTextView tvIcon;
        @BindView(R.id.tv_icon_number) TextView tvIconNumber;
        @BindColor(R.color.colorFf8076) @ColorInt int loveColor;
        @BindColor(R.color.colorb5b5b5) @ColorInt int normalColor;
        public UserDynamicHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void childBindView(int position, final DynamicBean moreBean, final Context mContext) {
            FrescoUtils.displayIcon(ivUserIcon,moreBean.getUser_img());
            tvUserName.setText(moreBean.getNick_name());
            tvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_DHms,moreBean.getTime()));
            tvReplyType.setText(moreBean.getType());
            tvType.setText("#"+moreBean.getCname()+"#");
            tvDiscuss.setText(AiteUtils.getSmiedTextWithAiteAndLinke(mContext,moreBean.getTitle(),moreBean.getInform(),moreBean.getReply_img()));

            tvType.setTag(moreBean.getCid());
            tvIconNumber.setText(moreBean.getLike_count());
            if (StringUtils.isEmpty(moreBean.getContent())){//没有content隐藏下面
                tvOtherUser.setVisibility(View.GONE);
            }else {
                tvOtherUser.setVisibility(View.VISIBLE);
                if (!StringUtils.isEmpty(moreBean.getRe_nick_name())) {//没有nickname为发现一级评论，显示content为发现出详细名字
                    textClick(tvOtherUser, AiteUtils.getSmiedTextWithAiteAndLinke(mContext, moreBean.getContent(), moreBean.getRe_inform(), moreBean.getRe_reply_img()), moreBean.getRe_nick_name(), moreBean.getRe_user_id(), mContext);
                } else {
                    tvOtherUser.setText(AiteUtils.getSmiedTextWithAiteAndLinke(mContext, moreBean.getContent(), moreBean.getRe_inform(), moreBean.getRe_reply_img()));
                }
            }
            final int fType = moreBean.getF_type();
            tvType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (fType){
                        case 0:
                            CircleDetailActivity.start(mContext,moreBean.getCid());
                            break;
                        case 1:
                            mContext.startActivity(new Intent(mContext,TravelsActivity.class));
                            break;
                        case 2:
                            FindCommonActivity.start(mContext,FindCommonActivity.DELICIOUS_NORMAL,0);
                            break;
                        case 3:
                            FindCommonActivity.start(mContext,FindCommonActivity.DESTINATION_NORMAL,0);
                            break;
                        case 4:
                            AppointTogetherDetailActivity.start(mContext,moreBean.getFid());
                            break;
                        case 5:
                            AppointWithMeDetailActivity.start(mContext,moreBean.getFid());
                            break;
                        default:
                            break;
                    }
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (fType){
                        case 0:
                            PostActivity.start(mContext,moreBean.getFid());
                            break;
                        case 1:
                            TravelsDetailActivity.start(mContext,moreBean.getFid(),"游记详情");
                            break;
                        case 2:
                            DeliciousDetailActivity.start(mContext,moreBean.getFid(),"美食详情");
                            break;
                        case 3:
                            DestinationDetailActivity.start(mContext,moreBean.getFid(),"目的地详情");
                            break;
                        case 4:
                            AppointTogetherDetailActivity.start(mContext,moreBean.getFid());
                            break;
                        case 5:
                            AppointWithMeDetailActivity.start(mContext,moreBean.getFid());
                            break;
                        default:
                            break;
                    }
                }
            });
            if (moreBean.getIs_like()!=null && moreBean.getIs_like().equals("1")){
                tvIconNumber.setTextColor(loveColor);
                tvIcon.setTextColor(loveColor);
                tvIcon.setText(mContext.getString(R.string.activity_circle_love_full));
            }else {
                tvIconNumber.setTextColor(normalColor);
                tvIcon.setTextColor(normalColor);
                tvIcon.setText(mContext.getString(R.string.activity_circle_love_empty));
            }
        }
    }
}
