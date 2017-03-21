package com.yunspeak.travel.ui.me.me;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import com.yunspeak.travel.R;
import com.yunspeak.travel.databinding.FragmentMeBinding;
import com.yunspeak.travel.download.HttpClient;
import com.yunspeak.travel.download.INetworkCallBack;
import com.yunspeak.travel.download.IRequestUrl;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.baseui.CropBaseFragment;
import com.yunspeak.travel.ui.home.HomeActivity;
import com.yunspeak.travel.ui.me.MeEvent;
import com.yunspeak.travel.ui.me.me.model.Me;
import com.yunspeak.travel.ui.me.setting.SettingActivity;
import com.yunspeak.travel.ui.view.ItemView;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.ShowImageUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;


/**
 * Created by wangyang on 2017/3/14.
 * 我的 个人中心
 */

public class MeFragment extends CropBaseFragment<Me> {

    @BindView(R.id.iv_bg)
    ImageView ivBg;
    @BindView(R.id.iv_icon)
    ImageView ivIcon;
    @BindView(R.id.tv_setting)
    TextView textView;
    @BindView(R.id.iv_setting)
    ItemView itemView;
    private FragmentMeBinding fragmentMeBinding;
    private Me.DataBean dataBean;
    private int upType=-1;
    private static final int UP_BG=99;//上传背景
    private static final int UP_ICON=100;//上传头像

    // TODO: 2017/3/21  刷新，包括其他页面做了修改，使用shareprefence 
    @Override
    protected void receiveData(Me meData) {
        this.dataBean = meData.getData();
        fragmentMeBinding.setMeData(this.dataBean);
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
        ButterKnife.bind(this, fragmentMeBinding.getRoot());
        return fragmentMeBinding.getRoot();
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
        });
        //上传图片
        //XEventUtils.postFileCommonBackJson(url,bgImageMap,flies,upType,new MeEvent());
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


    @OnClick({R.id.iv_bg, R.id.iv_icon,R.id.iv_setting,R.id.tv_setting})
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
            case R.id.tv_setting:
            case R.id.ll_setting:
                Intent intent = new Intent(getContext(), SettingActivity.class);
                startActivityForResult(intent, HomeActivity.REQ);
        }
    }
}
