package com.example.administrator.travel.ui.me.createalbum;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.event.HttpEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.BaseCropPhotoActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.ui.me.me.CropPhotoBaseFragment;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.StringUtils;
import com.example.administrator.travel.utils.ToastUtils;
import com.example.administrator.travel.utils.TypefaceUtis;
import com.example.administrator.travel.utils.XEventUtils;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;

import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by wangyang on 2016/8/2 0002.
 */
public class CreateAlbumActivity extends BaseCropPhotoActivity<CreateAlbumEvent> {
    private TextView mTvMore;//更多
    @ViewInject(R.id.iv_bg)
    private ImageView mIvBg;
    @ViewInject(R.id.tv_set_cover)
    private TextView mTvSetCover;
    @ViewInject(R.id.ed_set_name)
    private EditText mEdSetName;
    @ViewInject(R.id.ll_des)
    private LinearLayout mLlDes;
    @ViewInject(R.id.et_des)
    private EditText mEdDes;
    @ViewInject(R.id.tv_count)
    private TextView mTvCount;
    @ViewInject(R.id.bt_sure)
    private Button mBtSure;
    @ViewInject(R.id.v_bottom)
    private View mVBottom;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_create_album;
    }



    @Override
    protected void initChildListener() {
         init();

        mEdDes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int length = getString(mEdDes).length();
                if (length==0){
                    mLlDes.setVisibility(View.VISIBLE);
                }else {
                    if (mLlDes.getVisibility()==View.VISIBLE) {
                        mLlDes.setVisibility(View.GONE);
                    }
                    if (length>=80){
                        ToastUtils.showToast("输入字数最大了。");
                        return;
                    }
                    mTvCount.setText(length+"/80");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mTvSetCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftWore(mEdDes);
                showPictureCutPop(mVBottom);
            }
        });
        mBtSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String albumName = getString(mEdSetName);
                 if (StringUtils.isEmpty(albumName)){
                     ToastUtils.showToast("相册名不能为空");
                     return;
                 }
                 String des=getString(mEdDes);
                if (StringUtils.isEmpty(des)){
                    des="暂无简介";
                }
                Map<String, String> albumMap = MapUtils.Build().addKey(CreateAlbumActivity.this).addUserId().addTitle(albumName).addContent(des).end();
                List<String> files=new ArrayList<String>();
                files.add(filename);
                XEventUtils.postFileCommonBackJson(IVariable.CREATE_ALBUM,albumMap,files,0,new CreateAlbumEvent());
            }
        });
    }




    @Override
    protected void onLoad(int type) {
        setIsProgress(false);
    }

    private void init() {
        mTvMore = getmTvRightIcon();
        mTvMore.setTypeface(TypefaceUtis.getTypeface(this));
        mTvMore.setText(getResources().getString(R.string.activity_message_center_more));
    }


    @Override
    protected Activity initViewData() {
          return this;
    }

    @Override
    protected String setTitleName() {
        return "创建相册";
    }

    @Override
    protected void onSuccess(CreateAlbumEvent event) {
        ToastUtils.showToast(event.getMessage());
    }

    @Override
    protected void onFail(CreateAlbumEvent event) {

    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected ImageView childViewShow() {
        return mIvBg;
    }

    @Override
    protected void setOptions(UCrop.Options options) {
      options.setAspectRatioOptions(0,new AspectRatio("1",16,9));
    }
}
