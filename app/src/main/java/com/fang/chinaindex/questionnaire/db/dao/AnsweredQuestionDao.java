package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class AnsweredQuestionDao extends AbstractDao<Question> {

    private static final String TABLE_NAME = "AnsweredQuestion";
    private static final String PARAMS = "questionId long, " +
            "userId long, " +
            "surveyId long, " +
            "startTime text, " +
            "qNum text, " +
            "showId text, " +
            "questionTitle text, " +
            "isMust text, " +
            "sort text, " +
            "score text, " +
            "category text, " +
            "categoryText text, " +
            "templateId text, " +
            "answerNumber text, " +
            "description text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public AnsweredQuestionDao(SQLiteDatabase db) {
        super(db);
    }

    public void save(String userId, String surveyId, String startTime, Question question) {
        updateIfFailsInsert(userId, surveyId, startTime, question);
    }

    private void updateIfFailsInsert(String userId, String surveyId, String startTime, Question question) {
        ContentValues values = new ContentValues();
        values.put("userId", userId);
        values.put("surveyId", surveyId); // surveyId
        values.put("questionId", question.getId());
        values.put("startTime", startTime); // startTime
        values.put("qNum", question.getqNum());
        values.put("showId", question.getShowId());
        values.put("questionTitle", question.getQuestionTitle());
        values.put("isMust", question.getIsMust());
        values.put("sort", question.getSort());
        values.put("score", question.getScore());
        values.put("category", question.getCategory());
        values.put("categoryText", question.getCategoryText());
        values.put("templateId", question.getTemplateId());
        values.put("answerNumber", question.getAnswerNumber());
        values.put("description", question.getDescription());
        if (db.update(TABLE_NAME, values, "questionId=? and surveyId=? and startTime=?", new String[]{question.getId(), surveyId, startTime}) == 0) {
            db.insert(TABLE_NAME, null, values);
        }
    }

    public List<Question> getAnsweredQuestions(String userId, String surveyId, String startTime) {
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId = ? and surveyId=? and startTime=?", new String[]{userId, surveyId, startTime});
        List<Question> questions = new ArrayList<Question>();
        try {
            while (cursor.moveToNext()) {
                Question question = new Question();
                question.setId(cursor.getString(cursor.getColumnIndex("questionId")));
                question.setqNum(cursor.getString(cursor.getColumnIndex("qNum")));
                question.setShowId(cursor.getString(cursor.getColumnIndex("showId")));
                question.setQuestionTitle(cursor.getString(cursor.getColumnIndex("questionTitle")));
                question.setIsMust(cursor.getString(cursor.getColumnIndex("isMust")));
                question.setSort(cursor.getString(cursor.getColumnIndex("sort")));
                question.setScore(cursor.getString(cursor.getColumnIndex("score")));
                question.setCategory(cursor.getString(cursor.getColumnIndex("category")));
                question.setCategoryText(cursor.getString(cursor.getColumnIndex("categoryText")));
                question.setTemplateId(cursor.getString(cursor.getColumnIndex("templateId")));
                question.setAnswerNumber(cursor.getString(cursor.getColumnIndex("answerNumber")));
                question.setDescription(cursor.getString(cursor.getColumnIndex("description")));
                questions.add(question);
            }
        } finally {
            cursor.close();
        }
        return questions;
    }
}
