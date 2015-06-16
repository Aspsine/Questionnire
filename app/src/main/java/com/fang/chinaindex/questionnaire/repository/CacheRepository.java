package com.fang.chinaindex.questionnaire.repository;

import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UserInfo;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/25.
 */
public interface CacheRepository {

    public void saveUserInfo(UserInfo userInfo);

    public void saveSurveyInfos(String userId, List<SurveyInfo> surveyInfos);

    public List<SurveyInfo> getSurveyInfos(String userId);

    public List<String> getSurveyIds(String userId);

    public void saveSurveys(String userId, List<Survey> surveys);

    public Survey getSurvey(String surveyId);

    public List<Survey> getSurveys(String userId);

    public void saveAnsweredQuestion(String userId, String surveyId, String startTime, Question question);

    public List<Question> getAnsweredQuestions(String userId, String surveyId, String startTime);

    public void deleteAnsweredQuestions(String userId, String surveyId, String startTime, List<String> questionIds);

    public void saveAnsweredSurvey(String userId, SurveyInfo surveyInfo);

    public void deleteAnsweredSurvey(String userId, String surveyId, String startTime);
}
