package com.fang.chinaindex.questionnaire.repository;

import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;
import com.fang.chinaindex.questionnaire.model.UserInfo;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/25.
 */
public interface CacheRepository extends Repository {
    @Override
    void Login(String userName, String passWord, Callback<Login> callback);

    @Override
    void getSurveyResults(String userId, Callback<List<SurveyInfo>> callback);

    @Override
    void getSurveyDetails(String userId, String[] surveyIds, Callback<List<Survey>> callback);

    void getSurveyDetail(String userId, String surveyId, String time, Callback<Survey> callback);

    void saveUserInfo(UserInfo userInfo);

    void saveSurveyInfos(List<SurveyInfo> surveyInfos);

    void saveSurveys(List<Survey> surveys);

    void saveSurvey(Survey survey);
}
