package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspsine on 15/5/23.
 */
public class UserSurveyInfoDao extends AbstractDao<UserSurveyInfoDao> {

    private static final String TABLE_NAME = "User_SurveyInfo";

    private static final String PARAMS = "userId long, surveyId long";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public UserSurveyInfoDao(SQLiteDatabase db) {
        super(db);
    }

    public void save(String userId, List<SurveyInfo> surveyInfos) {
        if (existByUserId(userId)) {
            deleteByUserId(userId);
        } else {
            insert(userId, surveyInfos);
        }
    }

    public void insert(String userId, List<SurveyInfo> surveyInfos) {
        ContentValues values = new ContentValues();
        for (SurveyInfo surveyInfo : surveyInfos) {
            db.insert(TABLE_NAME, null, getContentValues(userId, surveyInfo.getSurveyId(), values, true));
            values.clear();
        }
    }


    public void deleteByUserId(String userId) {
        db.delete(TABLE_NAME, "userId = ?", new String[]{userId});
    }

    public void deleteBySurveyId(String surveyId) {
        db.delete(TABLE_NAME, "survey = ?", new String[]{surveyId});
    }

    public void update() {

    }

    public List<String> getSurveyIdsByUserId(String userId) {
        List<String> surveyIds = new ArrayList<String>();
        Cursor cursor = db.rawQuery("Select surveyId from " + TABLE_NAME + " where userId=?", new String[]{userId});
        try {
            while (cursor.moveToNext()) {
                String surveyId = cursor.getString(cursor.getColumnIndex("surveyId"));
                surveyIds.add(surveyId);
            }
        } finally {
            cursor.close();
        }
        return surveyIds;
    }

    private boolean existByUserId(String userId) {
        return getSurveyIdsByUserId(userId).size() > 0;
    }

    private ContentValues getContentValues(String userId, String surveyId, ContentValues contentValues, boolean userCache) {
        ContentValues values = userCache ? contentValues : new ContentValues();
        values.put("userId", userId);
        values.put("surveyId", surveyId);
        return values;
    }


}
