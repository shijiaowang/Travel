package com.example.administrator.travel.ui.me.messagecenter.relateme;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.me.messagecenter.relateme.detailmessage.RelateMeDetailActivity;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by wangyang on 2016/8/26 0026.
 * 与我相关
 */
public class RelateMeActivity extends BarBaseActivity implements View.OnClickListener {
    public static final int TYPE_AITE = 0;
    public static final int TYPE_DISCUSS = 1;
    public static final int TYPE_ZAMBIA = 2;//赞
    @ViewInject(R.id.rl_zambia)
    private RelativeLayout mRlZambia;
    @ViewInject(R.id.rl_discuss)
    private RelativeLayout mRlDiscuss;
    @ViewInject(R.id.rl_aite)
    private RelativeLayout mRlAite;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_relate_me;
    }

    @Override
    protected void initEvent() {
        mRlAite.setOnClickListener(this);
        mRlDiscuss.setOnClickListener(this);
        mRlZambia.setOnClickListener(this);
    }

    @Override
    protected void initViewData() {

    }

    @Override
    protected String setTitleName() {
        return "与我相关";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, RelateMeDetailActivity.class);
        switch (v.getId()) {
            case R.id.rl_aite:
                intent.putExtra(IVariable.TYPE, TYPE_AITE);
                startActivity(intent);
                break;
            case R.id.rl_discuss:
                intent.putExtra(IVariable.TYPE, TYPE_DISCUSS);
                startActivity(intent);
                break;
            case R.id.rl_zambia:
                intent.putExtra(IVariable.TYPE, TYPE_ZAMBIA);
                startActivity(intent);
                break;

        }
    }
}
