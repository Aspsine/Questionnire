package com.fang.chinaindex.questionnaire.model;

import java.util.List;

/**
 * Created by aspsine on 15/5/12.
 */
public class SurveyDetails {
    private String errorMessage;
    private List<Survey> surveys;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
}
