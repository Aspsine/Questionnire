package com.fang.chinaindex.questionnaire.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class AnsweredSurveyDao extends AbstractDao<SurveyInfo> {
    private static final String TABLE_NAME = "AnsweredSurvey";
    private static final String PARAMS = "surveyId long, " +
            "title text, " +
            "updateTime text, " +
            "collectionEndTime text, " +
            "typeId text, " +
            "typeName text, " +
            "companyName text";


    public static final void createTable(SQLiteDatabase db) {
        SQLUtils.createTable(TABLE_NAME, PARAMS);
    }

    public static final void dropTable(SQLiteDatabase db) {
        SQLUtils.createTable(TABLE_NAME, PARAMS);
    }

    public AnsweredSurveyDao(SQLiteDatabase db) {
        super(db);
    }
}
