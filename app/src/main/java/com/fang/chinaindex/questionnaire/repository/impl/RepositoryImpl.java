package com.fang.chinaindex.questionnaire.repository.impl;

import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;
import com.fang.chinaindex.questionnaire.repository.NetRepository;
import com.fang.chinaindex.questionnaire.repository.Repository;

import java.util.List;

/**
 * Created by aspsine on 15-5-9.
 */
public class RepositoryImpl implements Repository {
    private static final String TAG = RepositoryImpl.class.getSimpleName();
    private NetRepository mNet;

    public RepositoryImpl() {
        this.mNet = new NetRepositoryImpl();
    }

    @Override
    public void Login(String userName, String passWord, final Callback<Login> callback) {
        mNet.Login(userName, passWord, new Callback<Login>() {
            @Override
            public void success(Login login) {
                callback.success(login);
            }

            @Override
            public void failure(Exception e) {
                callback.failure(e);
            }
        });
    }

    @Override
    public void getSurveyResults(final String userId, final Callback<List<SurveyInfo>> callback) {

        mNet.getSurveyResults(userId, new Callback<List<SurveyInfo>>() {
            @Override
            public void success(List<SurveyInfo> surveyInfos) {
                callback.success(surveyInfos);
            }

            @Override
            public void failure(final Exception error) {
                callback.failure(error);
            }
        });
    }

    @Override
    public void getSurveyDetails(final String userId, final List<String> surveyIds, final Callback<List<Survey>> callback) {
        mNet.getSurveyDetails(userId, surveyIds, new Callback<List<Survey>>() {
            @Override
            public void success(List<Survey> surveys) {
                callback.success(surveys);
            }

            @Override
            public void failure(final Exception error) {
                callback.failure(error);
            }
        });
    }

    @Override
    public void uploadSample(String userId, Survey survey, final Callback<UploadSampleResult> callback) {
        mNet.uploadSample(userId, survey, new Callback<UploadSampleResult>() {
            @Override
            public void success(UploadSampleResult uploadSampleResult) {
                callback.success(uploadSampleResult);
            }

            @Override
            public void failure(Exception e) {
                callback.failure(e);
            }
        });
    }


}
