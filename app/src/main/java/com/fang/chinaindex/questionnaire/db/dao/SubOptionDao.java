package com.fang.chinaindex.questionnaire.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.SubOption;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

/**
 * Created by Aspsine on 2015/9/22.
 */
public class SubOptionDao extends AbstractDao<SubOption> {

    public SubOptionDao(SQLiteDatabase db) {
        super(db);
    }

    private static final String TABLE_NAME = "SubOption";

    private static final String PARAMS = "subOptionId long, " +
            "surveyId long, " +
            "questionId long, " +
            "optionId long, " +
            "optionTitle text, " +
            "sort text, " +
            "isOther text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }
}
