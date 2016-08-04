package com.example.administrator.travel.ui.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousDiscussAdapter;
import com.example.administrator.travel.ui.adapter.HotSpotsItemDecoration;
import com.example.administrator.travel.ui.adapter.TravelMemberAdapter;
import com.example.administrator.travel.ui.adapter.TravelsAddAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/7/30.
 * 游记详情
 */
public class TravelsDetailActivity extends BarBaseActivity {
    @ViewInject(R.id.rv_add_line)
    private RecyclerView mRvAddLine;
    @ViewInject(R.id.rv_member)
    private RecyclerView mRvMember;
    @ViewInject(R.id.lv_discuss)
    private ToShowAllListView mLvDiscuss;

    @Override
    protected void initContentView() {
        TextView mTvLine = FontsIconUtil.findIconFontsById(R.id.tv_line, this);
        TextView mTvdisscuss = FontsIconUtil.findIconFontsById(R.id.tv_discuss, this);
        TextView mTvTeamProfile = FontsIconUtil.findIconFontsById(R.id.tv_team_profile, this);
        TextView mTvIconAdd = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_add, this);
        TextView mTvIconAir = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_air, this);
        TextView mTvIconPeople = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_people, this);
        TextView mTvIconTime = FontsIconUtil.findIconFontsByIdAndFakeBoldText(R.id.tv_icon_time, this);
    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_travels_detail;
    }
    private boolean isFirst=true;//避免第一次margin为0
    @Override
    protected void initEvent() {
        mRvAddLine.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isFirst) {
                    isFirst = false;
                }else {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRvAddLine.getLayoutParams();
                    layoutParams.leftMargin = 0;
                    mRvAddLine.setLayoutParams(layoutParams);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
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
