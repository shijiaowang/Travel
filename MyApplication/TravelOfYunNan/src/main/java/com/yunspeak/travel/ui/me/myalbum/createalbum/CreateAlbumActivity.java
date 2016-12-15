package com.yunspeak.travel.ui.me.myalbum.createalbum;


import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseCutPhotoActivity;
import com.yunspeak.travel.ui.me.myalbum.editalbum.EditAlbumActivity;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.StringUtils;
import com.yunspeak.travel.utils.ToastUtils;
import com.yunspeak.travel.utils.XEventUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.model.AspectRatio;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/8/2 0002.
 * 创建相册
 */
public class CreateAlbumActivity extends BaseCutPhotoActivity<CreateAlbumEvent> {
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.tv_set_cover) TextView mTvSetCover;
    @BindView(R.id.ed_set_name) EditText mEdSetName;
    @BindView(R.id.ll_des) LinearLayout mLlDes;
    @BindView(R.id.et_des) EditText mEdDes;
    @BindView(R.id.tv_count) TextView mTvCount;
    @BindView(R.id.bt_sure) Button mBtSure;
    @BindView(R.id.v_bottom) View mVBottom;





    @Override
    protected void userResultSize(UCrop uCrop, int width, int height) {
        super.userResultSize(uCrop, 2000, 1000);
    }

    @Override
    protected void initEvent() {
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
                Map<String, String> albumMap = MapUtils.Build().addKey().addUserId().addTitle(albumName).addContent(des).end();
                List<String> files=new ArrayList<>();
                files.add(filename);
                XEventUtils.postFileCommonBackJson(IVariable.CREATE_ALBUM,albumMap,files,0,new CreateAlbumEvent());
                setIsProgress(true);
            }
        });
    }

    @Override
    protected boolean isAutoLoad() {
        return false;
    }

    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {

    }

    @Override
    protected boolean isChangeBarColor() {
        return true;
    }

    @Override
    protected String initUrl() {
        return null;
    }




    @Override
    protected void onSuccess(CreateAlbumEvent event) {
        ToastUtils.showToast(event.getMessage());
        setIsProgress(false);
        CreateAlbumBean createAlbumBean = GsonUtils.getObject(event.getResult(), CreateAlbumBean.class);
        EditAlbumActivity.start(this,createAlbumBean.getData());
    }

    @Override
    protected void onFail(CreateAlbumEvent event) {
          setIsProgress(false);
    }





    @Override
    protected void setOptions(UCrop.Options options) {
      options.setAspectRatioOptions(0,new AspectRatio("1",16,9));
    }

    @Override
    protected void childDisplay(String url, String filename) {
        FrescoUtils.displayNormal(mIvBg,url,600,450);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_album;
    }

    @Override
    protected String initTitle() {
        return "创建相册";
    }
}
