package com.yunspeak.travel.ui.me.setting;


import android.app.ProgressDialog;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.UserInfo;
import com.yunspeak.travel.db.DBManager;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.ParentPopClick;
import com.yunspeak.travel.global.SendTextClick;
import com.yunspeak.travel.ui.appoint.dialog.EnterAppointDialog;
import com.yunspeak.travel.ui.appoint.travelplan.lineplan.selectdestination.customdestination.adddestination.ProvinceBean;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.home.welcome.splash.login.LoginActivity;
import com.yunspeak.travel.ui.me.changephone.ChangePhoneActivity;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.baseui.PersonalProfileActivity;
import com.yunspeak.travel.ui.home.welcome.splash.SplashActivity;
import com.yunspeak.travel.ui.me.about.AboutActivity;
import com.yunspeak.travel.ui.me.changepassword.ChangePassWordActivity;
import com.yunspeak.travel.ui.me.userservice.CustomerServiceActivity;
import com.yunspeak.travel.ui.view.PhoneTextView;
import com.yunspeak.travel.utils.CacheUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GlobalUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShareUtil;
import com.hyphenate.chat.EMClient;
import com.yalantis.ucrop.UCrop;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.UserUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/22 0022.
 * 设置界面
 */
public class SettingActivity extends BaseCutPhotoActivity<SettingEvent> implements View.OnClickListener {
    @BindView(R.id.tv_user_id) TextView mTvUserId;
    @BindView(R.id.tv_user_nick_name) TextView mTvUserNickName;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_change_icon) TextView mTvChangeIcon;
    @BindView(R.id.tv_user_live_place) TextView mTvUserLivePlace;
    @BindView(R.id.ll_phone) LinearLayout mLlPhone;//更改手机
    @BindView(R.id.ll_profile) LinearLayout mLlProfile;//个人简介
    @BindView(R.id.ptv_phone) PhoneTextView mPtvPhone;
    @BindView(R.id.ll_logout) LinearLayout mLlLogout;
    @BindView(R.id.ll_about) LinearLayout mLlAbout;
    @BindView(R.id.ll_change_password) LinearLayout mLlChangePassword;
    @BindView(R.id.ll_back) LinearLayout mLlBack;
    @BindView(R.id.ll_clear) LinearLayout mLlClear;
    private UserInfo userInfo;
    private String nickName="";
    private String provice="";
    private String city="";
    private ArrayList<ProvinceBean> options1Items = new ArrayList<>(0);
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>(0);
    private OptionsPickerView pvOptions;
    private boolean isUpdate=false;
    private boolean messageIsChange=false;
    private String iconUrl;

    @Override
    protected void initEvent() {
        initCity();
        mLlPhone.setOnClickListener(this);
        mLlProfile.setOnClickListener(this);
        mLlLogout.setOnClickListener(this);
        mLlAbout.setOnClickListener(this);
        mLlChangePassword.setOnClickListener(this);
        mTvChangeIcon.setOnClickListener(this);
        mTvUserNickName.setOnClickListener(this);
        mTvUserLivePlace.setOnClickListener(this);
        mLlBack.setOnClickListener(this);
        mLlClear.setOnClickListener(this);
        initData();

    }

    private void initCity() {
        x.task().run(new Runnable() {
            @Override
            public void run() {
                try {
                    options1Items = DBManager.getProvince();
                    options2Items = DBManager.getCity(options1Items);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void initData() {
        userInfo = GlobalUtils.getUserInfo();
        try {
            mTvUserId.setText(userInfo.getId());
            FrescoUtils.displayIcon(mIvIcon, userInfo.getUser_img());
            String province = userInfo.getProvince();
            String city = userInfo.getCity();
            String provinceName = DBManager.getStringById("name", province);
            String cityName = DBManager.getStringById("name", city);
            mTvUserLivePlace.setText( provinceName+ "-" +cityName );
            mTvUserNickName.setText(userInfo.getNick_name());
            mPtvPhone.setPhoneNumber(userInfo.getTel());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        setIsProgress(false);
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected String initUrl() {
        return null;
    }




    @Override
    protected void onSuccess(SettingEvent settingEvent) {
        ToastUtils.showToast(settingEvent.getMessage());
        isUpdate=true;
        SettingBean object = GsonUtils.getObject(settingEvent.getResult(), SettingBean.class);
        UserInfo data = object.getData();
        if (data!=null){
            GlobalValue.userInfo=data;
            com.hyphenate.easeui.domain.UserInfo userInfo = new com.hyphenate.easeui.domain.UserInfo();
            userInfo.setId(data.getId());
            userInfo.setNick_name(data.getNick_name());
            userInfo.setUser_img(data.getUser_img());
            DBManager.insertChatUserInfo(userInfo);
        }
        UserUtils.saveUserInfo(data);
        messageIsChange=false;
        setResult(HomeActivity.UP_RESULT);
    }
    @Override
    protected void onFail(SettingEvent settingEvent) {
        setIsProgress(false);
        ToastUtils.showToast(settingEvent.getMessage());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_phone:
                startActivity(new Intent(this, ChangePhoneActivity.class));
                break;
            case R.id.ll_profile:
                startActivity(new Intent(this, PersonalProfileActivity.class));
                break;
            case R.id.ll_logout:
                EnterAppointDialog.showCommonDialog(this,"退出登录", "确定", "是否退出当前登录账号？", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        EMClient.getInstance().logout(true);
                        ShareUtil.deleteData(SettingActivity.this);
                        PushAgent.getInstance(SettingActivity.this).removeAlias(userInfo.getId(), "YUNS_ID", new UTrack.ICallBack() {
                            @Override
                            public void onMessage(boolean b, String s) {
                                LogUtils.e("是否成功"+b+"信息"+s);
                            }
                        });
                        startActivity(new Intent(SettingActivity.this, LoginActivity.class));
                        setResult(HomeActivity.RESULT);
                        finish();
                    }
                });

                break;
            case R.id.ll_about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case R.id.ll_change_password:
                startActivity(new Intent(this, ChangePassWordActivity.class));
                break;
            case R.id.tv_change_icon:
                showPictureCutPop(mLlLogout);
                break;
            case R.id.tv_user_nick_name:
                EnterAppointDialog.showInputTextView(this,userInfo.getNick_name(),"昵称设置","确定",new SendTextClick() {
                    @Override
                    public void onClick(String text) {
                          mTvUserNickName.setText(text);
                        nickName = text;
                        messageIsChange=true;
                    }
                });
                break;
            case R.id.tv_user_live_place:
                changeLivePlace();
                break;
            case R.id.ll_clear:
                final ProgressDialog progressDialog=new ProgressDialog(this);
                EnterAppointDialog.showCommonDialog(this, "清理缓存", "确认", "是否清理缓存", new ParentPopClick() {
                    @Override
                    public void onClick(int type) {
                        progressDialog.setTitle("清理中");
                        progressDialog.show();
                        try {
                            CacheUtils.cleanInternalCache(SettingActivity.this);
                            CacheUtils.cleanFiles(SettingActivity.this);
                            CacheUtils.cleanExternalCache(SettingActivity.this);
                            ImagePipeline imagePipeline =   Fresco.getImagePipeline();
                            imagePipeline.clearCaches();
                            ToastUtils.showToast("清除成功");
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            ToastUtils.showToast("清理失败");
                            progressDialog.dismiss();
                        }

                    }

                });

                break;

            case R.id.ll_back://意见反馈
                CustomerServiceActivity.start(this,false);
                break;
        }
    }

    private void changeLivePlace() {
        //选项选择器
        if (pvOptions == null) {
            pvOptions = new OptionsPickerView(this);
            //三级联动效果
            pvOptions.setPicker(options1Items, options2Items, true);
            //设置选择的三级单位
//          pwOptions.setLabels("省", "市", "区");
            pvOptions.setTitle("选择城市");
            pvOptions.setCyclic(false, true, true);
            pvOptions.setCancelable(true);
            //设置默认选中的三级项目
            pvOptions.setOnoptionsSelectListener(new OptionsPickerView.OnOptionsSelectListener() {

                @Override
                public void onOptionsSelect(int options1, int option2, int options3) {
                    //返回的分别是三个级别的选中位置
                    String tx = options1Items.get(options1).getPickerViewText()
                            + "-"+options2Items.get(options1).get(option2);
                    mTvUserLivePlace.setText(tx);
                    provice = options1Items.get(options1).getId();//省得id
                    String cityName = options2Items.get(options1).get(option2);
                    city = DBManager.getCityId(cityName, provice);
                    messageIsChange=true;
                }
            });

        }
        pvOptions.show();
    }

    @Override
    protected String initRightText() {
        return "保存";
    }

    @Override
    protected void otherOptionsItemSelected(MenuItem item) {
        if (!messageIsChange){
            ToastUtils.showToast("您尚未修改任何信息");
            return;
        }
        setIsProgress(true);
        MapUtils.Builder builder = MapUtils.Build().addKey().addUserId();
        if (!StringUtils.isEmptyNotNull(nickName)){
            builder.addNickName(nickName);
        }
        if (!StringUtils.isEmptyNotNull(provice) && !StringUtils.isEmptyNotNull(city)){
            builder.addProvince(provice).addCity(city);
        }
        List<String> files=new ArrayList<>();
        if (!StringUtils.isEmpty(iconUrl)){
              files.add(iconUrl);
        }
        XEventUtils.postFileCommonBackJson(IVariable.CHANGE_USER_INFO,builder.end(),files,TYPE_UPDATE,new SettingEvent());
    }



    @Override
    protected void setOptions(UCrop.Options options) {
        options.setCircleDimmedLayer(true);
    }

    @Override
    protected void childDisplay(String url, String filename) {
        messageIsChange=true;
        iconUrl = filename;
        FrescoUtils.displayIcon(mIvIcon,url );
    }


    @Override
    protected int initLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected String initTitle() {
        return "设置";
    }

    @Override
    protected void onStop() {
        super.onStop();
        options1Items=null;
        options2Items=null;
    }
}
