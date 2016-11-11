package com.yunspeak.travel.ui.me.othercenter.userdynamic;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.baseui.BaseRecycleViewAdapter;
import com.yunspeak.travel.ui.baseui.LoadMoreRecycleViewAdapter;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.ui.me.othercenter.OtherUserCenterBean;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.ui.view.ShowAllTextView;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public class UserDynamicAdapter extends BaseRecycleViewAdapter<OtherUserCenterBean.DataBean.MoreBean> {


    public UserDynamicAdapter(List<OtherUserCenterBean.DataBean.MoreBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }




    private void textClick(TextView text,String content,String name){
        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(content))return;
        int clickLength=name.trim().length()+1;
        String all="@"+name.trim()+":"+content;
        text.setMovementMethod(LinkMovementMethod.getInstance());
        SpannableStringBuilder spannable = new SpannableStringBuilder(all);
        spannable.setSpan(new ClickText("1"), 0,clickLength
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setText(spannable);

    }

    @Override
    public BaseRecycleViewHolder<OtherUserCenterBean.DataBean.MoreBean> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new UserDynamicHolder(inflateView(R.layout.item_other_user_fragment_dynamic, parent));
    }

    class ClickText extends ClickableSpan{
        private String userId;

        public ClickText(String userId) {
            this.userId = userId;
        }

        @Override
        public void onClick(View widget) {

        }
        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setColor(mContext.getResources().getColor(R.color.Ffbf75));
            ds.setUnderlineText(false);//是否有下划线
        }
    }
    class UserDynamicHolder extends BaseRecycleViewHolder<OtherUserCenterBean.DataBean.MoreBean> {
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
        public void childBindView(int position, final OtherUserCenterBean.DataBean.MoreBean moreBean, final Context mContext) {
            FrescoUtils.displayIcon(ivUserIcon,moreBean.getUser_img());
            tvUserName.setText(moreBean.getNick_name());
            tvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_DHms,moreBean.getTime()));
            tvReplyType.setText(moreBean.getType());
            tvType.setText("#"+moreBean.getCname()+"#");
            tvDiscuss.setText(moreBean.getTitle());
            tvType.setTag(moreBean.getCid());
            tvIconNumber.setText(moreBean.getCount_like());
            if (moreBean.getIs_like().equals("1")){
                tvIconNumber.setTextColor(loveColor);
                tvIcon.setTextColor(loveColor);
                tvIcon.setText(mContext.getString(R.string.activity_circle_love_full));
            }else {
                tvIconNumber.setTextColor(normalColor);
                tvIcon.setTextColor(normalColor);
                tvIcon.setText(mContext.getString(R.string.activity_circle_love_empty));
            }
            tvOtherUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PostActivity.start(mContext,moreBean.getId());
                }
            });
            tvType.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CircleDetailActivity.start(mContext,(String) tvType.getTag());
                }
            });
            textClick(tvOtherUser,moreBean.getContent(),moreBean.getRe_user());
        }
    }
}
