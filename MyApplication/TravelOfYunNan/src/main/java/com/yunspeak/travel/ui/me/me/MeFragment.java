package com.yunspeak.travel.ui.me.me;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentMeBinding;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.baseui.CropBaseFragment;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.me.me.model.Me;
import com.yunspeak.travel.ui.me.setting.SettingActivity;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShowImageUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * Created by wangyang on 2017/3/14.
 * 我的 个人中心
 */

public class MeFragment extends CropBaseFragment<Me> implements View.OnClickListener {


    ImageView ivBg;
    ImageView ivIcon;
    private FragmentMeBinding fragmentMeBinding;
    private int upType=-1;
    private static final int UP_BG=99;//上传背景
    private static final int UP_ICON=100;//上传头像

    // TODO: 2017/3/21  刷新，包括其他页面做了修改，自定义evenetbus
    @Override
    protected void receiveData(Me meData) {
        fragmentMeBinding.setMeData(meData.getData());
    }

    @Override
    protected Map<String, String> initParams() {
        return MapUtils.Build().addKey().addUserId().end();
    }

    @Override
    protected String initUrl() {
        return IRequestUrl.ME_MESSAGE;
    }

    @Override
    protected View initRootView(LayoutInflater inflater, ViewGroup container) {
        fragmentMeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_me, container, false);
       View view=fragmentMeBinding.getRoot();
        ivBg= (ImageView) view.findViewById(R.id.iv_bg);
        ivIcon= (ImageView) view.findViewById(R.id.iv_icon);
        ivBg.setOnClickListener(this);
        ivIcon.setOnClickListener(this);
        view.findViewById(R.id.iv_me_setting).setOnClickListener(this);
        view.findViewById(R.id.tv_me_setting).setOnClickListener(this);
        return view;
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
        Map<String, String> bgImageMap = MapUtils.Build().addKey().addUserId().end();
        if (StringUtils.isEmpty(fileName)) {
            ToastUtils.showToast("未找到图片");
        }
        List<String> flies=new ArrayList<>();
        flies.add(fileName);
        String url=upType==UP_BG? IRequestUrl.CHANGE_BG:IRequestUrl.CHANGE_USER_INFO;
        HttpClient.getInstance().postImage(url, bgImageMap, flies, new INetworkCallBack<TravelsObject>() {
            @Override
            public void accept(@NonNull TravelsObject travelsObject) throws Exception {
                ToastUtils.showToast(travelsObject.getMessage());
            }

            @Override
            public void error(Throwable throwable) {
               ToastUtils.showToast("上传失败");
            }
        },getContext());
    }

    @Override
    protected void childViewShow(String s) {
        if (upType==UP_BG){
            ShowImageUtils.showNormal(ivBg,R.drawable.normal_2_1,s);
        }else {
            ShowImageUtils.showCircle(ivIcon,R.drawable.boy,s,2);
        }
    }
    @Override
    protected void userResultSize(UCrop uCrop, int width, int height) {
        if (upType==UP_BG){
            super.userResultSize(uCrop, 2000, 1000);
        }else {
            super.userResultSize(uCrop, width, height);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HomeActivity.REQ && resultCode == HomeActivity.RESULT) {
            getActivity().finish();
        }
        if (requestCode == HomeActivity.REQ && resultCode == HomeActivity.UP_RESULT) {
            load();//拉去数据，更新
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
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
            case R.id.iv_me_setting:
            case R.id.tv_me_setting:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivityForResult(intent, HomeActivity.REQ);
                break;
        }
    }
}
