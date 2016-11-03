package com.yunspeak.travel.ui.home.homesearch;

import com.yunspeak.travel.event.HttpEvent;

/**
 * Created by wangyang on 2016/10/31 0031.
 */

public class HomeSearchEvent extends HttpEvent {
    private int position;
    private String searchType;

    public String getSearchType() {
        return searchType;
    }

    public void setSearchType(String searchType) {
        this.searchType = searchType;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
