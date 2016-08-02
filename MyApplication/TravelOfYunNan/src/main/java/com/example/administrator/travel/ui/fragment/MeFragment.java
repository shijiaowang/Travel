package com.example.administrator.travel.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.CreatePostActivity;
import com.example.administrator.travel.ui.activity.FollowAndFanActivity;
import com.example.administrator.travel.ui.activity.MessageCenterActivity;
import com.example.administrator.travel.ui.activity.MyAlbumActivity;
import com.example.administrator.travel.ui.activity.MyAppointActivity;
import com.example.administrator.travel.ui.activity.SettingActivity;
import com.example.administrator.travel.ui.view.FlowLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/12 0012.
 */
public class MeFragment extends BaseFragment implements View.OnClickListener {
    public static final String FOLLOW_SELECT="follow_select";//进入关注

    private FlowLayout mFlLabel;//称号
    private List<String> titles = new ArrayList<>();
    private LayoutInflater inflater;
    private TextView mTvMessageCenter;
    private TextView mTvFollowName;
    private TextView mTvFollowNumber;
    private TextView mTvFanName;
    private TextView mTvFanNumber;
    private LinearLayout mLlAlbum;
    private LinearLayout mLlSetting;
    private ImageView mIvSetting;
    private TextView mTvAppoint;

    @Override
    protected int initLayoutRes() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        mFlLabel = (FlowLayout) root.findViewById(R.id.fl_label);
        inflater = LayoutInflater.from(getContext());
        mTvMessageCenter = (TextView) root.findViewById(R.id.tv_message_center);
        mTvFollowName = (TextView) root.findViewById(R.id.tv_follow_name);
        mTvAppoint = (TextView) root.findViewById(R.id.tv_appoint);
        mTvFollowNumber = (TextView) root.findViewById(R.id.tv_follow_number);
        mTvFanName = (TextView) root.findViewById(R.id.tv_fan_name);
        mTvFanNumber = (TextView) root.findViewById(R.id.tv_fan_number);
        mLlAlbum = (LinearLayout) root.findViewById(R.id.ll_album);
        mLlSetting = (LinearLayout) root.findViewById(R.id.ll_setting);
        mIvSetting = (ImageView) root.findViewById(R.id.iv_setting);


    }

    @Override
    protected void initData() {
        titles.clear();
        titles.add("我叫小红帽");
        titles.add("绝对老司机");
        titles.add("新手");
        titles.add("老司机引领时尚");
        titles.add("我叫小红帽");
        titles.add("绝对老司机");
        titles.add("新手");
        titles.add("老司机引领时尚");
        for (int i = 0; i < titles.size(); i++) {
            TextView mTvTitle = (TextView) inflater.inflate(R.layout.item_fragment_me_title, mFlLabel, false);
            mTvTitle.setText(titles.get(i));
            mFlLabel.addView(mTvTitle);
        }


    }

    @Override
    protected void initListener() {
        mTvMessageCenter.setOnClickListener(this);//消息中心
        mTvAppoint.setOnClickListener(this);//我的约伴
        mTvFollowName.setOnClickListener(this);//关注
        mTvFollowNumber.setOnClickListener(this);
        mTvFanName.setOnClickListener(this);//粉丝
        mTvFollowNumber.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);//设置
        mLlAlbum.setOnClickListener(this);//相册
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_message_center:
                getContext().startActivity(new Intent(getContext(), MessageCenterActivity.class));
                break;
            case R.id.tv_follow_name:
            case R.id.tv_follow_number:
                Intent followIntent=new Intent(getContext(), FollowAndFanActivity.class);
                followIntent.putExtra(FOLLOW_SELECT,true);
                startActivity(followIntent);
                break;
            case R.id.tv_fan_name:
            case R.id.tv_fan_number:
                Intent fanIntent=new Intent(getContext(), FollowAndFanActivity.class);
                fanIntent.putExtra(FOLLOW_SELECT,false);
                startActivity(fanIntent);
                break;
            case R.id.ll_album:
                startActivity(new Intent(getContext(), MyAlbumActivity.class));
                break;
            case R.id.iv_setting:
            case R.id.ll_setting:
                startActivity(new Intent(getContext(), SettingActivity.class));
                break;
            case R.id.tv_appoint:
                startActivity(new Intent(getContext(), MyAppointActivity.class));
                break;
        }
    }
}
