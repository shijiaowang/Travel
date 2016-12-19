package com.yunspeak.travel.ui.appoint.travelplan.personnelequipment.choicesequipment.costsetting.desremark.createsuccess;

import com.yunspeak.travel.global.TravelsObject;

/**
 * Created by wangyang on 2016/12/6 0006.
 */

public class CreateSuccessBean extends TravelsObject {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String content;
        private String id_code;
        private String title;
        private String share_url;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getId_code() {
            return id_code;
        }

        public void setId_code(String id_code) {
            this.id_code = id_code;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShare_url() {
            return share_url;
        }

        public void setShare_url(String share_url) {
            this.share_url = share_url;
        }
    }
}
