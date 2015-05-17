package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by aspsine on 15/5/12.
 */
public class RecommendAnswer {
    /**
     * 开放提推荐选项id
     */
    @SerializedName("iID")
    private String id;
    /**
     * 内容
     */
    @SerializedName("sTitle")
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
