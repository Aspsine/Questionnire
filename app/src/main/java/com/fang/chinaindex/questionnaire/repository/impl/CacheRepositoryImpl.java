package com.fang.chinaindex.questionnaire.repository.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.DaoSession;
import com.fang.chinaindex.questionnaire.db.dao.LogicDao;
import com.fang.chinaindex.questionnaire.db.dao.OptionDao;
import com.fang.chinaindex.questionnaire.db.dao.QuestionDao;
import com.fang.chinaindex.questionnaire.db.dao.SurveyInfoDao;
import com.fang.chinaindex.questionnaire.db.dao.UserSurveyInfoDao;
import com.fang.chinaindex.questionnaire.model.Logic;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UserInfo;
import com.fang.chinaindex.questionnaire.repository.CacheRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/25.
 */
public class CacheRepositoryImpl implements CacheRepository {
    private DaoSession daoSession;
    private SQLiteDatabase db;

    public CacheRepositoryImpl(DaoSession daoSession) {
        this.daoSession = daoSession;
        this.db = daoSession.getSQLiteDatabase();
    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {
        daoSession.getUserDao().replace(userInfo);
    }

    @Override
    public void saveSurveyInfos(String userId, List<SurveyInfo> surveyInfos) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        db.beginTransaction();
        try {
            surveyInfoDao.replace(surveyInfos);
            userSurveyInfoDao.save(userId, surveyInfos);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public List<SurveyInfo> getSurveyInfos(String userId) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        List<SurveyInfo> surveyInfos = null;
        db.beginTransaction();
        try {
            List<String> surveyIds = userSurveyInfoDao.getSurveyIdsByUserId(userId);
            surveyInfos = surveyInfoDao.getSurveyInfos(surveyIds);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return surveyInfos;
    }

    @Override
    public void saveSurveys(List<Survey> surveys) {
        QuestionDao questionDao = daoSession.getQuestionDao();
        OptionDao optionDao = daoSession.getOptionDao();
        LogicDao logicDao = daoSession.getLogicDao();

        db.beginTransaction();
        try {
            for (Survey survey : surveys) {
                SurveyInfo info = survey.getInfo();
                //保存questions 通过surveyId关联
                List<Question> questions = survey.getQuestions();
                questionDao.replace(info.getSurveyId(), questions);
                for (Question question : questions) {
                    //保存options 通过surveyId，和questionId关联
                    List<Option> options = question.getOptions();
                    if (options != null && options.size() > 0) {
                        optionDao.replace(info.getSurveyId(), question.getId(), options);
                    }
                    //保存logics 通过surveyId，和questionId关联
                    List<Logic> logics = question.getLogics();
                    if (logics != null && logics.size() > 0) {
                        logicDao.replace(info.getSurveyId(), logics);
                    }
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public Survey getSurvey(String surveyId) {

        return null;
    }

    @Override
    public List<Survey> getSurveys(String userId) {
        List<Survey> surveys = new ArrayList<Survey>();
        db.beginTransaction();
        try {
            Cursor cursor = db.rawQuery("Select * from Survey", null);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return null;
    }


}
