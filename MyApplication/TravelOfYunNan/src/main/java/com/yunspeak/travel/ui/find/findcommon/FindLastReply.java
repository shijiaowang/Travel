package com.yunspeak.travel.ui.find.findcommon;

import com.yunspeak.travel.ui.find.findcommon.deliciousdetail.TravelReplyBean;

import java.util.List;

/**
 * Created by wangyang on 2016/8/25 0025.
 * 发现页面数据加载到最后一页时回复评论后返回的数据格式，
 * 需要转换
 */
public class FindLastReply {



    private int code;
    private String message;


    private List<TravelReplyBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<TravelReplyBean> getData() {
        return data;
    }

    public void setData(List<TravelReplyBean> data) {
        this.data = data;
    }


}
