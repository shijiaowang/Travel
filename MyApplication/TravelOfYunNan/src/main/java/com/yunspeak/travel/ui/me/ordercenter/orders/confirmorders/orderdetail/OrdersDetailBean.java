package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders.orderdetail;

import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class OrdersDetailBean{


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
        private String order_sn;
        private String status;
        private String user_id;
        private String pay_type;
        private String price;
        private int total_price;
        private String pay_name;
        private String pay_status;
        private int conpou;

        public int getDay() {
            return day;
        }

        public void setDay(int day) {
            this.day = day;
        }

        private int day;

        private List<BasecPriceBean> basec_price;

        public String getOrder_sn() {
            return order_sn;
        }

        public void setOrder_sn(String order_sn) {
            this.order_sn = order_sn;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public int getTotal_price() {
            return total_price;
        }

        public void setTotal_price(int total_price) {
            this.total_price = total_price;
        }

        public String getPay_name() {
            return pay_name;
        }

        public void setPay_name(String pay_name) {
            this.pay_name = pay_name;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public int getConpou() {
            return conpou;
        }

        public void setConpou(int conpou) {
            this.conpou = conpou;
        }

        public List<BasecPriceBean> getBasec_price() {
            return basec_price;
        }

        public void setBasec_price(List<BasecPriceBean> basec_price) {
            this.basec_price = basec_price;
        }

    }
}
