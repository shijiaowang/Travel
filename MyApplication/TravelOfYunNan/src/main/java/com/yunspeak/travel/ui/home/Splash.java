package com.yunspeak.travel.ui.home;

/**
 * Created by wangyang on 2016/9/7 0007.
 */
public class Splash {
    public Splash( String title,String content) {
        this.content = content;
        this.title = title;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    private String content;
}
