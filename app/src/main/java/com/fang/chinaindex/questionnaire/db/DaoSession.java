package com.fang.chinaindex.questionnaire.db;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.dao.SurveyInfoDao;
import com.fang.chinaindex.questionnaire.db.dao.UserDao;

/**
 * Created by Aspsine on 2015/5/26.
 */
public class DaoSession {
    private final SQLiteDatabase db;

    private final UserDao userDao;

    private final SurveyInfoDao surveyInfoDao;

    public DaoSession(SQLiteDatabase db) {
        this.db = db;

        this.userDao = new UserDao(db);
        this.surveyInfoDao = new SurveyInfoDao(db);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SurveyInfoDao getSurveyInfoDao() {
        return surveyInfoDao;
    }
}
