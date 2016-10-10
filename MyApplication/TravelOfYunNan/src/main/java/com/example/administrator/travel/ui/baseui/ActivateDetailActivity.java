package com.example.administrator.travel.ui.baseui;

import android.app.Activity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.travel.R;
import com.example.administrator.travel.bean.ActiveDetail;
import com.example.administrator.travel.event.ActiveDetailEvent;
import com.example.administrator.travel.global.IVariable;
import com.example.administrator.travel.utils.FormatDateUtils;
import com.example.administrator.travel.utils.GsonUtils;
import com.example.administrator.travel.utils.MapUtils;
import com.example.administrator.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

/**
 * Created by Administrator on 2016/7/26 0026.
 * 活动详情
 */
public class ActivateDetailActivity extends LoadingBarBaseActivity<ActiveDetailEvent> {
    @ViewInject(R.id.wv_html)
    private WebView mWvHtml;
    @ViewInject(R.id.iv_bg)
    private ImageView mIvBg;
    @ViewInject(R.id.iv_icon)
    private ImageView mIvIcon;
    @ViewInject(R.id.tv_name)
    private TextView mTvName;
    @ViewInject(R.id.tv_content)
    private TextView mTvContnet;
    @ViewInject(R.id.tv_money)
    private TextView mTvMoney;
    @ViewInject(R.id.tv_money2)
    private TextView mTvMoney2;
    @ViewInject(R.id.tv_number)//人数
    private TextView mTvNumber;
    @ViewInject(R.id.tv_time)//时间
    private TextView mTvTime;
    private String aId;


    @Override
    protected int setContentLayout() {
        return R.layout.activity_activate_detail;
    }

    @Override
    protected void initEvent() {
        aId = getIntent().getStringExtra(IVariable.A_ID);
    }

    @Override
    protected void onLoad(int typeRefresh) {
        Map<String, String> activeMap = MapUtils.Build().addKey(this).addAId(aId).addUserId().end();
        XEventUtils.getUseCommonBackJson(IVariable.FIND_ACTIVITY_DETAIL,activeMap,0,new ActiveDetailEvent());
    }

    @Override
    protected Activity initViewData() {
        WebSettings settings = mWvHtml.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        return this;
    }

    @Override
    protected String setTitleName() {
        return "活动详情";
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
    public float getAlpha() {
        return 0f;
    }

    @Override
    protected void onSuccess(ActiveDetailEvent activeDetailEvent) {
        dealData(activeDetailEvent);
    }



    private void dealData(ActiveDetailEvent event) {
        ActiveDetail activeDetail = GsonUtils.getObject(event.getResult(), ActiveDetail.class);
        ActiveDetail.DataBean data = activeDetail.getData();
        mWvHtml.loadUrl(data.getUrl());
        ImageOptions imageOptions=new ImageOptions.Builder().setSize(DensityUtil.getScreenWidth(),DensityUtil.dip2px(228)).setCrop(true).build();
        x.image().bind(mIvBg,data.getTitle_img(),imageOptions);
        x.image().bind(mIvIcon,data.getActivity_img());
        mTvName.setText(data.getTitle());
        mTvContnet.setText(data.getContent());
        mTvMoney.setText("¥" + data.getPrice());
        mTvMoney2.setText("¥"+data.getPrice());
        mTvNumber.setText(data.getMax_people()+"人");
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd",data.getStar_time())+"-"+FormatDateUtils.FormatLongTime("yyyy.MM.dd",data.getEnd_time()));
    }
}
