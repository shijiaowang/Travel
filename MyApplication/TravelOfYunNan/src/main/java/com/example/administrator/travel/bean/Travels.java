package com.example.administrator.travel.bean;

/**
 * Created by Administrator on 2016/7/7 0007.
 */
public class Travels {
    private String pictureUrl;
    private String userId;
    private String userName;
    private String userNikeName;
    private String travelsDes;//游记简介

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNikeName() {
        return userNikeName;
    }

    public void setUserNikeName(String userNikeName) {
        this.userNikeName = userNikeName;
    }

    public String getTravelsDes() {
        return travelsDes;
    }

    public void setTravelsDes(String travelsDes) {
        this.travelsDes = travelsDes;
    }
}
