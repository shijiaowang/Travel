package com.example.administrator.travel.ui.circle.hotpost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.FrescoUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/7/8 0008.
 */
public class CircleHotHolder extends BaseHolder<HotPostBean.DataBean> {
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_user_nick_name) TextView mTvNickName;
    @BindView(R.id.iv_user_icon) SimpleDraweeView mIvUserIcon;
    @BindView(R.id.tv_type) TextView mTvType;
    @BindView(R.id.tv_title) TextView mTvTitle;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.tv_zhan) TextView mTvZhan;
    @BindView(R.id.tv_zhan_number) TextView mTvZhanNumber;
    @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
    @BindView(R.id.ll_picture) LinearLayout mLlPicture;
    private final LayoutInflater inflater;

    public CircleHotHolder(Context context) {
        super(context);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected void initItemDatas(HotPostBean.DataBean circleHot, Context mContext, int position) {
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-mm-dd",circleHot.getTime()));
        mTvTitle.setText(circleHot.getTitle());
        mTvContent.setText(circleHot.getContent());
        FrescoUtils.displayIcon(mIvUserIcon,circleHot.getUser_img());
        mTvNickName.setText(circleHot.getNick_name());
        mTvDiscussNumber.setText(circleHot.getCount_reply());
        mTvZhanNumber.setText(circleHot.getCount_like());
        String forumImg = circleHot.getForum_img();
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
                ImageView imageView = (ImageView)inflater.inflate(R.layout.item_hot_post, mLlPicture, false);
                x.image().bind(imageView,url,getImageOptions(108,108));
                mLlPicture.addView(imageView);
            }
        }


    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_circle_hot);
    }
}
