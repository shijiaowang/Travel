package com.yunspeak.travel.bean;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.bean.BasecPriceBean;
import com.yunspeak.travel.bean.CouponBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/9 0009.
 */

public class ConfirmOrdersBean extends TravelsObject {

    private DataBean data;


    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {


        private OrderBean order;

        private List<BasecPriceBean> basec_price;


        private List<CouponBean> conpou;

        public OrderBean getOrder() {
            return order;
        }

        public void setOrder(OrderBean order) {
            this.order = order;
        }

        public List<BasecPriceBean> getBasec_price() {
            return basec_price;
        }

        public void setBasec_price(List<BasecPriceBean> basec_price) {
            this.basec_price = basec_price;
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
            private String pay_type;
            private String price;
            private String max_people;

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            private int  day;

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

            public String getMax_people() {
                return max_people;
            }

            public void setMax_people(String max_people) {
                this.max_people = max_people;
            }
        }

    }
}
