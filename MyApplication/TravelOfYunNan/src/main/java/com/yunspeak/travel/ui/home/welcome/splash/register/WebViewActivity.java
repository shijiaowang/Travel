package com.yunspeak.travel.ui.home.welcome.splash.register;

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

    @Override
    protected void initOptions() {
        pbProgress.setMax(100);
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
       mWvView.loadUrl(IVariable.PROTOCOL);
    }

    @Override
    protected String initTitle() {
        return "城外旅游软件服务及许可协议";
    }


}
