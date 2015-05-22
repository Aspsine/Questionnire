package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aspsine on 15-5-9.
 */
public class UserInfo {
    @SerializedName("iUserID")
    private long userId;
    @SerializedName("sUserName")
    private String userName;
    @SerializedName("dPermissionEndTime")
    private String permissionEndTime;
    @SerializedName("sRealName")
    private String realName;
    @SerializedName("sEmail")
    private String email;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPermissionEndTime() {
        return permissionEndTime;
    }

    public void setPermissionEndTime(String permissionEndTime) {
        this.permissionEndTime = permissionEndTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
