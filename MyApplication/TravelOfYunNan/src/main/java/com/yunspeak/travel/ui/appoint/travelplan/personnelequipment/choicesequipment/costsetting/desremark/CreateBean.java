package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark;

/**
 * Created by wangyang on 2016/12/2 0002.
 */

public class CreateBean {


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
