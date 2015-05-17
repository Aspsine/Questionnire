package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aspsine on 15/5/12.
 */
public class SurveyInfo {
    @SerializedName("iID")
    private String id;
    @SerializedName("sTitle")
    private String title;
    @SerializedName("dUpdateTime")
    private String updateTime;
    @SerializedName("dCollectEndTime")
    private String collectionEndTime;
    @SerializedName("iTypeID")
    private String typeId;
    @SerializedName("sTypeName")
    private String typeName;
    @SerializedName("sCompanyName")
    private String companyName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCollectionEndTime() {
        return collectionEndTime;
    }

    public void setCollectionEndTime(String collectionEndTime) {
        this.collectionEndTime = collectionEndTime;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
