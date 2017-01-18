package com.yunspeak.travel.ui.home.welcome.homeswitch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.GlobalValue;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.appoint.searchappoint.SearchAppointActivity;
import com.yunspeak.travel.ui.appoint.travelplan.TravelsPlanActivity;
import com.yunspeak.travel.ui.appoint.travelplan.TravelsPlanWithMeActivity;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wangyang on 2017/1/18.
 * 首页选择跳转的页面
 */

public class HomeSwitchActivity extends BaseToolBarActivity {
    public static final int GO_SEARCH_APPOINT = 1;
    public static final int GO_TOGETHER = 2;
    public static final int GO_WITH_ME = 3;
    @BindView(R.id.wv_html)
    WebView wvHtml;
    @BindView(R.id.bt_start)
    Button btStart;
    private int choose;

    public static void start(Context context, String url, int choose) {
        Intent intent = new Intent(context, HomeSwitchActivity.class);
        intent.putExtra(IVariable.URL, url);
        intent.putExtra(IVariable.POSITION, choose);
        context.startActivity(intent);
    }

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_switch;
    }

    @Override
    protected void initOptions() {
        choose = getIntent().getIntExtra(IVariable.POSITION, -1);
        mTvTitle.setText(initTitle());
        String url = getIntent().getStringExtra(IVariable.URL);
        wvHtml.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        wvHtml.loadUrl(url);
    }

    @Override
    protected String initTitle() {
        String title = "";
        switch (choose) {
            case GO_SEARCH_APPOINT:
                title = "寻伴出游";
                btStart.setText("进入约伴");
                break;
            case GO_TOGETHER:
                title = "老司机须知";
                btStart.setText("发布约伴-带人玩");
                break;
            case GO_WITH_ME:
                title = "定制形成";
                btStart.setText("发布约伴-找人带");
                break;
        }
        return title;
    }


    @OnClick(R.id.bt_start)
    public void onClick() {
        switch (choose) {
            case GO_SEARCH_APPOINT:
               startActivity(new Intent(this, SearchAppointActivity.class));
                break;
            case GO_TOGETHER:
                GlobalValue.mAppointType = IVariable.TYPE_TOGETHER;
                startActivity(new Intent(this, TravelsPlanActivity.class));
                break;
            case GO_WITH_ME:
                GlobalValue.mAppointType = IVariable.TYPE_TOGETHER;
                startActivity(new Intent(this, TravelsPlanWithMeActivity.class));
                break;
        }
    }
}
