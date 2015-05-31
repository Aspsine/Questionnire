package com.fang.chinaindex.questionnaire.db;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.dao.LogicDao;
import com.fang.chinaindex.questionnaire.db.dao.OptionDao;
import com.fang.chinaindex.questionnaire.db.dao.QuestionDao;
import com.fang.chinaindex.questionnaire.db.dao.SurveyInfoDao;
import com.fang.chinaindex.questionnaire.db.dao.UserDao;
import com.fang.chinaindex.questionnaire.db.dao.UserSurveyInfoDao;

/**
 * Created by Aspsine on 2015/5/26.
 */
public class DaoSession {
    private final SQLiteDatabase db;

    private final UserDao userDao;

    private final SurveyInfoDao surveyInfoDao;

    private final UserSurveyInfoDao userSurveyInfoDao;

    private final QuestionDao questionDao;

    private final OptionDao optionDao;

    private final LogicDao logicDao;

    public DaoSession(SQLiteDatabase db) {
        this.db = db;

        this.userDao = new UserDao(db);
        this.surveyInfoDao = new SurveyInfoDao(db);
        this.userSurveyInfoDao = new UserSurveyInfoDao(db);
        this.questionDao = new QuestionDao(db);
        this.optionDao = new OptionDao(db);
        this.logicDao = new LogicDao(db);
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return this.db;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public SurveyInfoDao getSurveyInfoDao() {
        return surveyInfoDao;
    }

    public UserSurveyInfoDao getUserSurveyInfoDao() {
        return userSurveyInfoDao;
    }

    public QuestionDao getQuestionDao() {
        return questionDao;
    }

    public OptionDao getOptionDao() {
        return optionDao;
    }

    public LogicDao getLogicDao() {
        return logicDao;
    }
}
