package com.fang.chinaindex.questionnaire.model;

/**
 * Created by Aspsine on 2015/5/5.
 */
public class SurveyAnswer {
    private String IQuestionID;
    private String SAnswers;
    private String SAnswersNote;
    private String DAddTime;
    private String ISort;

    public String getIQuestionID() {
        return IQuestionID;
    }

    public void setIQuestionID(String IQuestionID) {
        this.IQuestionID = IQuestionID;
    }

    public String getSAnswers() {
        return SAnswers;
    }

    public void setSAnswers(String SAnswers) {
        this.SAnswers = SAnswers;
    }

    public String getSAnswersNote() {
        return SAnswersNote;
    }

    public void setSAnswersNote(String SAnswersNote) {
        this.SAnswersNote = SAnswersNote;
    }

    public String getDAddTime() {
        return DAddTime;
    }

    public void setDAddTime(String DAddTime) {
        this.DAddTime = DAddTime;
    }

    public String getISort() {
        return ISort;
    }

    public void setISort(String ISort) {
        this.ISort = ISort;
    }
}
