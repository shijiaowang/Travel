package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.backmoney;

/**
 * Created by wangyang on 2016/12/12 0012.
 */

public class BackMoneyBean {
    private int code;
    private String message;

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String tid;
        private int type;
        private String start_time;
        private String day;
        private String pay_money;
        private String refund_money;

        public String getTid() {
            return tid;
        }

        public void setTid(String tid) {
            this.tid = tid;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getPay_money() {
            return pay_money;
        }

        public void setPay_money(String pay_money) {
            this.pay_money = pay_money;
        }

        public String getRefund_money() {
            return refund_money;
        }

        public void setRefund_money(String refund_money) {
            this.refund_money = refund_money;
        }
    }
}
