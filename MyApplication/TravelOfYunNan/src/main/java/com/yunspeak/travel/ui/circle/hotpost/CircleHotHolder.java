package com.yunspeak.travel.ui.circle.hotpost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.HotPostBean;
import com.yunspeak.travel.ui.adapter.holer.BaseRecycleViewHolder;
import com.yunspeak.travel.ui.circle.circlenav.circledetail.post.PostActivity;
import com.yunspeak.travel.utils.AiteUtils;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/8 0008.
 */
public class CircleHotHolder extends BaseRecycleViewHolder<HotPostBean.DataBean> {
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_user_nick_name) TextView mTvNickName;
    @BindView(R.id.iv_user_icon) SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_content)
    TextView mTvContent;
    @BindView(R.id.tv_zhan) TextView mTvZhan;
    @BindView(R.id.tv_zhan_number) TextView mTvZhanNumber;
    @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
    @BindView(R.id.ll_picture) LinearLayout mLlPicture;
    private LayoutInflater inflater;

    public CircleHotHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void childBindView(int position, final HotPostBean.DataBean circleHot, final Context mContext) {
        if (inflater==null) {
            inflater = LayoutInflater.from(mContext);
        }
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-MM-dd",circleHot.getTime()));
        mTvTitle.setText(circleHot.getTitle());
        if (StringUtils.isEmpty(circleHot.getTitle())){
            mTvTitle.setVisibility(View.GONE);
        }else {
            mTvTitle.setVisibility(View.VISIBLE);
            AiteUtils.parseTextMessage(mTvTitle, null, circleHot.getTitle(),false);
        }
        AiteUtils.parseTextMessage(mTvContent,circleHot.getInform(),circleHot.getContent(),false);
        FrescoUtils.displayIcon(mIvUserIcon,circleHot.getUser_img());
        mTvNickName.setText(circleHot.getNick_name());
        mTvDiscussNumber.setText(circleHot.getCount_reply());
        mTvZhanNumber.setText(circleHot.getCount_like());
        String forumImg = circleHot.getForum_img();
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostActivity.start(mContext,circleHot.getId());
            }
        });
        if (StringUtils.isEmpty(forumImg)){
            mLlPicture.setVisibility(View.GONE);
        }else {
            mLlPicture.setVisibility(View.VISIBLE);
            String[] images = forumImg.split(",");
            int newSize;
            //服务器可能返回超过三张
            String[] newImages=new String[newSize=images.length>3?3:images.length];
            System.arraycopy(images,0,newImages,0,newSize);
            for (String url:newImages){
                SimpleDraweeView imageView = (SimpleDraweeView) inflater.inflate(R.layout.item_hot_post, mLlPicture, false);
                FrescoUtils.displayNormal(imageView,url);
                mLlPicture.addView(imageView);
            }
        }

    }
}
