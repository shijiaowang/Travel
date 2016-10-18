package com.example.administrator.travel.ui.appoint.createsuccess;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.BaseNetWorkActivity;
import com.example.administrator.travel.ui.baseui.BaseToolBarActivity;
import com.example.administrator.travel.utils.ActivityUtils;

import org.xutils.view.annotation.ViewInject;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/9/1 0001.
 * 发布约伴成功
 */
public class CreateAppointSuccessActivity extends BaseToolBarActivity {
    @BindView(R.id.ll_key) LinearLayout mLlKey;


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(android.R.id.home);
        item.setTitle("返回约伴");
        return super.onCreateOptionsMenu(menu);
    }

    private void finishActivity() {
        ActivityUtils.getInstance().exit();
    }




    @Override
    protected int initLayoutRes() {
        return R.layout.activity_create_appoint_success;
    }

    @Override
    protected void initOptions() {
      initEvent();
    }

    @Override
    protected String initTitle() {
        return "发布成功";
    }
}
