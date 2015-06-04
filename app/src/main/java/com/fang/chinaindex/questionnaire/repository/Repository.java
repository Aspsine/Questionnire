package com.fang.chinaindex.questionnaire.repository;

import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;

import java.util.List;

/**
 * Created by aspsine on 15-5-9.
 */
public interface Repository {

    public void Login(String userName, String passWord, Callback<Login> callback);

    public void getSurveyResults(String userId, Callback<List<SurveyInfo>> callback);

    public void getSurveyDetails(String userId, List<String> surveyIds, Callback<List<Survey>> callback);

    public void uploadSample(String userId, Survey survey, Callback<UploadSampleResult> callback);

    public interface Callback<T> {
        public void success(T t);

        public void failure(Exception e);
    }
}
