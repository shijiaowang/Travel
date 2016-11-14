package com.yunspeak.travel.ui.baseui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.NestedScrollView;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.yunspeak.travel.R;
import com.yunspeak.travel.bean.ActiveDetailBean;
import com.yunspeak.travel.event.ActiveDetailEvent;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.utils.FormatDateUtils;
import com.yunspeak.travel.utils.FrescoUtils;
import com.yunspeak.travel.utils.GsonUtils;
import com.yunspeak.travel.utils.MapUtils;
import com.yunspeak.travel.utils.XEventUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Map;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/7/26 0026.
 * 活动详情
 */
public class ActivateDetailActivity extends BaseNetWorkActivity<ActiveDetailEvent> {
    @BindView(R.id.wv_html) WebView mWvHtml;
    @BindView(R.id.iv_bg) SimpleDraweeView mIvBg;
    @BindView(R.id.iv_icon) SimpleDraweeView mIvIcon;
    @BindView(R.id.tv_name) TextView mTvName;
    @BindView(R.id.tv_content) TextView mTvContnet;
    @BindView(R.id.tv_money) TextView mTvMoney;
    @BindView(R.id.tv_money2) TextView mTvMoney2;
    @BindView(R.id.tv_number) TextView mTvNumber;
    @BindView(R.id.tv_time) TextView mTvTime;
    @BindView(R.id.nsv_content)
    NestedScrollView nestedScrollView;
    private String aId;



    public static void start(Context context, String aid){
        Intent intent=new Intent(context,ActivateDetailActivity.class);
        intent.putExtra(IVariable.A_ID,aid);
        context.startActivity(intent);
    }
    @Override
    protected void initEvent() {
        mWvHtml = (WebView)findViewById(R.id.wv_html);
        mWvHtml.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        WebSettings settings = mWvHtml.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        aId = getIntent().getStringExtra(IVariable.A_ID);
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                float absOffset=scrollY/300f;
                absOffset=absOffset>1?1:absOffset;
                mToolbar.setBackgroundColor(Color.argb((int) (absOffset * 255), 92 , 208, 194));

            }
        });
    }



    @Override
    protected void childAdd(MapUtils.Builder builder, int type) {
         builder.addAId(aId);
    }

    @Override
    protected String initUrl() {
        return IVariable.FIND_ACTIVITY_DETAIL;
    }




    @Override
    protected boolean isChangeBarColor() {
        return true;
    }

    @Override
    protected void onSuccess(ActiveDetailEvent activeDetailEvent) {
        dealData(activeDetailEvent);
    }



    private void dealData(ActiveDetailEvent event) {
        ActiveDetailBean activeDetail = GsonUtils.getObject(event.getResult(), ActiveDetailBean.class);
        ActiveDetailBean.DataBean data = activeDetail.getData();
        mWvHtml.loadUrl(data.getUrl());
        FrescoUtils.displayNormal(mIvBg,data.getTitle_img());
        FrescoUtils.displayIcon(mIvIcon,data.getActivity_img());
        mTvName.setText(data.getTitle());
        mTvContnet.setText(data.getContent());
        mTvMoney.setText("¥" + data.getPrice());
        mTvMoney2.setText("¥"+data.getPrice());
        mTvNumber.setText(data.getMax_people()+"人");
        mTvTime.setText(FormatDateUtils.FormatLongTime("yyyy.MM.dd",data.getStart_time())+"-"+FormatDateUtils.FormatLongTime("yyyy.MM.dd",data.getEnd_time()));
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_activate_detail;
    }

    @Override
    protected String initTitle() {
        return "活动详情";
    }
}
