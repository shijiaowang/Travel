package com.example.administrator.travel.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/19 0019.
 * 个人简介
 */
public class PersonalProfileActivity extends LoadingBarBaseActivity {
    @ViewInject(R.id.tv_write)
    private TextView mTvWrite;
    @ViewInject(R.id.et_profile)
    private EditText mEtProfile;
    @ViewInject(R.id.bt_save_change)
    private Button mBtSaveChange;
    @ViewInject(R.id.tv_number)
    private TextView mTvNumber;
    @ViewInject(R.id.ll_hint)
    private LinearLayout mLlHint;
    @Override
    protected void initContentView() {
        FontsIconUtil.findIconFontsById(this,R.id.tv_write);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_personal_profile;
    }

    @Override
    protected void initEvent() {
        mBtSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
   mEtProfile.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
          if (mLlHint.getVisibility()== View.VISIBLE)mLlHint.setVisibility(View.GONE);
           mTvNumber.setText(getString(mEtProfile).length()+"");
           if (getString(mEtProfile).length()>0){
               btIsClick(mBtSaveChange,true);
           }else {
               btIsClick(mBtSaveChange,false);
           }
       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   });
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected void initViewData() {
     setIsProgress(false);
        btIsClick(mBtSaveChange,false);
    }

    @Override
    protected String setTitleName() {
        return "个人简介";
    }
    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
