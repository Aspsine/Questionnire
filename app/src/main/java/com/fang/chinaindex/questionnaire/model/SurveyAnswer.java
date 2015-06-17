package com.fang.chinaindex.questionnaire.model;

/**
 * Created by Aspsine on 2015/5/5.
 */
public class SurveyAnswer {
    /**
     * 问题Id
     */
    private String IQuestionID;
    /**
     * 回答选项Id
     */
    private String SAnswers;
    /**
     * 开放题或者开放选项存的值(urlEncode)
     */
    private String SAnswersNote;
    /**
     * 问题回答时间
     */
    private String DAddTime;
    /**
     * 排序字段(只有在排序题中起作用，其他默认为1)
     */
    private String ISort;

    public SurveyAnswer() {
    }

    public SurveyAnswer(String IQuestionID, String SAnswers, String SAnswersNote, String DAddTime, String ISort) {
        this.IQuestionID = IQuestionID;
        this.SAnswers = SAnswers;
        this.SAnswersNote = SAnswersNote;
        this.DAddTime = DAddTime;
        this.ISort = ISort;
    }

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
