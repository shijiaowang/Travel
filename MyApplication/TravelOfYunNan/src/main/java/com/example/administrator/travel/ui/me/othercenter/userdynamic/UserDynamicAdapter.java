package com.example.administrator.travel.ui.me.othercenter.userdynamic;

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

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.LoadMoreRecycleViewAdapter;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.CircleDetailActivity;
import com.example.administrator.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.example.administrator.travel.ui.me.othercenter.OtherUserCenterBean;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ShowAllTextView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.w3c.dom.Text;

import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public class UserDynamicAdapter extends LoadMoreRecycleViewAdapter<OtherUserCenterBean.DataBean.MoreBean> {


    public UserDynamicAdapter(List<OtherUserCenterBean.DataBean.MoreBean> mDatas, Context mContext) {
        super(mDatas, mContext);
    }

    @Override
    protected RecyclerView.ViewHolder normalHolder(LayoutInflater context, ViewGroup parent, int viewType) {
        View inflate = context.inflate(R.layout.item_other_user_fragment_dynamic, parent, false);
        return new UserDynamicHolder(inflate);
    }

    @Override
    protected void bindNormal(RecyclerView.ViewHolder holder, int position) {
        final UserDynamicHolder userDynamicHolder = (UserDynamicHolder) holder;
        final OtherUserCenterBean.DataBean.MoreBean moreBean = mDatas.get(position);
        FrescoUtils.displayIcon(userDynamicHolder.ivUserIcon,moreBean.getUser_img());
        userDynamicHolder.tvUserName.setText(moreBean.getNick_name());
        userDynamicHolder.tvTime.setText(FormatDateUtils.FormatLongTime(IVariable.Y_M_DHms,moreBean.getTime()));
        userDynamicHolder.tvReplyType.setText(moreBean.getType());
        userDynamicHolder.tvType.setText("#"+moreBean.getCname()+"#");
        userDynamicHolder.tvDiscuss.setText(moreBean.getTitle());
        userDynamicHolder.tvType.setTag(moreBean.getCid());
        userDynamicHolder.tvIconNumber.setText(moreBean.getCount_like());
        if (moreBean.getIs_like().equals("1")){
            userDynamicHolder.tvIconNumber.setTextColor(userDynamicHolder.loveColor);
            userDynamicHolder.tvIcon.setTextColor(userDynamicHolder.loveColor);
            userDynamicHolder.tvIcon.setText(mContext.getString(R.string.activity_circle_love_full));
        }else {
            userDynamicHolder.tvIconNumber.setTextColor(userDynamicHolder.normalColor);
            userDynamicHolder.tvIcon.setTextColor(userDynamicHolder.normalColor);
            userDynamicHolder.tvIcon.setText(mContext.getString(R.string.activity_circle_love_empty));
        }
        userDynamicHolder.tvOtherUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(mContext,moreBean.getId());
            }
        });
        userDynamicHolder.tvType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CircleDetailActivity.start(mContext,(String) userDynamicHolder.tvType.getTag());
            }
        });
        textClick(userDynamicHolder.tvOtherUser,moreBean.getContent(),moreBean.getRe_user());
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
    class UserDynamicHolder extends ItemViewHolder {
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
    }
}
