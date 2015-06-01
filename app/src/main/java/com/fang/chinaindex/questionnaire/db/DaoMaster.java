package com.fang.chinaindex.questionnaire.db;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.dao.LogicDao;
import com.fang.chinaindex.questionnaire.db.dao.OptionDao;
import com.fang.chinaindex.questionnaire.db.dao.QuestionDao;
import com.fang.chinaindex.questionnaire.db.dao.SurveyInfoDao;
import com.fang.chinaindex.questionnaire.db.dao.UserDao;
import com.fang.chinaindex.questionnaire.db.dao.UserSurveyInfoDao;
import com.fang.chinaindex.questionnaire.model.UserSurveyInfo;

/**
 * Created by Aspsine on 2015/5/26.
 */
public class DaoMaster {
    private final SQLiteDatabase db;

    public DaoMaster(SQLiteDatabase db) {
        this.db = db;
    }

    public static void createTable(SQLiteDatabase db) {
        UserDao.createTable(db);
        UserSurveyInfoDao.createTable(db);
        SurveyInfoDao.createTable(db);
        QuestionDao.createTable(db);
        OptionDao.createTable(db);
        LogicDao.createTable(db);
    }

    public static void dropTable(SQLiteDatabase db) {
        UserDao.dropTable(db);
        UserSurveyInfoDao.dropTable(db);
        SurveyInfoDao.dropTable(db);
        QuestionDao.dropTable(db);
        OptionDao.dropTable(db);
        LogicDao.dropTable(db);
    }

    public DaoSession newSession() {
        return new DaoSession(db);
    }

}
