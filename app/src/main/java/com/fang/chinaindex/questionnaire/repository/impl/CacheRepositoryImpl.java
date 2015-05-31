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


    public void saveUserInfo(UserInfo userInfo) {
        daoSession.getUserDao().replace(userInfo);
    }

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
                        logicDao.replace(info.getSurveyId(), question.getId(), logics);
                    }
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


}
