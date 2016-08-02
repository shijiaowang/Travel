package com.example.administrator.travel.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousDiscussAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

/**
 * Created by android on 2016/7/30.
 * 目的地详情
 */
public class DestinationDetailActivity extends BarBaseActivity implements View.OnClickListener {

    private ToShowAllListView mLvDiscuss;
    private TextView mTvDestinationDes;
    private TextView mTvShow;
    private boolean isShowAllFlag =false;

    @Override
    protected void initContentView() {
        TextView mTvDisscuss = FontsIconUtil.findIconFontsById(R.id.tv_discuss, this);
        TextView mTvSearch = FontsIconUtil.findIconFontsById(R.id.tv_search, this);
        TextView mTvSpot = FontsIconUtil.findIconFontsById(R.id.tv_spot, this);
        TextView mTvSpotAdd = FontsIconUtil.findIconFontsById(R.id.tv_spot_add, this);
        mLvDiscuss = (ToShowAllListView) findViewById(R.id.lv_discuss);
        mTvDestinationDes = (TextView) findViewById(R.id.tv_destination_des);
        mTvShow = (TextView) findViewById(R.id.tv_show);

    }

    @Override
    protected int setContentLayout() {
        return R.layout.activity_add_detail;
    }

    @Override
    protected void initEvent() {
        mTvShow.setOnClickListener(this);
    }

    @Override
    protected void initViewData() {
   mLvDiscuss.setAdapter(new DeliciousDiscussAdapter(this, null));
    }

    @Override
    protected boolean rootIsLinearLayout() {
        return false;
    }

    @Override
    protected boolean canScrollToChangeTitleBgColor() {
        return true;
    }

    @Override
    protected String setTitleName() {
        return "目的地";
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_show:
                changeShowWay();
                break;
        }
    }

    /**
     * 查看全部设置
     */
    private void changeShowWay() {
        if (isShowAllFlag){
            mTvDestinationDes.setEllipsize(TextUtils.TruncateAt.END);
            mTvDestinationDes.setMaxLines(7);
            mTvShow.setText(getResources().getString(R.string.cat_more));
        }else {
            mTvDestinationDes.setEllipsize(null);
            mTvDestinationDes.setMaxLines(Integer.MAX_VALUE);
            mTvShow.setText(getResources().getString(R.string.close_more));
        }
        isShowAllFlag=!isShowAllFlag;
    }
}
