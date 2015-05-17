package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aspsine on 15/5/12.
 */
public class Logic {
    /**
     * 逻辑主键ID
     */
    @SerializedName("iID")
    private String id;
    /**
     * 问卷问题id
     */
    @SerializedName("iQuestionID")
    private String questionId;
    /**
     * 回答的选项
     */
    @SerializedName("iSelectAnswer")
    private String selectAnswer;
    /**
     * 从哪题开始跳转
     */
    @SerializedName("iSkipFrom")
    private String skipFrom;
    /**
     * 跳转至那题
     */
    @SerializedName("iSkipTo")
    private String skipTo;
    /**
     * 跳转类型：
     * 1.单个转跳
     * 2.
     * 3.直接结束
     * 4.结束问卷并提交
     */
    @SerializedName("iLogicType")
    private String logicType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getSelectAnswer() {
        return selectAnswer;
    }

    public void setSelectAnswer(String selectAnswer) {
        this.selectAnswer = selectAnswer;
    }

    public String getSkipFrom() {
        return skipFrom;
    }

    public void setSkipFrom(String skipFrom) {
        this.skipFrom = skipFrom;
    }

    public String getSkipTo() {
        return skipTo;
    }

    public void setSkipTo(String skipTo) {
        this.skipTo = skipTo;
    }

    public String getLogicType() {
        return logicType;
    }

    public void setLogicType(String logicType) {
        this.logicType = logicType;
    }
}
