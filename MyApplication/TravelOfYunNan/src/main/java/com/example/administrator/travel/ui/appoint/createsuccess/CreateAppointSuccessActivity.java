package com.example.administrator.travel.ui.appoint.createsuccess;

import android.app.Activity;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.TravelsApplication;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.activity.BarBaseActivity;
import com.example.administrator.travel.utils.ActivityUtils;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/1 0001.
 * 发布约伴成功
 */
public class CreateAppointSuccessActivity extends BarBaseActivity {
    @ViewInject(R.id.ll_key)
    private LinearLayout mLlKey;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_create_appoint_success;
    }

    @Override
    protected void initEvent() {
      if (GlobalValue.mAppointType== IVariable.TYPE_WITH_ME){
          mLlKey.setVisibility(View.GONE);
      }
        try {
            finishActivity();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void finishActivity() {
        ActivityUtils.getInstance().exit();
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "发布成功";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    protected boolean leftIsFontIcon() {
        return false;
    }

    @Override
    protected float getLeftTextSize() {
        return 12;
    }

    @Override
    protected String getLeftText() {
        return "返回约伴";
    }
}
