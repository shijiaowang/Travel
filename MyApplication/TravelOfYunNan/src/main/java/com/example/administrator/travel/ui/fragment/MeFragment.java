package com.example.administrator.travel.ui.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.UserInfo;
import com.example.administrator.travel.ui.baseui.CustomerServiceActivity;
import com.example.administrator.travel.ui.baseui.FollowAndFanActivity;
import com.example.administrator.travel.ui.baseui.HomeActivity;
import com.example.administrator.travel.ui.baseui.IdentityAuthenticationActivity;
import com.example.administrator.travel.ui.baseui.MessageCenterActivity;
import com.example.administrator.travel.ui.baseui.MyAlbumActivity;
import com.example.administrator.travel.ui.baseui.MyAppointActivity;
import com.example.administrator.travel.ui.baseui.MyCollectionActivity;
import com.example.administrator.travel.ui.baseui.OrdersCenterActivity;
import com.example.administrator.travel.ui.baseui.OtherUserCenterActivity;
import com.example.administrator.travel.ui.baseui.SettingActivity;
import com.example.administrator.travel.ui.baseui.TitleManagementActivity;
import com.example.administrator.travel.ui.me.LevelActivity;
import com.example.administrator.travel.ui.view.FlowLayout;
import com.example.administrator.travel.utils.GlobalUtils;
import com.example.administrator.travel.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private TextView mTvMyCollection;
    private TextView mTvMyOrder;
    private LinearLayout mLlCustomerCenter;
    private TextView mTvProfile;
    private LinearLayout mLlIdentity;
    private TextView mTvNickName;
    private ImageView mIvTitleEdit;
    private TextView mTvLevel;

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
        mLlCustomerCenter = (LinearLayout) root.findViewById(R.id.ll_customer_center);
        mLlIdentity = (LinearLayout) root.findViewById(R.id.ll_identity);
        mIvSetting = (ImageView) root.findViewById(R.id.iv_setting);
        mTvMyCollection = (TextView) root.findViewById(R.id.tv_my_collection);
        mTvMyOrder = (TextView) root.findViewById(R.id.tv_my_order);
        mTvProfile = (TextView) root.findViewById(R.id.tv_profile);
        mTvNickName = (TextView) root.findViewById(R.id.tv_nick_name);
        mIvTitleEdit = (ImageView) root.findViewById(R.id.iv_title_edit);
        mTvLevel = (TextView) root.findViewById(R.id.tv_level);

        CircleImageView viewById = (CircleImageView) root.findViewById(R.id.civ_icon);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), OtherUserCenterActivity.class));
            }
        });
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
        UserInfo userInfo = GlobalUtils.getUserInfo();
        if (userInfo==null)return;
        mTvNickName.setText(userInfo.getNick_name());
        if (StringUtils.isEmpty(userInfo.getContent())){
            mTvProfile.setText("这个人很懒，什么都没有留下。。。");
        }else {
            mTvProfile.setText(userInfo.getContent());
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
        mTvMyCollection.setOnClickListener(this);//我的收藏
        mTvMyOrder.setOnClickListener(this);//我的订单
        mLlCustomerCenter.setOnClickListener(this);//客服中心
        mLlIdentity.setOnClickListener(this);//身份认证
        mIvTitleEdit.setOnClickListener(this);//称号管理
        mTvLevel.setOnClickListener(this);//等级
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
                Intent intent = new Intent(getContext(), SettingActivity.class);
               startActivityForResult(intent, HomeActivity.REQ);
                break;
            case R.id.tv_appoint:
                startActivity(new Intent(getContext(), MyAppointActivity.class));
                break;
            case R.id.tv_my_collection:
                startActivity(new Intent(getContext(), MyCollectionActivity.class));
                break;
            case R.id.tv_my_order:
                startActivity(new Intent(getContext(), OrdersCenterActivity.class));
                break;
            case R.id.ll_customer_center:
                startActivity(new Intent(getContext(), CustomerServiceActivity.class));
                break;
            case R.id.ll_identity:
                startActivity(new Intent(getContext(), IdentityAuthenticationActivity.class));
                break;
            case R.id.iv_title_edit:
                startActivity(new Intent(getContext(), TitleManagementActivity.class));
                break;
            case R.id.tv_level:
                startActivity(new Intent(getContext(), LevelActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==HomeActivity.REQ && resultCode==HomeActivity.RESULT){
            ((HomeActivity) getActivity()).finish();
        }
    }
}
