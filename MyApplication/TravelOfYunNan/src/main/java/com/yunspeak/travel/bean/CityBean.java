package com.yunspeak.travel.bean;

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
        private int minLevel;//用于单选 酒店
        private int maxLevel;//用于单选 酒店
        private boolean isChecked;
    public CityBean(){

    }
    public CityBean(String id, int minLevel, int maxLevel, String upid, String name) {
        this.id = id;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.upid = upid;
        this.name = name;
    }

    public CityBean(String name, String id, boolean isChecked) {
        this.name = name;
        this.id = id;
        this.isChecked = isChecked;
    }

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
