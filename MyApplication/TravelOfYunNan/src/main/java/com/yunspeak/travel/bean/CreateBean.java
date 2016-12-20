package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/12/2 0002.
 */

public class CreateBean extends TravelsObject {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String id_code;
        private String ret_order;
        private int pay_type;

        public String getId_code() {
            return id_code;
        }

        public void setId_code(String id_code) {
            this.id_code = id_code;
        }

        public String getRet_order() {
            return ret_order;
        }

        public void setRet_order(String ret_order) {
            this.ret_order = ret_order;
        }

        public int getPay_type() {
            return pay_type;
        }

        public void setPay_type(int pay_type) {
            this.pay_type = pay_type;
        }
    }
}
