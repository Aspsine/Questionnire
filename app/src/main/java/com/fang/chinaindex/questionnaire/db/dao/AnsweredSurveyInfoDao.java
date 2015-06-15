package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.ViewAnimationUtils;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class AnsweredSurveyInfoDao extends AbstractDao<SurveyInfo> {

    private static final String TABLE_NAME = "AnsweredSurvey";
    private static final String PARAMS = "surveyId long, " +
            "userId long, " +
            "startTime text, " + // start time of the survey
            "endTime text," +    // end time of the survey
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

    public AnsweredSurveyInfoDao(SQLiteDatabase db) {
        super(db);
    }

    public void save(String userId, String startTime, String endTime, SurveyInfo surveyInfo) {
        updateIfFailsInsert(userId, startTime, endTime, surveyInfo);
    }

    private void updateIfFailsInsert(String userId, String startTime, String endTime, SurveyInfo surveyInfo) {
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("startTime", startTime);
        values.put("endTime", endTime);
        values.put("surveyId", surveyInfo.getSurveyId());
        values.put("title", surveyInfo.getTitle());
        values.put("updateTime", surveyInfo.getUpdateTime());
        values.put("collectionEndTime", surveyInfo.getCollectionEndTime());
        values.put("typeId", surveyInfo.getTypeId());
        values.put("typeName", surveyInfo.getTypeName());
        values.put("companyName", surveyInfo.getCompanyName());
        if (db.update(TABLE_NAME, values, "surveyId=? and userId=? and startTime=?", new String[]{surveyInfo.getSurveyId(), userId}) == 0) {
            db.insert(TABLE_NAME, null, values);
        }
    }

    public SurveyInfo getAnsweredSurveyInfo(String userId, String surveyId, String startTime) {
        SurveyInfo surveyInfo = new SurveyInfo();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId=? and surveyId=? and startTime=?", new String[]{userId, surveyId, startTime});
        try {
            cursor.moveToFirst();
            surveyInfo.setSurveyId(cursor.getString(cursor.getColumnIndex("surveyId")));
            surveyInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            surveyInfo.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
            surveyInfo.setCollectionEndTime(cursor.getString(cursor.getColumnIndex("collectionEndTime")));
            surveyInfo.setTypeId(cursor.getString(cursor.getColumnIndex("typeId")));
            surveyInfo.setTypeName(cursor.getString(cursor.getColumnIndex("typeName")));
            surveyInfo.setCompanyName(cursor.getString(cursor.getColumnIndex("companyName")));
        } finally {
            cursor.close();
        }
        return surveyInfo;
    }

    public List<SurveyInfo> getAnsweredSurveyInfos(String userId) {
        List<SurveyInfo> surveyInfos = new ArrayList<SurveyInfo>();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId=?", new String[]{userId});
        try {
            cursor.moveToFirst();
            SurveyInfo surveyInfo = new SurveyInfo();
            surveyInfo.setSurveyId(cursor.getString(cursor.getColumnIndex("surveyId")));
            surveyInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            surveyInfo.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
            surveyInfo.setCollectionEndTime(cursor.getString(cursor.getColumnIndex("collectionEndTime")));
            surveyInfo.setTypeId(cursor.getString(cursor.getColumnIndex("typeId")));
            surveyInfo.setTypeName(cursor.getString(cursor.getColumnIndex("typeName")));
            surveyInfo.setCompanyName(cursor.getString(cursor.getColumnIndex("companyName")));
            surveyInfos.add(surveyInfo);
        } finally {
            cursor.close();
        }
        return surveyInfos;
    }

    public void delete(String userId, String surveyId, String startTime) {
        db.delete(TABLE_NAME, "userId=? and surveyId=? and startTime=?", new String[]{userId, surveyId, startTime});
    }
}
