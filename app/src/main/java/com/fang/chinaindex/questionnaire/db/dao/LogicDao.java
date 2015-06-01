package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Logic;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class LogicDao extends AbstractDao<Logic> {

    private static final String TABLE_NAME = "Logic";

    private static final String PARAMS =
            "logicId long, " +
            "surveyId long, " +
            "questionId long, " +
            "selectAnswer text, " +
            "skipFrom text, " +
            "skipTo text, " +
            "logicType text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public LogicDao(SQLiteDatabase db) {
        super(db);
    }

    public void replace(String surveyId, List<Logic> logics) {
        ContentValues contentValues = new ContentValues();
        for (Logic logic : logics) {
            db.replace(TABLE_NAME, null, getContentValues(surveyId, logic, contentValues, true));
            contentValues.clear();
        }
    }

    public ContentValues getContentValues(String surveyId, Logic logic, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyId);
        values.put("questionId", logic.getQuestionId());
        values.put("logicId", logic.getId());
        values.put("selectAnswer", logic.getSelectAnswer());
        values.put("skipFrom", logic.getSkipFrom());
        values.put("skipTo", logic.getSkipTo());
        values.put("logicType", logic.getLogicType());
        return values;
    }
}
