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
        private String price;
        private int total_price;
        private String pay_way;
        private String conpou;
        /**
         * id : 1
         * key : 平台服务费
         * value : 20
         * type : 1
         * time : 1469586077
         * status : 1
         */

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

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getConpou() {
            return conpou;
        }

        public void setConpou(String conpou) {
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
