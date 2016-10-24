package com.yunspeak.travel.ui.appoint.settingtitle;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by Administrator on 2016/9/6 0006.
 */
public class SettingTitleEvent extends HttpEvent {
    private SettingTitle settingTitle;

    public SettingTitle getSettingTitle() {
        return settingTitle;
    }

    public void setSettingTitle(SettingTitle settingTitle) {
        this.settingTitle = settingTitle;
    }
}
