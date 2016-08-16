package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.Circle;
import com.example.administrator.travel.bean.CircleDetail;
import com.example.administrator.travel.utils.FontsIconUtil;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.StringUtils;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CircleHolder extends BaseHolder<CircleDetail.DataBean.BodyBean> {
    @ViewInject(R.id.iv_user_icon)
    private ImageView mIvUserIcon;
    @ViewInject(R.id.tv_user_nike_name)
    private TextView mTvUserNikeName;
    @ViewInject(R.id.tv_time)
    private TextView mTvTime;
    @ViewInject(R.id.tv_love_number)
    private TextView mTvLoveNumber;
    @ViewInject(R.id.tv_title)
    private TextView mTvTitle;
    @ViewInject(R.id.tv_discuss_number)
    private TextView mTvDiscussNumber;
    @ViewInject(R.id.tv_content)
    private TextView mTvContent;

    public CircleHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleDetail.DataBean.BodyBean datas, Context mContext) {
         if (datas==null){
             return;
         }
        x.image().bind(mIvUserIcon, datas.getUser_img());
        mTvDiscussNumber.setText(datas.getCount_reply());
        mTvLoveNumber.setText(datas.getCount_like());
        mTvUserNikeName.setText(datas.getNick_name());
        if (StringUtils.isEmpty(datas.getTitle())){
            mTvTitle.setVisibility(View.GONE);
        }else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(datas.getTitle());
        }
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-M-dd HH:mm",datas.getTime()));
        mTvContent.setText(datas.getContents());
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_circle);
        TextView mTvIconLove = FontsIconUtil.findIconFontsById(R.id.tv_icon_love, mContext, inflate);
        TextView mTvIconDiscuss = FontsIconUtil.findIconFontsById(R.id.tv_icon_discuss, mContext, inflate);
        return inflate;
    }
}
