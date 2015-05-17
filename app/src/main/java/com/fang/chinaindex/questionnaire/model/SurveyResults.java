package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aspsine on 15/5/12.
 */
public class SurveyResults {
    private String errorMessage;
    @SerializedName("surveys")
    private List<SurveyInfo> surveyInfos;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<SurveyInfo> getSurveyInfos() {
        return surveyInfos;
    }

    public void setSurveyInfos(List<SurveyInfo> surveyInfos) {
        this.surveyInfos = surveyInfos;
    }
}
