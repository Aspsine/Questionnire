package com.fang.chinaindex.questionnaire.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by aspsine on 15/5/12.
 */
public class Option implements Comparable<Option> {
    @SerializedName("iID")
    private String id;
    @SerializedName("sOptionTitle")
    private String optionTitle;
    @SerializedName("iSort")
    private String sort;
    @SerializedName("bISOther")
    private boolean isOther;
    private List<Scoring> scorings;

    /**
     * aspsine add inOrder to indicate if the option is a answer;
     */
    private boolean checked;

    private String openAnswer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOptionTitle() {
        return optionTitle;
    }

    public void setOptionTitle(String optionTitle) {
        this.optionTitle = optionTitle;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public boolean isOther() {
        return isOther;
    }

    public void setIsOther(boolean isOther) {
        this.isOther = isOther;
    }

    public List<Scoring> getScorings() {
        return scorings;
    }

    public void setScorings(List<Scoring> scorings) {
        this.scorings = scorings;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getOpenAnswer() {
        return openAnswer;
    }

    public void setOpenAnswer(String openAnswer) {
        this.openAnswer = openAnswer;
    }

    @Override
    public int compareTo(Option another) {
        return Integer.valueOf(this.sort) - Integer.valueOf(another.sort);
    }
}
