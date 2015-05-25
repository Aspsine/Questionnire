package com.fang.chinaindex.questionnaire.repository.impl;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.Constants;
import com.fang.chinaindex.questionnaire.model.Login;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyDetails;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.SurveyResults;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;
import com.fang.chinaindex.questionnaire.repository.CacheRepository;
import com.fang.chinaindex.questionnaire.repository.NetRepository;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.util.DES;
import com.fang.chinaindex.questionnaire.util.L;
import com.fang.chinaindex.questionnaire.util.MD5;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by aspsine on 15-5-9.
 */
public class RepositoryImpl implements Repository {
    private static final String TAG = RepositoryImpl.class.getSimpleName();
    private CacheRepository mCache;
    private NetRepository mNet;
    private Context mContext;

    public RepositoryImpl(Context context) {
        this.mContext = context;
    }

    @Override
    public void Login(String userName, String passWord, final Callback<Login> callback) {
        mNet.Login(userName, passWord, new Callback<Login>() {
            @Override
            public void success(Login login) {
                if (TextUtils.isEmpty(login.getErrorMessage())){
                    callback.success(login);
                    mCache.saveUserInfo(login.getUserInfo());
                }else {
                    failure(new Exception(login.getErrorMessage()));
                }
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
                mCache.saveSurveyInfos(surveyInfos);
            }

            @Override
            public void failure(final Exception error) {
                mCache.getSurveyResults(userId, new Callback<List<SurveyInfo>>() {
                    @Override
                    public void success(List<SurveyInfo> surveyInfos) {
                        callback.success(surveyInfos);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void getSurveyDetails(final String userId, final String[] surveyIds, final Callback<List<Survey>> callback) {
        mNet.getSurveyDetails(userId, surveyIds, new Callback<List<Survey>>() {
            @Override
            public void success(List<Survey> surveys) {
                callback.success(surveys);
            }

            @Override
            public void failure(final Exception error) {
                mCache.getSurveyDetails(userId, surveyIds, new Callback<List<Survey>>(){

                    @Override
                    public void success(List<Survey> surveys) {
                        callback.success(surveys);
                    }

                    @Override
                    public void failure(Exception e) {
                        callback.failure(error);
                    }
                });
            }
        });
    }

    @Override
    public void uploadSample(String userId, Survey survey, Callback<UploadSampleResult> callback) {

    }


}
