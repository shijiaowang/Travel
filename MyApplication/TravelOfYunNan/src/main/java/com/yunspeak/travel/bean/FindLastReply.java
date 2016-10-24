package com.yunspeak.travel.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/8/25 0025.
 * 发现页面数据加载到最后一页时回复评论后返回的数据格式，
 * 需要转换
 */
public class FindLastReply {

    /**
     * code : 1
     * message : 评论成功
     * data : [{"id":"46","f_id":"12","user_id":"10009","pid":"0","content":"这只是一个测试评论而已，而已","reply_img":"","reply_time":"1472115545","floor":"44","type":"3","status":"1","like_count":"3","is_like":"0","nick_name":"我叫王小","user_img":"http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png","reply":{"floor":"","nick_name":"","content":"","reply_img":""}}]
     */

    private int code;
    private String message;
    /**
     * id : 46
     * f_id : 12
     * user_id : 10009
     * pid : 0
     * content : 这只是一个测试评论而已，而已
     * reply_img :
     * reply_time : 1472115545
     * floor : 44
     * type : 3
     * status : 1
     * like_count : 3
     * is_like : 0
     * nick_name : 我叫王小
     * user_img : http://192.168.1.38/Uploads/Picture/2016-08-16/57b288d6d58d6.png
     * reply : {"floor":"","nick_name":"","content":"","reply_img":""}
     */

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
