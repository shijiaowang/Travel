package com.example.administrator.travel.ui.activity;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.ImageFolder;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.PictureSelectorAdapter;
import com.example.administrator.travel.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

/**
 * Created by android on 2016/8/23.
 * 相册中图片选择
 */
public class PictureSelectorActivity extends BarBaseActivity {
    @ViewInject(R.id.gv_photo)
    private GridView mGvPhoto;
    @ViewInject(R.id.tv_send)
    private TextView mTvSend;
    @ViewInject(R.id.tv_cancel)
    private TextView mTvCancel;
    private ImageFolder mFolder;
    private List<String> mImages;
    private PictureSelectorAdapter adapter;



    @Override
    protected int setContentLayout() {
        return R.layout.activity_picture_selector;
    }

    @Override
    protected void initEvent() {
        mTvSend.setText("发送");
        if (GlobalValue.mSelectImages!=null){
            mTvSend.setText("发送("+GlobalValue.mSelectImages.size()+")");
        }
        getmVsRightIcon().inflate();
        TextView mTvCancel = (TextView) findViewById(R.id.tv_ok);
        mTvCancel.setText("取消");
        mTvCancel.setTextSize(12, TypedValue.COMPLEX_UNIT_SP);
        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initViewData() {
        //获取图片
        mFolder = (ImageFolder) getIntent().getSerializableExtra(IVariable.IMAGE_FOLDER);
        if (mFolder!=null){
            String dir = mFolder.getDir();
            File file=new File(dir);
            mImages = Arrays.asList(file.list(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String filename) {
                    if (filename.endsWith(".jpg") || filename.endsWith(".png")
                            || filename.endsWith(".jpeg")) {
                        return true;
                    }
                    return false;
                }
            }));
        }
        if (mImages==null || mImages.size()==0){
            ToastUtils.showToast("没有图片");
            return;
        }
        adapter = new PictureSelectorAdapter(this, mImages, mFolder.getDir());
        mGvPhoto.setAdapter(adapter);
        adapter.setOnSelectChangeListener(new PictureSelectorAdapter.OnSelectChangeListener() {
            @Override
            public void change(int size) {
                mTvSend.setText("发送("+size+")");
            }
        });
    }

    @Override
    protected String setTitleName() {
        return "选择图片";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected boolean haveRightIcon() {
        return true;
    }



}
