package com.yunspeak.travel.ui.me.me;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;

import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.me.level.LevelActivity;
import com.yunspeak.travel.ui.me.myhobby.UserLabelBean;
import com.yunspeak.travel.ui.me.setting.SettingActivity;
import com.yunspeak.travel.ui.me.userservice.CustomerServiceActivity;
import com.yunspeak.travel.ui.me.fansandfollow.FollowAndFanActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.me.identityauth.IdentityAuthenticationActivity;
import com.yunspeak.travel.ui.me.messagecenter.MessageCenterActivity;
import com.yunspeak.travel.ui.me.myalbum.MyAlbumActivity;
import com.yunspeak.travel.ui.me.myappoint.MyAppointActivity;
import com.yunspeak.travel.ui.me.mycollection.MyCollectionActivity;
import com.yunspeak.travel.ui.me.ordercenter.OrdersCenterActivity;

import com.yunspeak.travel.ui.me.myhobby.MyHobbyActivity;
import com.yunspeak.travel.ui.me.mytheme.MyThemeActivity;
import com.yunspeak.travel.ui.me.titlemanage.TitleManagementActivity;

import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayout;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/12 0012.
 * 个人
 */
public class MeFragment extends CropPhotoBaseFragment<MeEvent> implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener
{
    public static final String FOLLOW_SELECT = "follow_select";//进入关注
    private LayoutInflater inflater;
    @BindView(R.id.fl_label)
    FlexboxLayout mFlLabel;//称号
    @BindView(R.id.tv_message_center)
    TextView mTvMessageCenter;
    @BindView(R.id.tv_follow_name)
    TextView mTvFollowName;
    @BindView(R.id.tv_follow_number)
    TextView mTvFollowNumber;
    @BindView(R.id.tv_fan_name)
    TextView mTvFanName;
    @BindView(R.id.tv_fan_number)
    TextView mTvFanNumber;
    @BindView(R.id.ll_album)
    LinearLayout mLlAlbum;
    @BindView(R.id.ll_setting)
    LinearLayout mLlSetting;
    @BindView(R.id.tv_appoint)
    TextView mTvAppoint;
    @BindView(R.id.tv_my_collection)
    TextView mTvMyCollection;
    @BindView(R.id.tv_my_order)
    TextView mTvMyOrder;
    @BindView(R.id.ll_customer_center)
    LinearLayout mLlCustomerCenter;
    @BindView(R.id.tv_profile)
    TextView mTvProfile;
    @BindView(R.id.ll_identity)
    LinearLayout mLlIdentity;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwifeLayout;
    @BindView(R.id.tv_nick_name)
    TextView mTvNickName;
    @BindView(R.id.tv_title_edit)
    TextView mTvTitleEdit;
    @BindView(R.id.tv_level)
    TextView mTvLevel;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.ll_hobby) LinearLayout mLlHobby;
    @BindView(R.id.ll_theme) LinearLayout mLlTheme;
    @BindView(R.id.tv_setting) TextView mTvSetting;
    private int upType=-1;
    private static final int UP_BG=99;//上传背景
    private static final int UP_ICON=100;//上传头像


    @Override
    protected int initResLayout() {
        return R.layout.fragment_me;
    }


    @Override
    protected void initListener() {
        init();
        mTvMessageCenter.setOnClickListener(this);//消息中心
        mTvAppoint.setOnClickListener(this);//我的约伴
        mTvFollowName.setOnClickListener(this);//关注
        mTvFollowNumber.setOnClickListener(this);
        mTvFanName.setOnClickListener(this);//粉丝
        mTvFollowNumber.setOnClickListener(this);//关注
        mTvSetting.setOnClickListener(this);
        mLlSetting.setOnClickListener(this);//设置
        mLlAlbum.setOnClickListener(this);//相册
        mTvMyCollection.setOnClickListener(this);//我的收藏
        mTvMyOrder.setOnClickListener(this);//我的订单
        mLlCustomerCenter.setOnClickListener(this);//客服中心
        mLlIdentity.setOnClickListener(this);//身份认证
        mTvTitleEdit.setOnClickListener(this);//称号管理
        mTvLevel.setOnClickListener(this);//等级
        mIvIcon.setOnClickListener(this);//更换头像
        mIvBg.setOnClickListener(this);//设置背景
        mLlHobby.setOnClickListener(this);//我的兴趣
        mLlTheme.setOnClickListener(this);//我的主题
        mSwifeLayout.setOnRefreshListener(new MyRefreshListener());//刷新数据
        mIvIcon.setOnClickListener(this);//更改头像

    }

    private void init() {
        inflater = LayoutInflater.from(getContext());
        UserInfo userInfo = GlobalUtils.getUserInfo();
        if (userInfo == null) return;
        mTvNickName.setText(userInfo.getNick_name());
        if (StringUtils.isEmpty(userInfo.getContent())) {
            mTvProfile.setText("这个人很懒，什么都没有留下。。。");
        } else {
            mTvProfile.setText(userInfo.getContent());
        }
    }


    @Override
    protected String initUrl() {
        return IVariable.UPDATE_ME_MESSAGE;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_message_center:
                getContext().startActivity(new Intent(getContext(), MessageCenterActivity.class));
                break;
            case R.id.tv_follow_name:
            case R.id.tv_follow_number:
                Intent followIntent = new Intent(getContext(), FollowAndFanActivity.class);
                followIntent.putExtra(FOLLOW_SELECT, true);
                startActivity(followIntent);
                break;
            case R.id.tv_fan_name:
            case R.id.tv_fan_number:
                Intent fanIntent = new Intent(getContext(), FollowAndFanActivity.class);
                fanIntent.putExtra(FOLLOW_SELECT, false);
                startActivity(fanIntent);
                break;
            case R.id.ll_album:
                startActivity(new Intent(getContext(), MyAlbumActivity.class));
                break;
            case R.id.tv_setting:
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
            case R.id.tv_title_edit:
                startActivity(new Intent(getContext(), TitleManagementActivity.class));
                break;
            case R.id.tv_level:
                 startActivity(new Intent(getContext(), LevelActivity.class));
                break;
            case R.id.iv_bg:
                LinearLayout homeBottom = ((HomeActivity) getActivity()).getmLlBottom();
                upType = UP_BG;
                showPictureCutPop(homeBottom);
                break;
            case R.id.iv_icon:
                LinearLayout bottom = ((HomeActivity) getActivity()).getmLlBottom();
                upType = UP_ICON;
                showPictureCutPop(bottom);
                break;
            case R.id.ll_hobby:
                startActivity(new Intent(getContext(), MyHobbyActivity.class));
                break;
            case R.id.ll_theme:
                startActivity(new Intent(getContext(), MyThemeActivity.class));
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeActivity.REQ && resultCode == HomeActivity.RESULT) {
            ((HomeActivity) getActivity()).finish();
        }
    }

    @Override
    protected void setOptions(UCrop.Options options) {
        if (upType==UP_ICON){
            options.setCircleDimmedLayer(true);
            options.setAspectRatioOptions(0,new AspectRatio("0",1,1));
        }
    }

    @Override
    protected void childUpImage() {
        Map<String, String> bgImageMap = MapUtils.Build().addKey(getContext()).addUserId().end();
        if (StringUtils.isEmpty(fileName)) {
            ToastUtils.showToast("未找到图片");
        }
        List<String> flies=new ArrayList<>();
        flies.add(fileName);
        String url=upType==UP_BG?IVariable.CHANGE_BG:IVariable.CHANGE_USER_INFO;
        XEventUtils.postFileCommonBackJson(url,bgImageMap,flies,upType,new MeEvent());
    }

    @Override
    public void onSuccess(MeEvent meEvent) {
        switch (meEvent.getType()){
            case UP_BG:
            case UP_ICON:
                ToastUtils.showToast(meEvent.getMessage());
                break;
            case TYPE_REFRESH_BY_USER:
            case TYPE_REFRESH:
                mSwifeLayout.setRefreshing(false);
                dealRefreshData(meEvent);
                break;
        }

    }

    /**
     * 刷新个人数据
     * @param meEvent
     */
    private void dealRefreshData(MeEvent meEvent) {
        MeBean meBean = GsonUtils.getObject(meEvent.getResult(), MeBean.class);
        MeBean.DataBean data = meBean.getData();
        mTvFanNumber.setText(data.getFans());
        mTvFollowNumber.setText(data.getFollow());
        UserInfo user = data.getUser();
        FrescoUtils.displayIcon(mIvIcon, user.getUser_img());
        FrescoUtils.displayNormal(mIvBg, user.getBackground_img(),600,450,R.drawable.user_center_norbg);
        mTvNickName.setText(user.getNick_name());
        mTvProfile.setText(user.getContent());
        mTvLevel.setText("LV."+user.getLevel());
        List<UserLabelBean> user_label = data.getUser_label();
        mFlLabel.removeAllViews();
        if (user_label==null || user_label.size()==0){
            return;
        }
        for (UserLabelBean userLabelBean:user_label){

            TextView inflate = (TextView) inflater.inflate(R.layout.item_fragment_me_title, mFlLabel, false);
            inflate.setText(userLabelBean.getName());
            mFlLabel.addView(inflate);
        }

    }

    @Override
    public void onRefresh() {
        onLoad(TYPE_REFRESH);
    }

    /**
     * 下拉刷新
     */
   class MyRefreshListener implements SwipeRefreshLayout.OnRefreshListener {

       @Override
       public void onRefresh() {
         onLoad(TYPE_REFRESH);
       }
   }



    @Override
    protected void childViewShow(String s) {

         if (upType==UP_BG){
             FrescoUtils.displayNormal(mIvBg,s,600,450);
         }else {
             FrescoUtils.displayIcon(mIvIcon,s);
         }
    }

    @Override
    protected void onFail(MeEvent event) {
        if (event.getType()==TYPE_REFRESH_BY_USER) {
            mSwifeLayout.setRefreshing(false);
        }else {
            super.onFail(event);
        }

    }
}