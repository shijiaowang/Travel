package com.yunspeak.travel.ui.home.welcome.splash.register;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yunspeak.travel.R;
import com.yunspeak.travel.global.IVariable;
import com.yunspeak.travel.ui.baseui.BaseToolBarActivity;
import com.yunspeak.travel.utils.LogUtils;
import com.yunspeak.travel.utils.StringUtils;

import butterknife.BindView;

/**
 * Created by wangyang on 2016/12/1 0001.
 */

public class WebViewActivity extends BaseToolBarActivity {

    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;
    @BindView(R.id.wv_html)
    WebView mWvView;

    @Override
    protected int initLayoutRes() {
        return R.layout.activity_web_view;
    }
    public static void start(Context context,String title,String url){
        Intent intent=new Intent(context,WebViewActivity.class);
        intent.putExtra(IVariable.TITLE,title);
        intent.putExtra(IVariable.URL,url);
        context.startActivity(intent);
    }
    @Override
    protected void initOptions() {
        pbProgress.setMax(100);
        String title = getIntent().getStringExtra(IVariable.TITLE);
        mTvTitle.setText(StringUtils.isEmpty(title)?"城外旅游软件服务及许可协议":title);
        String url = getIntent().getStringExtra(IVariable.URL);
        url=StringUtils.isEmpty(url)?IVariable.PROTOCOL:url;
        mWvView = (WebView)findViewById(R.id.wv_html);
        mWvView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return true;
            }
        });
        WebSettings settings = mWvView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(false);
        mWvView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pbProgress.setProgress(newProgress);
                if (newProgress==100){
                    pbProgress.setVisibility(View.GONE);
                }
            }
        });
       mWvView.loadUrl(url);
    }

    @Override
    protected String initTitle() {
        return "城外旅游软件服务及许可协议";
    }


}
