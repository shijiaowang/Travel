package com.yunspeak.travel.ui.me.othercenter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.ColorInt;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.domain.UserInfo;
import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.AppBarStateChangeListener;
import com.yunspeak.travel.ui.baseui.BaseChangeBarColorActivity;
import com.yunspeak.travel.ui.me.myappoint.chat.ChatActivity;
import com.yunspeak.travel.ui.me.myhobby.UserLabelBean;
import com.yunspeak.travel.ui.me.othercenter.useralbum.AppBarStateEvent;
import com.yunspeak.travel.ui.me.othercenter.useralbum.OtherAlbumBean;
import com.yunspeak.travel.ui.me.othercenter.useralbum.OtherAlbumEvent;
import com.yunspeak.travel.ui.me.othercenter.useralbum.OtherCenterAlbumFragment;
import com.yunspeak.travel.ui.me.othercenter.userdynamic.UserDynamicFragment;
import com.yunspeak.travel.ui.me.othercenter.userinfo.UserInfoBean;
import com.yunspeak.travel.ui.me.othercenter.userinfo.UserInfoFragment;
import com.yunspeak.travel.ui.view.FontsIconTextView;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.android.flexbox.FlexboxLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindColor;
import butterknife.BindView;


/**
 * Created by wangyang on 2016/7/13 0013.
 * 他人中心
 */
public class OtherUserCenterActivity extends BaseChangeBarColorActivity<OtherUserCenterEvent> implements View.OnClickListener {


    private String[] mTitles = new String[]{"动态", "相册", "个人"};
    private boolean isInflate = false;
    private boolean viewPageIsInflate = false;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (isInflate) {
                return;
            }
            mHandler.removeCallbacksAndMessages(null);//移除所有的参数和消息
            mPbLoad.clearAnimation();
            mPbLoad.setVisibility(View.GONE);
            switch (msg.what) {
                case 0:
                    mFlTitle.setVisibility(View.VISIBLE);
                    isInflate = true;
                    break;
                case 1:
                    mLlNewUser.setVisibility(View.VISIBLE);
                    isInflate = true;
                    break;
            }
        }
    };

    private int i = -1;
    private LayoutInflater inflater;
    private AnimationSet animationSet;
    @BindView(R.id.pb_load)
    View mPbLoad;
    @BindView(R.id.tv_private_icon)
    FontsIconTextView mTvPrivateIcon;
    @BindView(R.id.tv_follow_icon)
    FontsIconTextView mTvFollowIcon;
    @BindView(R.id.tv_follow)
    TextView mTvFollow;
    @BindView(R.id.ll_private)
    LinearLayout mLlPrivate;
    @BindView(R.id.tv_user_nick_name)
    TextView tvUserNickNmae;
    @BindView(R.id.ll_follow)
    LinearLayout mLlFollow;
    @BindView(R.id.tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.vp_dynamic)
    ViewPager mVpDynamic;
    @BindView(R.id.fl_title)
    FlexboxLayout mFlTitle;
    @BindView(R.id.ll_new_user)
    LinearLayout mLlNewUser;
    @BindView(R.id.iv_bg)
    SimpleDraweeView ivBg;
    @BindView(R.id.iv_icon)
    SimpleDraweeView ivIcon;
    @BindView(R.id.tv_level)
    TextView tvLevel;
    @BindView(R.id.tv_fan_sum)
    TextView tvFanSum;
    @BindView(R.id.tv_signature)
    TextView tvSignature;
    @BindColor(R.color.colorFAFAFA)
    @ColorInt
    int mBarColor;
    private List<INotify> fragments;
    private String userId;
    private int isFans = -1;
    private int mCurrentPage = 0;
    private UserInfo userInfo;


    @Override
    protected void initHeader() {
        mVsHeader.setLayoutResource(R.layout.activity_other_top);
        mVsHeader.inflate();
        mVsTab.setLayoutResource(R.layout.tab_layout);
        mVsTab.inflate();
        inflater = LayoutInflater.from(this);
        userId = getIntent().getStringExtra(IVariable.USER_ID);

    }

    @Override
    protected int initContent() {
        return R.layout.view_pager;
    }

    @Override
    protected String initTitle() {
        return "个人中心";
    }

    @Override
    protected void initListener() {
        if (userId.equals(GlobalUtils.getUserInfo().getId())){
            mLlPrivate.setVisibility(View.GONE);
            mLlFollow.setVisibility(View.GONE);
        }else {
            mLlFollow.setOnClickListener(this);
            mLlPrivate.setOnClickListener(this);
        }
        mVpDynamic.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
        builder.addMyId().add(IVariable.USER_ID, userId).addType((mCurrentPage + 1) + "").addPageSize().addCount(0);
    }

    @Override
    protected String initUrl() {
        return IVariable.OTHER_USER_INFO;
    }

    @Override
    protected void onSuccess(OtherUserCenterEvent otherUserCenterEvent) {
        switch (otherUserCenterEvent.getType()) {
            case TYPE_REFRESH:
                dealInfoData(otherUserCenterEvent);
                break;
            case TYPE_UPDATE:
                isFans = isFans == 0 ? 1 : 0;
                ToastUtils.showToast(otherUserCenterEvent.getMessage());
                dealFanOrNotFan(isFans);
                break;
        }

    }

    /**
     * 处理基础信息
     *
     * @param otherUserCenterEvent
     */
    private void dealInfoData(OtherUserCenterEvent otherUserCenterEvent) {
        switch (mCurrentPage) {
            case 0:
                dynamicRefresh(otherUserCenterEvent);
                break;
            case 1:
                otherAlbumRefresh(otherUserCenterEvent);
                break;
            case 2:
                userInfoRefresh(otherUserCenterEvent);
                break;
        }
    }

    /**
     * 刷新相册
     * @param otherUserCenterEvent
     */
    private void otherAlbumRefresh(OtherUserCenterEvent otherUserCenterEvent) {
        OtherAlbumBean otherAlbumBean=GsonUtils.getObject(otherUserCenterEvent.getResult(),OtherAlbumBean.class);
        UserBean user = otherAlbumBean.getData().getUser();
        initUserInfo(user);
        List<UserLabelBean> userLabel = otherAlbumBean.getData().getUser_label();
        initTitle(userLabel);
        fragments.get(mCurrentPage).notifys(otherAlbumBean.getData().getMore());
    }

    /**
     * 个人信息刷新
     * @param otherUserCenterEvent
     */
    private void userInfoRefresh(OtherUserCenterEvent otherUserCenterEvent) {
        UserInfoBean userInfoBean = GsonUtils.getObject(otherUserCenterEvent.getResult(), UserInfoBean.class);
        UserBean user = userInfoBean.getData().getUser();
        initUserInfo(user);
        List<UserLabelBean> userLabel = userInfoBean.getData().getUser_label();
        initTitle(userLabel);
        fragments.get(mCurrentPage).notify(user);

    }

    /**
     * 刷新动态
     *
     * @param otherUserCenterEvent
     */
    private void dynamicRefresh(OtherUserCenterEvent otherUserCenterEvent) {
        if (!viewPageIsInflate || mCurrentPage == 0) {
            OtherUserCenterBean otherUserCenterBean = GsonUtils.getObject(otherUserCenterEvent.getResult(), OtherUserCenterBean.class);
            List<UserLabelBean> userLabel = otherUserCenterBean.getData().getUser_label();
            initTitle(userLabel);
            UserBean user = otherUserCenterBean.getData().getUser();
            initUserInfo(user);
            if (!viewPageIsInflate) {
                viewPageIsInflate = true;
                fragments = new ArrayList<>();
                UserDynamicFragment dynamicFragment = UserDynamicFragment.newInstance(userId, otherUserCenterBean.getData().getMore());
                UserInfoFragment userInfoFragment = UserInfoFragment.newInstance(userId);
                OtherCenterAlbumFragment otherCenterAlbumFragment = OtherCenterAlbumFragment.newInstance(userId);
                fragments.add(dynamicFragment);
                fragments.add(otherCenterAlbumFragment);
                fragments.add(userInfoFragment);
                PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager());
                mVpDynamic.setAdapter(adapter);
                mVpDynamic.setOffscreenPageLimit(3);
                mTabLayout.setupWithViewPager(mVpDynamic);
            } else {
                fragments.get(mCurrentPage).notifys(otherUserCenterBean.getData().getMore());
            }
        }
    }

    /**
     * 填充用户信息
     *
     * @param user
     */
    private void initUserInfo(UserBean user) {
        userInfo = new UserInfo();
        userInfo.setId(userId);
        userInfo.setNick_name(user.getNick_name());
        userInfo.setUser_img(user.getUser_img());
        isFans = user.getIs_fans();
        dealFanOrNotFan(isFans);
        String nickName = user.getNick_name();
        mTvTitle.setText(nickName);
        tvSignature.setText(user.getContent());
        FrescoUtils.displayIcon(ivIcon, user.getUser_img());
        FrescoUtils.displayNormal(ivBg, user.getBackground_img());
        tvFanSum.setText("粉丝:" + user.getFans());
        tvLevel.setText("Lv." + user.getLevel());
        tvUserNickNmae.setText(user.getNick_name());

    }

    /**
     * 处理是否是粉丝按钮逻辑
     *
     * @param isFans
     */
    private void dealFanOrNotFan(int isFans) {
        if (isFans == 1) {
            mTvFollowIcon.setText(R.string.activity_other_followed);
            mTvFollow.setText("已关注");
        } else {
            mTvFollowIcon.setText(R.string.activity_my_album_add);
            mTvFollow.setText("关注");
        }

    }

    /**
     * 設置用戶標籤
     *
     * @param userLabel
     */
    private void initTitle(List<UserLabelBean> userLabel) {
        isInflate = false;
        if (userLabel != null && userLabel.size() > 0) {
            mFlTitle.removeAllViews();
            mFlTitle.setVisibility(View.GONE);
            for (UserLabelBean userLabelBean : userLabel) {
                TextView textView = (TextView) inflater.inflate(R.layout.item_activity_other_title_item, mFlTitle, false);
                textView.setText(userLabelBean.getName());
                mFlTitle.addView(textView);
            }
            i = 0;
            mFlTitle.setVisibility(View.INVISIBLE);
            mLlNewUser.setVisibility(View.GONE);
        } else {
            mLlNewUser.setVisibility(View.INVISIBLE);
            mFlTitle.setVisibility(View.GONE);
            i = 1;
        }
        initAnimation();
        mPbLoad.setVisibility(View.VISIBLE);
        mPbLoad.startAnimation(animationSet);
        mHandler.sendEmptyMessageDelayed(i, 1500);
    }

    /**
     * 動畫
     */
    private void initAnimation() {
        animationSet = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        alphaAnimation.setDuration(1500);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 0.8f, 0.0f, 0.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1500);
        scaleAnimation.setFillAfter(true);
        //
        RotateAnimation rotateAnimation = new RotateAnimation(0, 8888, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation.setDuration(10000);
        rotateAnimation.setFillAfter(true);


        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.addAnimation(rotateAnimation);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_follow:
                followOrCancel();
                break;
            case R.id.ll_private:
                ChatActivity.start(this,userId,userId, EaseConstant.CHATTYPE_SINGLE,userInfo);
                break;
        }
    }

    private void followOrCancel() {
        String type = "";
        switch (isFans) {
            case -1:
                ToastUtils.showToast("出现了不明错误");
                return;
            case 0://取消关注
                type = "2";
                break;
            case 1:
                type = "1";
                break;
        }
        Map<String, String> followMap = MapUtils.Build().addKey(this).addUserId().add("u_id", userId).addType(type).end();
        XEventUtils.postUseCommonBackJson(IVariable.FOLLOW_OR_CANCEL_FOLLOW, followMap, TYPE_UPDATE, new OtherUserCenterEvent());
    }


    class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return (Fragment) fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    public static void start(Context context, View v, String id) {
        if (StringUtils.isEmpty(id))return;
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(v, v.getWidth() / 2, v.getHeight() / 2, 0, 0);
        Intent intent = new Intent(context, OtherUserCenterActivity.class);
        intent.putExtra(IVariable.USER_ID, id);
        ActivityCompat.startActivity(((Activity) context), intent, compat.toBundle());
    }

    public static void start(Context context, String id) {
        if (StringUtils.isEmpty(id))return;
        try {
            if (id.equals(GlobalUtils.getUserInfo().getId()))return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, OtherUserCenterActivity.class);
        intent.putExtra(IVariable.USER_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected void appBarStateChange(AppBarStateChangeListener.State state) {
        if (mCurrentPage==1){
            AppBarStateEvent appBarStateEvent=new AppBarStateEvent();
            if(state== AppBarStateChangeListener.State.COLLAPSED){
                appBarStateEvent.setClose(true);
            }else {
                appBarStateEvent.setClose(false);
            }
           EventBus.getDefault().post(appBarStateEvent);
        }
    }
}
