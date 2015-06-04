package com.fang.chinaindex.questionnaire.repository;

import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/25.
 */
public interface NetRepository extends Repository {
    @Override
    void Login(String userName, String passWord, Callback<Login> callback);

    @Override
    void getSurveyResults(String userId, Callback<List<SurveyInfo>> callback);

    @Override
    void getSurveyDetails(String userId, List<String> surveyIds, Callback<List<Survey>> callback);

    @Override
    void uploadSample(String userId, Survey survey, Callback<UploadSampleResult> callback);
}
