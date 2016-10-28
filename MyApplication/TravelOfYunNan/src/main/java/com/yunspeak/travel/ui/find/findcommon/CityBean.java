package com.yunspeak.travel.ui.find.findcommon;

/**
 * Created by wangyang on 2016/10/28 0028.
 */

public class CityBean {

        private String id;
        private String name;
        private String upid;
       private boolean isChecked;

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

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

        public String getUpid() {
            return upid;
        }

        public void setUpid(String upid) {
            this.upid = upid;
        }

}
