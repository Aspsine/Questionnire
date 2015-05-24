package com.fang.chinaindex.questionnaire.model;

/**
 * Created by aspsine on 15/5/23.
 */
public class UserSurveyInfo {
    private String userId;
    private String surveyId;

    public UserSurveyInfo(String userId, String surveyId) {
        this.userId = userId;
        this.surveyId = surveyId;
    }

    public UserSurveyInfo() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(String surveyId) {
        this.surveyId = surveyId;
    }
}
