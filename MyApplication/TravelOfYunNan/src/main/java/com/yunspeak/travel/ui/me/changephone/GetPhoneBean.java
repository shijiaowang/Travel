package com.yunspeak.travel.ui.me.changephone;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/10/6 0006.
 * 获取用户当前的手机号
 */

public class GetPhoneBean extends TravelsObject {



    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id;
        private String tel;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
