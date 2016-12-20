package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

import java.util.List;

/**
 * Created by wangyang on 2016/8/25 0025.
 * 发现页面数据加载到最后一页时回复评论后返回的数据格式，
 * 需要转换
 */
public class FindLastReply extends TravelsObject {



    private List<TravelReplyBean> data;

    public List<TravelReplyBean> getData() {
        return data;
    }

    public void setData(List<TravelReplyBean> data) {
        this.data = data;
    }


}
