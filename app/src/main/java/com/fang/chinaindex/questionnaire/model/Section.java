package com.fang.chinaindex.questionnaire.model;

/**
 * Created by aspsine on 15/5/10.
 */
public class Section {
    private CharSequence title;
    private int id;
    private int iconResId;

    public Section(CharSequence title, int id, int iconResId) {
        this.title = title;
        this.id = id;
        this.iconResId = iconResId;
    }

    public CharSequence getTitle() {
        return title;
    }

    public void setTitle(CharSequence title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }
}
