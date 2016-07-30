package com.example.administrator.travel.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousDiscussAdapter;
import com.example.administrator.travel.ui.adapter.HotSpotsItemDecoration;
import com.example.administrator.travel.ui.adapter.TravelMemberAdapter;
import com.example.administrator.travel.ui.adapter.TravelsAddAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by android on 2016/7/30.
 * 游记详情
 */
public class TravelsDetailActivity extends BarBaseActivity {

    private RecyclerView mRvAddLine;
    private RecyclerView mRvMember;
    private ToShowAllListView mLvDiscuss;

    @Override
    protected void initContentView() {
        TextView mTvLine = FontsIconUtil.findIconFontsById(R.id.tv_line, this);
        TextView mTvdisscuss = FontsIconUtil.findIconFontsById(R.id.tv_discuss, this);
        TextView mTvTeamProfile = FontsIconUtil.findIconFontsById(R.id.tv_team_profile, this);
        mRvAddLine = (RecyclerView) findViewById(R.id.rv_add_line);
        mRvMember = (RecyclerView) findViewById(R.id.rv_member);
        mLvDiscuss = (ToShowAllListView) findViewById(R.id.lv_discuss);
        TextView mTvIconAdd = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_add, this);
        TextView mTvIconAir = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_air, this);
        TextView mTvIconPeople = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_people, this);
        TextView mTvIconTime = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_time, this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_detail;
    }

    @Override
    protected void initEvent() {

    }

    @Override
    protected void initViewData() {
        mRvAddLine.setAdapter(new TravelsAddAdapter(this, null));
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvAddLine.setLayoutManager(linearLayoutManager);
        mRvAddLine.addItemDecoration(new HotSpotsItemDecoration(18));
        LinearLayoutManager memberLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRvMember.setAdapter(new TravelMemberAdapter(this, null));
        mRvMember.setLayoutManager(memberLayoutManager);
        mRvMember.addItemDecoration(new HotSpotsItemDecoration(24));
        mLvDiscuss.setAdapter(new DeliciousDiscussAdapter(this, null));
    }

    @Override
    protected String setTitleName() {
        return "游记详情";
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }
}
