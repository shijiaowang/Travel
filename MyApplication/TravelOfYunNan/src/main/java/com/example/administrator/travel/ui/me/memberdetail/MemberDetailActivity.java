package com.example.administrator.travel.ui.me.memberdetail;


import android.app.Activity;
import android.support.v7.util.DiffUtil;
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
import com.example.administrator.travel.utils.LogUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;


import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
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
    private List<MemberDetailBean.DataBean.JoinBean> newDatas;


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
                memberEnterAdapter.notifyDataSetChanged();
                newDatas = new ArrayList<>(joing);
                newDatas.get(position).setState("2");
                changeData(newDatas);
                break;
            case MemberEnterAdapter.TYPE_RESUSE:
                int position1 = memBerDetailEvent.getPosition();
                mTvEnter.setText(getString(R.string.text_downing, getListSize(joing)));
                //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
                newDatas = new ArrayList<>(joing);
                MemberDetailBean.DataBean.JoinBean remove = newDatas.remove(position1);
                LogUtils.e(remove.getNick_name()+"是第"+position1+"个孩子");
                //changeData(newDatas);
                memberEnterAdapter.notifyItemRemoved(position1);
                memberEnterAdapter.notifyItemRangeChanged(position1,joing.size()-position1);
                joing=newDatas;
                memberEnterAdapter.setmDatas(joing);
                break;
        }


    }

    /**
     * 更新数据
     * @param newDatas
     */
    private void changeData(List<MemberDetailBean.DataBean.JoinBean> newDatas) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MemberDiffCallback(joing, newDatas), true);
        //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
        //别忘了将新数据给Adapter
        diffResult.dispatchUpdatesTo(memberEnterAdapter);
        joing = newDatas;
        memberEnterAdapter.setmDatas(joing);

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
