package com.fang.chinaindex.questionnaire.repository;

import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/25.
 */
public interface NetRepository {

    void Login(String userName, String passWord, Callback<Login> callback);

    void getSurveyResults(String userId, Callback<List<SurveyInfo>> callback);

    void getSurveyDetails(String userId, List<String> surveyIds, Callback<List<Survey>> callback);

    void uploadSample(String userId, Survey survey, Callback<UploadSampleResult> callback);

    public interface Callback<T> {
        public void success(T t);

        public void failure(Exception e);
    }
}
