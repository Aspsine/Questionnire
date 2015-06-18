package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class AnsweredOptionDao extends AbstractDao<Option> {

    private static final String TABLE_NAME = "AnsweredOption";

    private static final String PARAMS = "optionId long, " +
            "surveyId long, " +
            "questionId long, " +
            "userId long, " +
            "startTime text, " +
            "checked boolean, " +
            "optionTitle text, " +
            "sort text, " +
            "openAnswer text, " +
            "isOther text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public AnsweredOptionDao(SQLiteDatabase db) {
        super(db);
    }

    public void save(String userId, String surveyId, String questionId, String startTime, List<Option> options) {
        updateIfFailsInsert(userId, surveyId, questionId, startTime, options);
    }

    private void updateIfFailsInsert(String userId, String surveyId, String questionId, String startTime, List<Option> options) {
        ContentValues contentValues = new ContentValues();
        for (Option option : options) {
            if (option.isChecked()) {
                ContentValues values = getContentValues(userId, surveyId, questionId, startTime, option, contentValues, true);
                if (db.update(TABLE_NAME, values,
                        "userId = ? and surveyId = ? and questionId = ? and optionId = ? and startTime =?",
                        new String[]{userId, surveyId, questionId, option.getId(), startTime}) == 0) {
                    db.insert(TABLE_NAME, null, values);
                }
                contentValues.clear();
            }
        }
    }

    public List<Option> getAnsweredOptions(String userId, String surveyId, String questionId, String startTime) {
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId=? and surveyId=? and questionId=? and startTime=?", new String[]{userId, surveyId, questionId, startTime});
        List<Option> options = new ArrayList<Option>();
        try {
            while (cursor.moveToNext()) {
                Option option = new Option();
                option.setId(cursor.getString(cursor.getColumnIndex("optionId")));
                option.setOptionTitle(cursor.getString(cursor.getColumnIndex("optionTitle")));
                option.setSort(cursor.getString(cursor.getColumnIndex("sort")));
                option.setIsOther(cursor.getInt(cursor.getColumnIndex("isOther")) == 1);
                option.setChecked(cursor.getInt(cursor.getColumnIndex("checked")) == 1);
                option.setOpenAnswer(cursor.getString(cursor.getColumnIndex("openAnswer")));
                options.add(option);
            }
        } finally {
            cursor.close();
        }
        return options;
    }

    public ContentValues getContentValues(String userId, String surveyId, String questionId, String startTime, Option option, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("userId", userId);
        values.put("surveyId", surveyId);
        values.put("questionId", questionId);
        values.put("optionId", option.getId());
        values.put("startTime", startTime);
        values.put("checked", option.isChecked());
        values.put("optionTitle", option.getOptionTitle());
        values.put("sort", option.getSort());
        values.put("openAnswer", option.getOpenAnswer());
        values.put("isOther", option.isOther());
        return values;
    }

    public void delete(String userId, String surveyId, String startTime) {
        db.delete(TABLE_NAME, "userId=? and surveyId=? and startTime=?", new String[]{userId, surveyId, startTime});
    }

    public void deleteOptions(String userId, String surveyId, String questionId, String startTime) {
        db.delete(TABLE_NAME, "userId=? and surveyId=? and questionId=? and startTime=?", new String[]{userId, surveyId, questionId, startTime});
    }
}
