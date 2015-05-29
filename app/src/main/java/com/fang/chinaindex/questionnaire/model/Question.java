package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aspsine on 15/5/12.
 */
public class Question {
    /**
     * 问题所在问卷中的id
     */
    @SerializedName("iID")
    private String id;
    /**
     * 问题序号（页面上展示的Q2）
     */
    @SerializedName("QNum")
    private String qNum;
    /**
     *问题所在题库中的Id（暂时无用）
     */
    @SerializedName("iQuestionID")
    private String questionId;
    /**
     * 问题所在题库中的编号
     */
    @SerializedName("sShowID")
    private String showId;
    /**
     * 问题标题
     */
    @SerializedName("sQuestionTilte")
    private String questionTitle;
    /**
     * 是否必答题
     */
    @SerializedName("bISMust")
    private String isMust;
    /**
     * catalogID
     */
    @SerializedName("iCatalogID")
    private String catalogId;
    /**
     *问题排序
     */
    @SerializedName("iSort")
    private String sort;
    /**
     * 目录的排序字段（用不到）
     */
    @SerializedName("iCatalogSort")
    private String catalogSort;
    /**
     * 分值或开放回答个数
     */
    @SerializedName("iScore")
    private String score;
    /**
     * 问题类型id
     */
    @SerializedName("iCategory")
    private String category;
    /**
     * 问题类型名称（单选题）
     */
    @SerializedName("sCategoryText")
    private String categoryText;
    /**
     * 打分题模版ID（多选，打分题用）
     */
    @SerializedName("iTemplateID")
    private String templateId;
    /**
     * 最大允许回答个数
     */
    @SerializedName("iAnswerNumber")
    private String answerNumber;
    /**
     * 问题描述，在打分题后面会有一段描述
     */
    @SerializedName("sDescription")
    private String description;

    private List<Option> options;
    private List<RecommendAnswer> recommendAnswers;
    private List<Logic> logics;

    /**
     * 上传问卷使用
     * 答题时间（格式：2014-08-18 15:02:38）
     */
    private String answeredTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getqNum() {
        return qNum;
    }

    public void setqNum(String qNum) {
        this.qNum = qNum;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getIsMust() {
        return isMust;
    }

    public void setIsMust(String isMust) {
        this.isMust = isMust;
    }

    public String getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String catalogId) {
        this.catalogId = catalogId;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getCatalogSort() {
        return catalogSort;
    }

    public void setCatalogSort(String catalogSort) {
        this.catalogSort = catalogSort;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public String getAnswerNumber() {
        return answerNumber;
    }

    public void setAnswerNumber(String answerNumber) {
        this.answerNumber = answerNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public List<RecommendAnswer> getRecommendAnswers() {
        return recommendAnswers;
    }

    public void setRecommendAnswers(List<RecommendAnswer> recommendAnswers) {
        this.recommendAnswers = recommendAnswers;
    }

    public List<Logic> getLogics() {
        return logics;
    }

    public void setLogics(List<Logic> logics) {
        this.logics = logics;
    }

    public String getAnsweredTime() {
        return answeredTime;
    }

    public void setAnsweredTime(String answeredTime) {
        this.answeredTime = answeredTime;
    }
}
