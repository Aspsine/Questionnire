package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class OptionDao extends AbstractDao<Option> {

    private static final String TABLE_NAME = "Option";

    private static final String PARAMS = "_id integer autoincrement, " +
            "optionId long, " +
            "surveyId, long, " +
            "questionId long, " +
            "optionTitle text, " +
            "sort text " +
            "isOther text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public OptionDao(SQLiteDatabase db) {
        super(db);
    }

    public void replace(String surveyId, String questionId, List<Option> options) {
        ContentValues contentValues = new ContentValues();
        for (Option option : options) {
            db.replace(TABLE_NAME, null, getContentValues(surveyId, questionId, option, contentValues, true));
            contentValues.clear();
        }
    }

    public ContentValues getContentValues(String surveyId, String questionId, Option option, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyId);
        values.put("questionId", questionId);
        values.put("optionId", option.getId());
        values.put("optionTitle", option.getOptionTitle());
        values.put("sort", option.getSort());
        values.put("isOther", option.isOther());
        return values;
    }
}
