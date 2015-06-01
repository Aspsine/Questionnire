package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class QuestionDao extends AbstractDao<Question> {

    private static final String TABLE_NAME = "Question";

    private static final String PARAMS = "questionId long, " +
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
            "description text, " +
            "FOREIGN KEY(surveyId) REFERENCES SurveyInfo(surveyId)";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public QuestionDao(SQLiteDatabase db) {
        super(db);
    }

    public void replace(String surveyId, List<Question> questions) {
        ContentValues contentValues = new ContentValues();
        for (Question question : questions) {
            db.replace(TABLE_NAME, null, getContentValue(surveyId, question, contentValues, true));
            contentValues.clear();
        }
    }

    public ContentValues getContentValue(String surveyId, Question question, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyId);
        values.put("questionId", question.getId());
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
        return values;
    }


}
