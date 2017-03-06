package com.yunspeak.travel.db;

import android.text.TextUtils;

import com.hyphenate.easeui.domain.UserInfo;

import java.util.List;

import simpledao.cityoff.com.easydao.BaseEasyDao;

/**
 * Created by wangyang on 2017/3/3.
 * 环信用户信息处理
 */

public class ChatDao extends BaseEasyDao<UserInfo> {
    /**
     * 插入向天对象数据
     *
     * @param userInfos 聊天对象集合
     */
    public void insertChatUserInfo(List<UserInfo> userInfos) {
        if (userInfos == null) return;
        for (UserInfo userInfo : userInfos) {
            if (userInfo == null || TextUtils.isEmpty(userInfo.getId())) continue;
            updateOrInsert(userInfo.getId(),userInfo);
        }
    }


    public void updateOrInsert(String id, UserInfo object) {
        UserInfo userInfo = new UserInfo(id);
        super.updateOrInsert(userInfo, object);
    }
}
