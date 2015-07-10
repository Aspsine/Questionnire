package com.fang.chinaindex.questionnaire.repository.impl;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.DaoSession;
import com.fang.chinaindex.questionnaire.db.dao.AnsweredOptionDao;
import com.fang.chinaindex.questionnaire.db.dao.AnsweredQuestionDao;
import com.fang.chinaindex.questionnaire.db.dao.AnsweredSurveyInfoDao;
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
import com.fang.chinaindex.questionnaire.model.UserSurveyInfo;
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
    public List<SurveyInfo> getAnsweredSurveyInfos(String userId, boolean finished) {
        AnsweredSurveyInfoDao answeredSurveyInfoDao = daoSession.getAnsweredSurveyInfoDao();
        List<SurveyInfo> surveyInfos = null;
        db.beginTransaction();
        try {
            surveyInfos = answeredSurveyInfoDao.getAnsweredSurveyInfos(userId, finished);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return surveyInfos;
    }

    @Override
    public List<String> getSurveyIds() {
        SurveyInfoDao surveyInfoDao = daoSession.getSurveyInfoDao();
        List<String> surveyIds = null;
        db.beginTransaction();
        try {
            surveyIds = surveyInfoDao.getSurveyIds();
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return surveyIds;
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
    public void linkUserAndSurveyInfo(String userId, String surveyId) {
        UserSurveyInfoDao userSurveyInfoDao = daoSession.getUserSurveyInfoDao();
        db.beginTransaction();
        try {
            userSurveyInfoDao.save(userId, surveyId);
            db.setTransactionSuccessful();
        }finally {
            db.endTransaction();
        }
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
                userSurveyInfoDao.save(userId, info.getSurveyId());
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
    public void saveAnsweredQuestion(String userId, String surveyId, String startTime, Question question) {
        AnsweredQuestionDao answeredQuestionDao = daoSession.getAnsweredQuestionDao();
        AnsweredOptionDao answeredOptionDao = daoSession.getAnsweredOptionDao();
        db.beginTransaction();
        try {
            answeredQuestionDao.save(userId, surveyId, startTime, question);
            answeredOptionDao.deleteOptions(userId, surveyId, question.getId(), startTime);
            answeredOptionDao.save(userId, surveyId, question.getId(), startTime, question.getOptions());
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public List<Question> getAnsweredQuestions(String userId, String surveyId, String startTime) {
        AnsweredQuestionDao answeredQuestionDao = daoSession.getAnsweredQuestionDao();
        AnsweredOptionDao optionDao = daoSession.getAnsweredOptionDao();
        List<Question> questions = null;
        db.beginTransaction();
        try {
            questions = answeredQuestionDao.getAnsweredQuestions(userId, surveyId, startTime);
            for (Question question : questions) {
                List<Option> options = optionDao.getAnsweredOptions(userId, surveyId, question.getId(), startTime);
                question.setOptions(options);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        return questions;
    }

    @Override
    public void deleteAnsweredQuestions(String userId, String surveyId, String startTime, List<String> questionIds) {
        //清理 currentPosition之后的所有数据库中保存的AnsweredQuestion和对应的AnsweredOption
        AnsweredQuestionDao answeredQuestionDao = daoSession.getAnsweredQuestionDao();
        AnsweredOptionDao answeredOptionDao = daoSession.getAnsweredOptionDao();
        db.beginTransaction();
        try {
            for (String questionId : questionIds) {
                answeredQuestionDao.deleteAnsweredQuestions(userId, surveyId, questionId, startTime);
                answeredOptionDao.deleteOptions(userId, surveyId, questionId, startTime);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void saveAnsweredSurvey(String userId, SurveyInfo surveyInfo) {
        AnsweredSurveyInfoDao answeredSurveyInfoDao = daoSession.getAnsweredSurveyInfoDao();
        db.beginTransaction();
        try {
            answeredSurveyInfoDao.save(userId, surveyInfo);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void deleteAnsweredSurvey(String userId, String surveyId, String startTime) {
        AnsweredSurveyInfoDao answeredSurveyInfoDao = daoSession.getAnsweredSurveyInfoDao();
        AnsweredQuestionDao answeredQuestionDao = daoSession.getAnsweredQuestionDao();
        AnsweredOptionDao answeredOptionDao = daoSession.getAnsweredOptionDao();
        db.beginTransaction();
        try {
            answeredSurveyInfoDao.delete(userId, surveyId, startTime);
            answeredQuestionDao.delete(userId, surveyId, startTime);
            answeredOptionDao.delete(userId, surveyId, startTime);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void deleteAnsweredSurveys(String userId, List<SurveyInfo> surveyInfos) {
        AnsweredSurveyInfoDao answeredSurveyInfoDao = daoSession.getAnsweredSurveyInfoDao();
        AnsweredQuestionDao answeredQuestionDao = daoSession.getAnsweredQuestionDao();
        AnsweredOptionDao answeredOptionDao = daoSession.getAnsweredOptionDao();
        db.beginTransaction();
        try {
            for (SurveyInfo info : surveyInfos) {
                answeredSurveyInfoDao.delete(userId, info.getSurveyId(), info.getStartTime());
                answeredQuestionDao.delete(userId, info.getSurveyId(), info.getStartTime());
                answeredOptionDao.delete(userId, info.getSurveyId(), info.getStartTime());
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}
