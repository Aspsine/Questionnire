package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Logic;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class LogicDao extends AbstractDao<Logic> {

    private static final String TABLE_NAME = "Logic";

    private static final String PARAMS = "_id integer autoincrement, " +
            "logicId long primary key, " +
            "surveyId long, " +
            "foreQuestionId long " +
            "questionId long " +
            "";

    public static final void createTable(SQLiteDatabase db){

    }

    public static final void dropTable(SQLiteDatabase db){

    }

    public LogicDao(SQLiteDatabase db) {
        super(db);
    }

    public void replace(String surveyId, String questionId, List<Logic> logics) {
        ContentValues contentValues = new ContentValues();
        for (Logic logic : logics) {
            ContentValues values = getContentValues(surveyId, questionId, logic, contentValues, true);
            db.replace(TABLE_NAME, null, contentValues);
            contentValues.clear();
        }
    }

    public ContentValues getContentValues(String surveyId, String questionId, Logic logic, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyId);
        values.put("questionId", questionId);

        return values;
    }
}
