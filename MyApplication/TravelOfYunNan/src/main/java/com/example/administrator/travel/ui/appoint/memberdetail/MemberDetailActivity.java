package com.example.administrator.travel.ui.appoint.memberdetail;


import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.adapter.MemberEnterAdapter;
import com.example.administrator.travel.ui.adapter.MemberJoinedAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;


import org.xutils.view.annotation.ViewInject;



/**
 * Created by Administrator on 2016/8/4 0004.
 * 成员详情
 */
public class MemberDetailActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.lv_joined)
    private ToShowAllListView mLvJoined;
    @ViewInject(R.id.tv_joined)
    private TextView mTvJoined;
    @ViewInject(R.id.rv_enter)
    private RecyclerView mRvEnter;
    @ViewInject(R.id.tv_enter)
    private TextView mTvEnter;

    @Override
    protected int setContentLayout() {
        return R.layout.activity_member_detail;
    }

    @Override
    protected void initEvent() {
        mTvEnter.setOnClickListener(this);
        mTvJoined.setOnClickListener(this);
    }

    private void hideOrShowMember(View show, TextView toggle) {
        if (show != null) {
            int visibility = show.getVisibility();
            show.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
            toggle.setText(visibility == View.VISIBLE ? getString(R.string.text_up, 4) : getString(R.string.text_down, 4));
        }
    }

    @Override
    protected void initViewData() {

        mLvJoined.setAdapter(new MemberJoinedAdapter(this, null));
        // TODO: 2016/8/4 0004 页面太卡  就是这里出问题了
        mRvEnter.setAdapter(new MemberEnterAdapter(this, null));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRvEnter.setLayoutManager(linearLayoutManager);
        mRvEnter.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected String setTitleName() {
        return "成员详情";
    }

    @Override
    public float getAlpha() {
        return 1f;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_enter:
                hideOrShowMember(mRvEnter, mTvEnter);
                break;
            case R.id.tv_joined:
                hideOrShowMember(mLvJoined, mTvJoined);
                break;

        }
    }


}
