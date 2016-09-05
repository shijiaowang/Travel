package com.example.administrator.travel.ui.adapter.holer;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.CircleDetail;
import com.example.administrator.travel.ui.adapter.CircleDetailPhotoAdapter;
import com.example.administrator.travel.ui.view.FontsIconTextView;
import com.example.administrator.travel.ui.view.ToShowAllGridView;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.StringUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/11 0011.
 */
public class CircleDetailHolder extends BaseHolder<CircleDetail.DataBean.BodyBean> {
    @ViewInject(R.id.iv_user_icon)
    private ImageView mIvUserIcon;
    @ViewInject(R.id.tv_user_nick_name)
    private TextView mTvUserNickName;
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
    @ViewInject(R.id.gv_photo)
    private ToShowAllGridView mGvPhoto;
    private CircleDetailPhotoAdapter circleDetailPhotoAdapter;
    private List<String> list;
    @ViewInject(R.id.tv_icon_love)
    public FontsIconTextView mTvIconLove;

    public CircleDetailHolder(Context context) {
        super(context);
    }

    @Override
    protected void initItemDatas(CircleDetail.DataBean.BodyBean datas, Context mContext, int position) {
        if (datas == null) {
            return;
        }
        x.image().bind(mIvUserIcon, datas.getUser_img());
        mTvDiscussNumber.setText(datas.getCount_reply());
        mTvLoveNumber.setText(datas.getCount_like());
        if (StringUtils.isEmpty(datas.getTitle())) {
            mTvTitle.setVisibility(View.GONE);
        } else {
            mTvTitle.setVisibility(View.VISIBLE);
            mTvTitle.setText(datas.getTitle());
        }
        mTvIconLove.setTextColor(datas.getIs_like().equals("1")?mContext.getResources().getColor(R.color.otherFf7f6c):mContext.getResources().getColor(R.color.colorA1a1a1));
        mTvLoveNumber.setTextColor(datas.getIs_like().equals("1")?mContext.getResources().getColor(R.color.otherFf7f6c):mContext.getResources().getColor(R.color.colorA1a1a1));
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy-M-dd HH:mm", datas.getTime()));
        mTvUserNickName.setText(datas.getNick_name());
        mTvContent.setText(datas.getContent());
        String imageUrl = datas.getForum_img();
        if (!StringUtils.isEmpty(imageUrl)) {
            mGvPhoto.setVisibility(View.VISIBLE);
            mGvPhoto.setCanClick(false);//不消费事件
            String[] split = imageUrl.split(",");
            list = new ArrayList<>();
            list.clear();
            int size=initSize(split.length);
            initGvHeight(size);
            for (int i = 0; i < size; i++) {
                list.add(split[i]);
            }
            setData(mContext, list);
        } else {
            mGvPhoto.setVisibility(View.GONE);
        }
    }

    private int initSize(int length) {
        int size=length;
        if (length>6){
            size=6;
        }
        return size;
    }

    private void initGvHeight(int length) {
       int height;
        if (length <= 3) {
            height= DensityUtil.dip2px(101);//图片高加距离
        }else {
            height= DensityUtil.dip2px(202);//最多6个
        }
        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,height);
        layoutParams.leftMargin= DensityUtil.dip2px(13);
        layoutParams.rightMargin= DensityUtil.dip2px(13);
        mGvPhoto.setLayoutParams(layoutParams);
    }

    private void setData(Context mContext, List<String> list) {
        if (circleDetailPhotoAdapter == null) {
            circleDetailPhotoAdapter = new CircleDetailPhotoAdapter(mContext, list);
            mGvPhoto.setAdapter(circleDetailPhotoAdapter);
        }
        circleDetailPhotoAdapter.notifyData(list);
    }


    @Override
    public View initRootView(Context mContext) {
        View inflate = inflateView(R.layout.item_activity_circle);
        return inflate;
    }
}
