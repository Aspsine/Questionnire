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
 * Created by Aspsine on 2015/5/22.
 */
public class SurveyInfoDao extends AbstractDao<SurveyInfo> {

    private static final String TABLE_NAME = "SurveyInfo";

    private static final String PARAMS = "surveyId long, title text, updateTime text, collectionEndTime text, typeId text, typeName text, companyName text, userId long";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public SurveyInfoDao(SQLiteDatabase db) {
        super(db);
    }

    public void save(List<SurveyInfo> surveyInfos, String userId) throws Exception {
        if (exist(userId)) {
            deleteByUserId(userId);
            insert(surveyInfos, userId);
        }
    }

    public void insert(List<SurveyInfo> surveyInfos, String userId) {
        db.beginTransaction();
        try {
            ContentValues contentValues = new ContentValues();
            for (SurveyInfo surveyInfo : surveyInfos) {
                db.insert(TABLE_NAME, null, getContentValues(surveyInfo, userId, contentValues, true));
                contentValues.clear();
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void insert(SurveyInfo surveyInfo, String userId) {
        db.insert(TABLE_NAME, null, getContentValues(surveyInfo, userId, null, false));
    }

    public void delete(String surveyId, String userId) {
        db.delete(TABLE_NAME, "surveyId=?, userId=?", new String[]{surveyId, userId});
    }

    public void deleteByUserId(String userId) {
        db.delete(TABLE_NAME, "userId=?", new String[]{userId});
    }

    private void deleteBySurveyId(String surveyId) {
        db.delete(TABLE_NAME, "surveyId=?", new String[]{surveyId});
    }

    private void update(SurveyInfo surveyInfo, String userId) {
        db.update(TABLE_NAME, getContentValues(surveyInfo, userId, null, false), "userId=?", new String[]{userId});
    }

    public List<SurveyInfo> getSurveyInfos(String userId){
        List<SurveyInfo> surveyInfos = new ArrayList<SurveyInfo>();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId=?", new String[]{userId});
            while (cursor.moveToNext()) {
                SurveyInfo surveyInfo = new SurveyInfo();
                surveyInfo.setSurveyId(cursor.getString(cursor.getColumnIndex("surveyId")));
                surveyInfo.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                surveyInfo.setUpdateTime(cursor.getString(cursor.getColumnIndex("updateTime")));
                surveyInfo.setCollectionEndTime(cursor.getString(cursor.getColumnIndex("collectionEndTime")));
                surveyInfo.setTypeId(cursor.getString(cursor.getColumnIndex("typeId")));
                surveyInfo.setTypeName(cursor.getString(cursor.getColumnIndex("typeName")));
                surveyInfo.setCompanyName(cursor.getString(cursor.getColumnIndex("companyName")));
                surveyInfos.add(surveyInfo);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return surveyInfos;
    }

    public boolean exist(String surveyId, String userId) throws Exception {
        List<SurveyInfo> surveyInfos = getSurveyInfos(userId);
        for (SurveyInfo surveyInfo : surveyInfos) {
            if (surveyInfo.getSurveyId().equals(surveyId)) {
                return true;
            }
        }
        return false;
    }

    public boolean exist(String userId) throws Exception {
        List<SurveyInfo> surveyInfos = getSurveyInfos(userId);
        return surveyInfos.size() > 0;
    }

    private ContentValues getContentValues(SurveyInfo surveyInfo, String userId, ContentValues contentValues, boolean userCache) {
        ContentValues values = userCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyInfo.getSurveyId());
        values.put("title", surveyInfo.getTitle());
        values.put("updateTime", surveyInfo.getUpdateTime());
        values.put("collectionEndTime", surveyInfo.getCollectionEndTime());
        values.put("typeId", surveyInfo.getTypeId());
        values.put("typeName", surveyInfo.getTypeName());
        values.put("companyName", surveyInfo.getCompanyName());
        values.put("userId", userId);
        return values;
    }
}
