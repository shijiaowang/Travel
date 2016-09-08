package com.example.administrator.travel.ui.activity;

import android.content.Intent;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.GlobalValue;
import com.example.administrator.travel.global.IVariable;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/9/1 0001.
 * 费用设置
 */
public class CostSettingActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.ll_with_me)
    private LinearLayout mLlWithMe;//一起玩页面不显示
    @ViewInject(R.id.bt_next)
    private Button mBtNext;
    @Override
    protected int setContentLayout() {
        return R.layout.activity_cost_setting;
    }

    @Override
    protected void initEvent() {
        TextView mTvRightNext = getmTvRightIcon();
        mTvRightNext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mTvRightNext.setText(R.string.next);
        mBtNext.setOnClickListener(this);
      if (GlobalValue.mAppointType== IVariable.TYPE_TOGETHER){
          mLlWithMe.setVisibility(View.GONE);
      }
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "费用设置";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_next:
                startActivity(new Intent(this,DesRemarkActivity.class));
                break;
        }
    }
}
