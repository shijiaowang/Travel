package com.yunspeak.travel.bean;

import simpledao.cityoff.com.easydao.annotation.TableName;
import simpledao.cityoff.com.easydao.annotation.UpdateKey;

/**
 * Created by wangyang on 2017/3/3.
 * 保存主页数据
 */
@TableName("home_data")
public class HomePageDataBean {
    @UpdateKey
    private String name;
    private String pageContent;

    public HomePageDataBean(String name, String pageContent) {
        this.name = name;
        this.pageContent = pageContent;
    }

    public String getPageContent() {

        return pageContent;
    }

    public void setPageContent(String pageContent) {
        this.pageContent = pageContent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
