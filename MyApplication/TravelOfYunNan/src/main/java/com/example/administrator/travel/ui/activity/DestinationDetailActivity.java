package com.example.administrator.travel.ui.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.ui.adapter.DeliciousDiscussAdapter;
import com.example.administrator.travel.ui.view.ToShowAllListView;
import com.example.administrator.travel.utils.FontsIconUtil;

import org.xutils.view.annotation.ViewInject;

/**
 * Created by android on 2016/7/30.
 * 目的地详情
 */
public class DestinationDetailActivity extends BarBaseActivity implements View.OnClickListener {
    @ViewInject(R.id.lv_discuss)
    private ToShowAllListView mLvDiscuss;
    @ViewInject(R.id.tv_destination_des)
    private TextView mTvDestinationDes;
    @ViewInject(R.id.tv_show)
    private TextView mTvShow;
    private boolean isShowAllFlag =false;



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
