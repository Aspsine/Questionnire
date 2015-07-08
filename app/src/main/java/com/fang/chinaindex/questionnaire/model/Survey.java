package com.fang.chinaindex.questionnaire.model;

import java.util.List;

/**
 * Created by aspsine on 15/5/12.
 */
public class Survey {
    private SurveyInfo info;
    private List<Question> questions;

    public Survey() {
    }

    public Survey(SurveyInfo info, List<Question> questions) {
        this.info = info;
        this.questions = questions;
    }

    public SurveyInfo getInfo() {
        return info;
    }

    public void setInfo(SurveyInfo info) {
        this.info = info;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
}
