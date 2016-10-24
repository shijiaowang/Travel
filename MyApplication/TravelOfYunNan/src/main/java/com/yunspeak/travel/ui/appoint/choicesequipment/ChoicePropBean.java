package com.yunspeak.travel.ui.appoint.choicesequipment;

import java.util.List;

/**
 * Created by Administrator on 2016/9/18 0018.
 */
public class ChoicePropBean {

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
        /**
         * id : 1
         * name : 野营
         */

        private List<ProptypeBean> proptype;
        private List<ProplistsBean> proplists;

        public List<ProptypeBean> getProptype() {
            return proptype;
        }
        public void setProptype(List<ProptypeBean> proptype) {
            this.proptype = proptype;
        }
        public List<ProplistsBean> getProplists() {
            return proplists;
        }

        public void setProplists(List<ProplistsBean> proplists) {
            this.proplists = proplists;
        }

        public static class ProptypeBean {
            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }

        public static class ProplistsBean {
            private String id;
            private String tp_id;
            private String name;
            private String content;
            private String logo_img;
            private String number;
            private String price;
            private String add_time;
            private String status;
            private String popular;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTp_id() {
                return tp_id;
            }

            public void setTp_id(String tp_id) {
                this.tp_id = tp_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getLogo_img() {
                return logo_img;
            }

            public void setLogo_img(String logo_img) {
                this.logo_img = logo_img;
            }

            public String getNumber() {
                return number;
            }

            public void setNumber(String number) {
                this.number = number;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getPopular() {
                return popular;
            }

            public void setPopular(String popular) {
                this.popular = popular;
            }
        }
    }
}
