package com.fang.chinaindex.questionnaire.db.dao;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class AnsweredQuestionDao extends AbstractDao<Question> {

    private static final String TABLE_NAME = "AnsweredQuestion";
    private static final String PARAMS = "questionId long, " +
            "surveyId long, " +
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

    public static final void dropTable(SQLiteDatabase db){
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public AnsweredQuestionDao(SQLiteDatabase db) {
        super(db);
    }


}
