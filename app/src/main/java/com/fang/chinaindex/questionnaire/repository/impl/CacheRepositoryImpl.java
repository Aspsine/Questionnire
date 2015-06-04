package com.fang.chinaindex.questionnaire.repository.impl;

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
        daoSession.getUserDao().save(userInfo);
    }

    @Override
    public void saveSurveyInfos(String userId, List<SurveyInfo> surveyInfos) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        db.beginTransaction();
        try {
            surveyInfoDao.save(surveyInfos);
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
    public List<String> getSurveyIds(String userId) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        List<String> surveyIds = null;
        db.beginTransaction();
        try {
            surveyIds = userSurveyInfoDao.getSurveyIdsByUserId(userId);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return surveyIds;
    }

    @Override
    public void saveSurveys(String userId, List<Survey> surveys) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        QuestionDao questionDao = daoSession.getQuestionDao();
        OptionDao optionDao = daoSession.getOptionDao();
        LogicDao logicDao = daoSession.getLogicDao();

        db.beginTransaction();
        try {
            for (Survey survey : surveys) {
                SurveyInfo info = survey.getInfo();
                //关联表
                userSurveyInfoDao.save(userId, info);
                //保存surveyInfo
                surveyInfoDao.save(info);
                //保存questions 通过surveyId关联
                List<Question> questions = survey.getQuestions();
                questionDao.save(info.getSurveyId(), questions);
                for (Question question : questions) {
                    //保存options 通过surveyId，和questionId关联
                    List<Option> options = question.getOptions();
                    if (options != null && options.size() > 0) {
                        optionDao.save(info.getSurveyId(), question.getId(), options);
                    }
                    //保存logics 通过surveyId，和questionId关联
                    List<Logic> logics = question.getLogics();
                    if (logics != null && logics.size() > 0) {
                        logicDao.save(info.getSurveyId(), question.getId(), logics);
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
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        QuestionDao questionDao = daoSession.getQuestionDao();
        OptionDao optionDao = daoSession.getOptionDao();
        LogicDao logicDao = daoSession.getLogicDao();
        Survey survey = new Survey();
        db.beginTransaction();
        try {
            SurveyInfo surveyInfo = surveyInfoDao.getSurveyInfo(surveyId);
            survey.setInfo(surveyInfo);
            List<Question> questions = questionDao.getQuestions(surveyId);
            for (Question question : questions) {
                List<Option> options = optionDao.getOptions(question.getId());
                List<Logic> logics = logicDao.getLogics(question.getId());
                question.setOptions(options);
                question.setLogics(logics);
            }
            survey.setQuestions(questions);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return survey;
    }

    @Override
    public List<Survey> getSurveys(String userId) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        QuestionDao questionDao = daoSession.getQuestionDao();
        OptionDao optionDao = daoSession.getOptionDao();
        LogicDao logicDao = daoSession.getLogicDao();
        List<Survey> surveys = new ArrayList<Survey>();
        List<SurveyInfo> surveyInfos = null;
        db.beginTransaction();
        try {
            List<String> surveyIds = userSurveyInfoDao.getSurveyIdsByUserId(userId);
            surveyInfos = surveyInfoDao.getSurveyInfos(surveyIds);
            for (SurveyInfo surveyInfo : surveyInfos) {
                Survey survey = new Survey();
                survey.setInfo(surveyInfo);
                List<Question> questions = questionDao.getQuestions(surveyInfo.getSurveyId());
                for (Question question : questions) {
                    List<Option> options = optionDao.getOptions(question.getId());
                    List<Logic> logics = logicDao.getLogics(question.getId());
                    question.setOptions(options);
                    question.setLogics(logics);
                }
                survey.setQuestions(questions);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return surveys;
    }


}
