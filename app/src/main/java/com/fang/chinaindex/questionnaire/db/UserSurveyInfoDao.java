package com.fang.chinaindex.questionnaire.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.model.UserSurveyInfo;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspsine on 15/5/23.
 */
public class UserSurveyInfoDao {

    private static final String TABLE_NAME = "User_SurveyInfo";

    private static final String PARAMS = "userId long, surveyId long";

    private static UserSurveyInfoDao sInstance;

    private DBOpenHelper mHelper;

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public static UserSurveyInfoDao getInstance() {
        if (sInstance == null) {
            sInstance = new UserSurveyInfoDao();
        }
        return sInstance;
    }

    private UserSurveyInfoDao() {
        mHelper = DBOpenHelper.getInstance();
    }

    public void save(String userId, List<String> surveyIds) {
        if (existByUserId(userId)) {
            deleteByUserId(userId);
        } else {
            insert(userId, surveyIds);
        }
    }

    public void insert(String userId, String surveyId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("surveyId", surveyId);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public void insert(String userId, List<String> surveyIds) {
        List<UserSurveyInfo> userSurveyInfos = new ArrayList<UserSurveyInfo>();
        for (String surveyId : surveyIds) {
            userSurveyInfos.add(new UserSurveyInfo(userId, surveyId));
        }
        insert(userSurveyInfos);
    }

    public void insert(UserSurveyInfo userSurveyInfo) {
        String userId = userSurveyInfo.getUserId();
        String surveyId = userSurveyInfo.getSurveyId();
        insert(userId, surveyId);
    }

    public void insert(List<UserSurveyInfo> userSurveyInfos) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        try {
            db.beginTransaction();
            ContentValues values = new ContentValues();
            for (UserSurveyInfo userSurveyInfo : userSurveyInfos) {
                db.insert(TABLE_NAME, null, getContentValues(userSurveyInfo, values, true));
                values.clear();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
    }

    public void deleteByUserId(String userId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "userId = ?", new String[]{userId});
        db.close();
    }

    public void deleteBySurveyId(String surveyId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "survey = ?", new String[]{surveyId});
        db.close();
    }

    public void update() {

    }

    public List<String> getSurveyIdsByUserId(String userId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        List<String> surveyIds = new ArrayList<String>();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery("Select surveyId from " + TABLE_NAME + " where userId=?", new String[]{userId});
            while (cursor.moveToNext()) {
                String surveyId = cursor.getString(cursor.getColumnIndex("surveyId"));
                surveyIds.add(surveyId);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.close();
        return surveyIds;
    }

    private boolean existByUserId(String userId) {
        return getSurveyIdsByUserId(userId).size() > 0;
    }

    private ContentValues getContentValues(UserSurveyInfo userSurveyInfo, ContentValues contentValues, boolean userCache) {
        ContentValues values = userCache ? contentValues : new ContentValues();
        values.put("userId", userSurveyInfo.getUserId());
        values.put("surveyId", userSurveyInfo.getSurveyId());
        return values;
    }


}
