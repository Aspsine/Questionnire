package com.fang.chinaindex.questionnaire.repository;

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
import com.fang.chinaindex.questionnaire.util.DES;
import com.fang.chinaindex.questionnaire.util.L;
import com.fang.chinaindex.questionnaire.util.MD5;
import com.google.gson.Gson;

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
    private Gson mGson;
    private Context mContext;

    public RepositoryImpl(Context context) {
        this.mContext = context;
        this.mGson = new Gson();
    }

    @Override
    public void Login(String userName, String passWord, final Callback<Login> callback) {
        Map<String, String> params = new HashMap<>();
        try {
            String sUserName = DES.encryptDES(userName, Constants.CONFIG.ENCRYPT_KEY);
            String sPassWord = DES.encryptDES(passWord, Constants.CONFIG.ENCRYPT_KEY);
            params.put("username", sUserName);
            params.put("password", sPassWord);
            params.put("encrypt", MD5.md5(userName + passWord + Constants.CONFIG.ENCRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = createRequestUrl(Constants.URL.APP_CHECK_USER, params);
        L.i(TAG, url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String json) {
                callback.success(mGson.fromJson(json, Login.class));
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.failure(volleyError);
            }
        });
        App.getRequestQueue().add(request);
    }

    @Override
    public void getSurveyResults(String userId, final Callback<List<SurveyInfo>> callback) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            String sUserId = DES.encryptDES(userId, Constants.CONFIG.ENCRYPT_KEY);
            params.put("iUserId", sUserId);
            params.put("encrypt", MD5.md5(userId + Constants.CONFIG.ENCRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = createRequestUrl(Constants.URL.SURVEYS, params);
        Log.i(TAG, url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String json) {
                try {
                    SurveyResults result = mGson.fromJson(json, SurveyResults.class);
                    if (result != null && TextUtils.isEmpty(result.getErrorMessage())) {
                        callback.success(result.getSurveyInfos());
                    } else {
                        callback.failure(new Exception(result != null ? result.getErrorMessage() : "Server error"));
                    }
                } catch (Exception e) {
                    callback.failure(e);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.failure(volleyError);
            }
        });
        App.getRequestQueue().add(request);
    }

    @Override
    public void getSurveyDetails(String userId, String[] surveyIds, final Callback<List<Survey>> callback) {
        StringBuilder sb = new StringBuilder();
        for (String id : surveyIds) {
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        Map<String, String> params = new HashMap<String, String>();
        try {
            String sUserId = DES.encryptDES(userId, Constants.CONFIG.ENCRYPT_KEY);
            String sSurveyIDs = DES.encryptDES(sb.toString(), Constants.CONFIG.ENCRYPT_KEY);
            params.put("iUserId", sUserId);
            params.put("ids", sSurveyIDs);
            params.put("encrypt", MD5.md5(userId + sb.toString() + Constants.CONFIG.ENCRYPT_KEY));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String url = createRequestUrl(Constants.URL.SURVEYS_DETAIL, params);
        L.i(TAG, url);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String json) {
                try {
                    SurveyDetails details = mGson.fromJson(json, SurveyDetails.class);
                    if (details != null && TextUtils.isEmpty(details.getErrorMessage())) {
                        callback.success(details.getSurveys());
                    }else{
                        callback.failure(new Exception(details != null ? details.getErrorMessage() : "Sever error"));
                    }
                } catch (Exception e) {
                    callback.failure(e);
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.failure(volleyError);
            }
        });
        App.getRequestQueue().add(request);
    }

    @Override
    public void uploadSample(String userId, Survey survey, Callback<UploadSampleResult> callback) {

    }

    public String createRequestUrl(String url, Map<String, String> params) {
        StringBuilder sb = new StringBuilder(url);
        sb.append("?");
        try {
            Iterator iterator = params.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();
                sb.append(URLEncoder.encode((String) entry.getKey(), Constants.CONFIG.ENCODING));
                sb.append('=');
                sb.append(URLEncoder.encode((String) entry.getValue(), Constants.CONFIG.ENCODING));
                sb.append('&');
            }
            sb.deleteCharAt(sb.length() - 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
