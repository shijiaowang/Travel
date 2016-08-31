package com.example.administrator.travel.ui.activity;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.ChoicePropsLeftAdapter;
import com.example.administrator.travel.ui.adapter.ChoicePropsRightAdapter;
import com.example.administrator.travel.ui.adapter.PopEquAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/31 0031.
 * 选择道具界面
 */
public class ChoicePropsActivity extends LoadingBarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.lv_type)
    private ListView mLvType;
    @ViewInject(R.id.lv_equ)
    private ListView mLvEqu;
    @ViewInject(R.id.tv_sure)
    private TextView mTvSure;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.tv_equ_number)
    private TextView mTvEquNumber;
    @ViewInject(R.id.rl_equ)
    private RelativeLayout mRlEqu;
    @ViewInject(R.id.rl_bottom)
    private RelativeLayout mRlBottom;
    @ViewInject(R.id.rl_select)
    private RelativeLayout mRlSelect;
    @ViewInject(R.id.lv_select_equ)
    private ListView mLvSelect;
    private PopEquAdapter popEquAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_choice_props;
    }

    @Override
    protected void initEvent() {
        mRlEqu.setOnClickListener(this);
        mTvSure.setOnClickListener(this);
        mRlSelect.setOnClickListener(this);
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        mLvEqu.setAdapter(new ChoicePropsRightAdapter(this, null));
        mLvType.setAdapter(new ChoicePropsLeftAdapter(this, null));
        return null;
    }

    @Override
    protected String setTitleName() {
        return "选择道具";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_sure:
                break;
            case R.id.rl_equ:
                showSelected();
                break;
            case R.id.rl_select:
                goneEqu();
                break;
        }
    }

    private void goneEqu() {
       mRlSelect.setVisibility(View.GONE);
    }

    /**
     * 已选择的道具
     */
    private void showSelected() {
        if (mRlSelect.getVisibility() == View.VISIBLE) {
            mRlSelect.setVisibility(View.INVISIBLE);
        } else {
            mRlSelect.setVisibility(View.VISIBLE);
            mRlSelect.setClickable(true);
            mRlSelect.setFocusable(true);
            mRlSelect.requestFocus();
            showSelect();
        }
    }

    private void showSelect() {
        if (popEquAdapter == null) {
            popEquAdapter = new PopEquAdapter(this, null);
        }
        mLvSelect.setAdapter(popEquAdapter);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && mRlSelect.getVisibility()==View.VISIBLE) {
            mRlSelect.setVisibility(View.GONE);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
