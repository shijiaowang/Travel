package com.yunspeak.travel.ui.me.othercenter;

import com.yunspeak.travel.global.TravelsObject;
import com.yunspeak.travel.ui.me.myhobby.UserLabelBean;
import com.yunspeak.travel.ui.me.othercenter.userdynamic.DynamicBean;

import java.util.List;

/**
 * Created by wangyang on 2016/10/13 0013.
 */

public class OtherUserCenterBean extends TravelsObject {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private UserBean user;

        private List<DynamicBean> more;
        private List<UserLabelBean> user_label;

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<DynamicBean> getMore() {
            return more;
        }

        public void setMore(List<DynamicBean> more) {
            this.more = more;
        }

        public List<UserLabelBean> getUser_label() {
            return user_label;
        }

        public void setUser_label(List<UserLabelBean> user_label) {
            this.user_label = user_label;
        }
    }
}
