package com.example.administrator.travel.ui.appoint.cutpicture;

import android.text.Html;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.activity.BarBaseActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/20 0020.
 * 裁剪图片
 */
public class CutPictureActivity extends BarBaseActivity {

    @Override
    protected int setContentLayout() {
        return R.layout.activity_cut_picture;
    }

    @Override
    protected void initEvent() {




    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "裁剪图片";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
