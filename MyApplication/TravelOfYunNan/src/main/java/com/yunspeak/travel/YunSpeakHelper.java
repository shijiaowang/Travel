package com.yunspeak.travel;

import com.yunspeak.travel.db.DBManager;
import com.hyphenate.easeui.controller.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;

public class YunSpeakHelper {
    public static void  setUserProfilePrivider(){
        EaseUI easeUI=EaseUI.getInstance();
        easeUI.setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {
            @Override
            public EaseUser getUser(String username) {
                return DBManager.getChatUserByChatId(username);
            }
        });
    }
}


