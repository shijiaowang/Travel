package com.yunspeak.travel.ui.me.about.model;
import android.content.Intent;
import android.view.View;
import com.yunspeak.travel.ui.home.welcome.splash.register.WebViewActivity;
import com.yunspeak.travel.utils.UIUtils;

/**
 * Created by wangyang on 2017/3/30.
 */

public class AboutModel {
    private String version;
    public AboutModel(){
        version= UIUtils.getVersion(UIUtils.getContext());
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void onLinkClick(View view){
        view.getContext().startActivity(new Intent(view.getContext(), WebViewActivity.class));
    }

}
