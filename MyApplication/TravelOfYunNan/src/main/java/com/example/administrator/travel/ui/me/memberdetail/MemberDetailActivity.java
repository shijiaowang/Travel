package com.example.administrator.travel.ui.me.memberdetail;


import android.app.Activity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.ui.baseui.BarBaseActivity;
import com.example.administrator.travel.ui.baseui.LoadingBarBaseActivity;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.xutils.view.annotation.ViewInject;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2016/8/4 0004.
 * 成员详情
 */
public class MemberDetailActivity extends LoadingBarBaseActivity<MemBerDetailEvent> implements View.OnClickListener {
    @ViewInject(R.id.rv_joined)
    private RecyclerView mRvJoined;
    @ViewInject(R.id.tv_joined)
    private TextView mTvJoined;
    @ViewInject(R.id.rv_enter)
    private RecyclerView mRvEnter;
    @ViewInject(R.id.tv_enter)
    private TextView mTvEnter;
    private String tId;
    private int enterSize = 0;
    private int joinedSize = 0;
    private List<MemberDetailBean.DataBean.JoinBean> joined;
    private List<MemberDetailBean.DataBean.JoinBean> joing;
    private MemberEnterAdapter memberEnterAdapter;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_member_detail;
    }

    @Override
    protected void initEvent() {
        tId = getIntent().getStringExtra(IVariable.DATA);
        mTvEnter.setOnClickListener(this);
        mTvJoined.setOnClickListener(this);
    }

    @Override
    protected void onLoad(int type) {
        Map<String, String> end = MapUtils.Build().addKey(this).addUserId().addtId(tId).end();
        XEventUtils.getUseCommonBackJson(IVariable.MEMBER_DETAIL, end, type, new MemBerDetailEvent());
    }

    private void hideOrShowMember(View show, TextView toggle) {
        if (show != null) {
            int visibility = show.getVisibility();
            show.setVisibility(visibility == View.VISIBLE ? View.GONE : View.VISIBLE);
            if (toggle == mTvJoined) {
                toggle.setText(visibility == View.VISIBLE ? getString(R.string.text_up, getListSize(joined)) : getString(R.string.text_down, getListSize(joined)));
            } else {
                toggle.setText(visibility == View.VISIBLE ? getString(R.string.text_uping, getListSize(joing)) : getString(R.string.text_downing, getListSize(joing)));
            }
        }
    }

    @Override
    protected Activity initViewData() {

        return this;
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
    protected void onSuccess(MemBerDetailEvent memBerDetailEvent) {
        switch (memBerDetailEvent.getType()) {
            case TYPE_REFRESH:
                dealLoad(memBerDetailEvent);
                break;
            case MemberEnterAdapter.TYPE_AGREE:
                int position = memBerDetailEvent.getPosition();
                joing.get(position).setState("2");
                memberEnterAdapter.notifyDataSetChanged();
                break;
            case MemberEnterAdapter.TYPE_RESUSE:
                int position1 = memBerDetailEvent.getPosition();
                joing.remove(position1);
                mTvEnter.setText(getString(R.string.text_downing, getListSize(joing)));
                memberEnterAdapter.notifyItemRemoved(position1);
                memberEnterAdapter.notifyItemRangeChanged(0,joing.size());


                break;
        }


    }


    private void dealLoad(MemBerDetailEvent memBerDetailEvent) {
        MemberDetailBean object = GsonUtils.getObject(memBerDetailEvent.getResult(), MemberDetailBean.class);
        String isBoss = object.getData().getIs_boss();
        joined = object.getData().getJoined();
        joing = object.getData().getJoin_ing();
        if (isBoss.equals("1")) {//是否是领队
            mTvEnter.setText(getString(R.string.text_downing, getListSize(joing)));
            memberEnterAdapter = new MemberEnterAdapter(this, joing);
            mRvEnter.setAdapter(memberEnterAdapter);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            //解决 嵌套滑动不流畅问题
            linearLayoutManager.setSmoothScrollbarEnabled(true);
            linearLayoutManager.setAutoMeasureEnabled(true);
            mRvEnter.setLayoutManager(linearLayoutManager);
            mRvEnter.setHasFixedSize(true);
            mRvEnter.setNestedScrollingEnabled(false);
            mRvEnter.setItemAnimator(new DefaultItemAnimator());
        } else {
            mTvEnter.setVisibility(View.GONE);
            mRvEnter.setVisibility(View.GONE);
        }
        mTvJoined.setText(getString(R.string.text_down, getListSize(joined)));
        mRvJoined.setAdapter(new MemberJoinedAdapter(this, joined));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //解决 嵌套滑动不流畅问题
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        linearLayoutManager.setAutoMeasureEnabled(true);
        mRvJoined.setLayoutManager(linearLayoutManager);
        mRvJoined.setHasFixedSize(true);
        mRvJoined.setNestedScrollingEnabled(false);
        mRvJoined.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_enter:
                hideOrShowMember(mRvEnter, mTvEnter);
                break;
            case R.id.tv_joined:
                hideOrShowMember(mRvJoined, mTvJoined);
                break;

        }
    }


}
