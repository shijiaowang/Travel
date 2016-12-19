package com.yunspeak.travel.ui.find.findcommon;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.utils.StringUtils;

/**
 * Created by wangyang on 2016/10/28 0028.
 */

public class CityBean extends TravelsObject {

        private String id;
        private String name;
        private String upid;
        private String type;//这个是用在appointFragment中的
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
            if (StringUtils.isEmpty(type) && StringUtils.isEmpty(upid)){
                return "3";//返回3是玩法
            }
            return StringUtils.isEmpty(type)?upid:type;
        }

        public void setUpid(String upid) {
            this.upid = upid;
        }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
