package com.yunspeak.travel.ui.me.mytheme;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.ui.adapter.holer.BaseHolder;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FormatDateUtils;

import org.xutils.x;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/3 0003.
 */
public class ThemeCommonHolder extends BaseHolder {


    @BindView(R.id.tv_delete) FontsIconTextView mTvDelete;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.tv_name)  TextView mTvName;
    @BindView(R.id.tv_content) TextView mTvContent;
    @BindView(R.id.iv_user_icon) ImageView mIvUserIcon;
    @BindView(R.id.tv_circle) TextView mTvCircle;
    @BindView(R.id.tv_discuss_number) TextView mTvDiscussNumber;
    @BindView(R.id.tv_love_number) TextView mTvLoveNumber;
    @BindView(R.id.diss) TextView mTvDiscuss;

    public ThemeCommonHolder(Context context) {
        super(context);

    }

    @Override
    protected void initItemDatas(Object data1, Context mContext, int position) {
        if (data1 instanceof MyPostBean.DataBean) {
            MyPostBean.DataBean datas=((MyPostBean.DataBean) data1);
            x.image().bind(mIvUserIcon, datas.getImg());
            mTvName.setText(datas.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss", datas.getAdd_time()));
            mTvLoveNumber.setText(datas.getCount_like());
            mTvCircle.setText("#" + datas.getCname() + "#");
            mTvContent.setText(datas.getContent());
            mTvDiscussNumber.setText(datas.getCount_reply());
        }else if (data1 instanceof MyPublishBean.DataBean){
            MyPublishBean.DataBean datas=((MyPublishBean.DataBean) data1);
            x.image().bind(mIvUserIcon, datas.getImg());
            mTvName.setText(datas.getTitle());
            mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:mm:ss", datas.getAdd_time()));
            mTvLoveNumber.setText(datas.getCount_like());
            mTvCircle.setText("#" + datas.getCname() + "#");
            mTvContent.setText(datas.getContent());
            mTvDiscuss.setVisibility(View.GONE);
            mTvDiscussNumber.setVisibility(View.GONE);
        }
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_fragment_my_theme);
    }
}