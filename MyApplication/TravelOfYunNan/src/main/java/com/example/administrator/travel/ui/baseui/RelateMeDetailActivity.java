package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.widget.ListView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.adapter.AppointMessageAdapter;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by Administrator on 2016/8/26 0026.
 * 与我相关 评论 赞 @
 */
public class RelateMeDetailActivity extends LoadingBarBaseActivity{
    @ViewInject(R.id.lv_relate_me)
    private ListView mLvRelateMe;
    private int type;
    private String title;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_relate_me_detail;
    }

    @Override
    protected void initEvent() {
       init();
    }

    private void init() {
        type = getIntent().getIntExtra(IVariable.TYPE, -1);
        switch (type){
            case RelateMeActivity.TYPE_AITE:
                title = "@我的";
                break;
            case RelateMeActivity.TYPE_ZAMBIA:
                title = "赞";
                break;
            case RelateMeActivity.TYPE_DISCUSS:
                title="评论我的";
                break;
        }
    }

    @Override
    protected void onLoad() {

    }

    @Override
    protected Activity initViewData() {
        changeTitle(title);
        mLvRelateMe.setAdapter(new AppointMessageAdapter(this,null));
        return null;
    }

    @Override
    protected String setTitleName() {
        return "关于我详情";
    }

    @Override
    public float getAlpha() {
        return 1.0f;
    }
}
