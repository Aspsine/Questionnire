package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aspsine on 15/5/12.
 */
public class Scoring {
    @SerializedName("iID")
    private String id;
    @SerializedName("sScoringOptionTitle")
    private String scoringOptionTitle;
    @SerializedName("iSort")
    private String sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScoringOptionTitle() {
        return scoringOptionTitle;
    }

    public void setScoringOptionTitle(String scoringOptionTitle) {
        this.scoringOptionTitle = scoringOptionTitle;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
