package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aspsine on 15/5/12.
 */
public class SurveyInfo {
    @SerializedName("iID")
    private String surveyId;
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

    /**
     * 问卷开始回答时间
     */
    private String startTime;

    /**
     * 问卷回答结束时间
     */
    private String endTime;

    /**
     * 是否已完成答卷
     */
    private boolean finished;

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public boolean isFinished() {
        return finished;
    }
}
