package com.example.administrator.travel.ui.me.messagecenter.appointmessage;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.holer.BaseHolder;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.CommonMessageBean;
import com.example.administrator.travel.ui.view.ShowAllTextView;
import com.example.administrator.travel.utils.FormatDateUtils;

import org.xutils.x;

import butterknife.BindColor;
import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/26 0026.
 */
public class AppointMessageHolder extends BaseHolder<CommonMessageBean.DataBean> {
    @BindView(R.id.iv_user_icon)
    ImageView mIvUserIcon;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_cat_more)
    TextView mTvCatMore;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_message)
    ShowAllTextView mTvMessage;
    @BindView(R.id.tv_time)
    TextView mTvTime;
    @BindView(R.id.iv_image)
    ImageView mIvImage;
    @BindView(R.id.tv_des)
    TextView mTvDes;
    @BindColor(R.color.Ffbf75)
    @ColorInt
    int yellow;
    @BindColor(R.color.otherTitleBg)
    @ColorInt
    int green;
    @BindColor(R.color.colorFf8076)
    @ColorInt
    int red;
    @BindColor(R.color.color97cb66)
    @ColorInt
    int green2;

    public AppointMessageHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CommonMessageBean.DataBean datas, Context mContext, int position) {
        mTvDes.setText(datas.getTitle_desc());
        mTvName.setText(datas.getNick_name());
        int color = green2;
        if (datas.getTitle().equals("报名了")) {
            color = green;
        } else if (datas.getTitle().equals("退出了")) {
            color = yellow;
        } else if (datas.getTitle().equals("拒绝了")) {
            color=red;
        } else if (datas.getTitle().equals("通过了")) {
            color=green2;
        } else if (datas.getTitle().equals("评论了")) {
            color=green2;
        } else if (datas.getTitle().equals("回复了")) {
          color=green;
        }
        mTvStatus.setTextColor(color);
        mTvStatus.setText(datas.getTitle());
        mTvMessage.setText(datas.getContent());
        x.image().bind(mIvUserIcon, datas.getUser_img());
        x.image().bind(mIvImage, datas.getImg());
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd HH:ss", datas.getReply_time()));
    }

    @Override
    public View initRootView(Context mContext) {
        return inflateView(R.layout.item_activity_appoint_message);
    }
}
