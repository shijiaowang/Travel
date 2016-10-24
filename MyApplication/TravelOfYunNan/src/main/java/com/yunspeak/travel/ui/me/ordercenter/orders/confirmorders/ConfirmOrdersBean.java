package com.yunspeak.travel.ui.me.ordercenter.orders.confirmorders;

import com.yunspeak.travel.ui.me.ordercenter.BasecPriceBean;
import com.yunspeak.travel.ui.me.ordercenter.CouponBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class ConfirmOrdersBean {


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


        private OrderBean order;

        private PayBean pay;


        private List<BasecPriceBean> basec_price;

        private List<CouponBean> order_conpou;

        private List<CouponBean> conpou;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public PayBean getPay() {
            return pay;
        }

        public void setPay(PayBean pay) {
            this.pay = pay;
        }

        public List<BasecPriceBean> getBasec_price() {
            return basec_price;
        }

        public void setBasec_price(List<BasecPriceBean> basec_price) {
            this.basec_price = basec_price;
        }

        public List<CouponBean> getOrder_conpou() {
            return order_conpou;
        }

        public void setOrder_conpou(List<CouponBean> order_conpou) {
            this.order_conpou = order_conpou;
        }

        public List<CouponBean> getConpou() {
            return conpou;
        }

        public void setConpou(List<CouponBean> conpou) {
            this.conpou = conpou;
        }

        public static class OrderBean {
            private String id;
            private String order_sn;
            private String status;
            private String user_id;
            private String price;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

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
        }

        public static class PayBean {
            private String id;
            private String money;
            private String order_sn;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getOrder_sn() {
                return order_sn;
            }

            public void setOrder_sn(String order_sn) {
                this.order_sn = order_sn;
            }
        }



    }
}
