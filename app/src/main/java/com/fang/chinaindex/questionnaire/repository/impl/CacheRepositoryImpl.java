package com.fang.chinaindex.questionnaire.repository.impl;

import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;
import com.fang.chinaindex.questionnaire.model.UserInfo;
import com.fang.chinaindex.questionnaire.repository.CacheRepository;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/25.
 */
public class CacheRepositoryImpl implements CacheRepository {



    @Override
    public void Login(String userName, String passWord, Callback<Login> callback) {

    }

    @Override
    public void getSurveyResults(String userId, Callback<List<SurveyInfo>> callback) {

    }

    @Override
    public void getSurveyDetails(String userId, String[] surveyIds, Callback<List<Survey>> callback) {

    }

    @Override
    public void getSurveyDetail(String userId, String surveyId, String time, Callback<Survey> callback) {

    }

    @Override
    public void uploadSample(String userId, Survey survey, Callback<UploadSampleResult> callback) {

    }

    @Override
    public void saveUserInfo(UserInfo userInfo) {

    }

    @Override
    public void saveSurveyInfos(List<SurveyInfo> surveyInfos) {

    }

    @Override
    public void saveSurveys(List<Survey> surveys) {

    }

    @Override
    public void saveSurvey(Survey survey) {

    }
}
