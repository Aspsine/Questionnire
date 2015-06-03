package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Logic;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class LogicDao extends AbstractDao<Logic> {

    private static final String TABLE_NAME = "Logic";

    private static final String PARAMS = "logicId long, " +
            "surveyId long, " +
            "questionId long, " +
            "selectAnswer text, " +
            "logicQuestionId text, " +
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

    public void save(String surveyId, String questionId, List<Logic> logics) {
        updateIfFailsInsert(surveyId, questionId, logics);
    }

    public void updateIfFailsInsert(String surveyId, String questionId, List<Logic> logics) {
        ContentValues contentValues = new ContentValues();
        for (Logic logic : logics) {
            ContentValues values = getContentValues(surveyId, questionId, logic, contentValues, true);
            if (db.update(TABLE_NAME, values, "logicId = ? and surveyId = ? and questionId = ?",
                    new String[]{logic.getId(), surveyId, questionId}) == 0) {
                db.insert(TABLE_NAME, null, values);
            }
            contentValues.clear();
        }
    }

//    public void insert(String surveyId, List<Logic> logics) {
//        ContentValues contentValues = new ContentValues();
//        for (Logic logic : logics) {
//            db.insert(TABLE_NAME, null, getContentValues(surveyId, logic, contentValues, true));
//            contentValues.clear();
//        }
//    }
//
//    public void delete(String surveyId) {
//        db.delete(TABLE_NAME, "surveyId =?", new String[]{surveyId});
//    }

//    public void replace(String surveyId, List<Logic> logics) {
//        ContentValues contentValues = new ContentValues();
//        for (Logic logic : logics) {
//            db.replace(TABLE_NAME, null, getContentValues(surveyId, logic, contentValues, true));
//            contentValues.clear();
//        }
//    }

    public List<Logic> getLogics(String questionId) {
        List<Logic> logics = new ArrayList<Logic>();
        Cursor cursor = db.rawQuery("select * from Logic where questionId = ?", new String[]{questionId});
        try {
            while (cursor.moveToNext()) {
                Logic logic = new Logic();
                logic.setId(cursor.getString(cursor.getColumnIndex("logicId")));
                logic.setLogicQuestionId(cursor.getString(cursor.getColumnIndex("logicQuestionId")));
                logic.setSelectAnswer(cursor.getString(cursor.getColumnIndex("selectAnswer")));
                logic.setSkipFrom(cursor.getString(cursor.getColumnIndex("skipFrom")));
                logic.setSkipTo(cursor.getString(cursor.getColumnIndex("skipTo")));
                logic.setLogicType(cursor.getString(cursor.getColumnIndex("logicType")));
                logics.add(logic);
            }
        } finally {
            cursor.close();
        }
        return logics;
    }

    public ContentValues getContentValues(String surveyId, String questionId, Logic logic, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyId);
        values.put("questionId", questionId);
        values.put("logicId", logic.getId());
        values.put("logicQuestionId", logic.getLogicQuestionId());
        values.put("selectAnswer", logic.getSelectAnswer());
        values.put("skipFrom", logic.getSkipFrom());
        values.put("skipTo", logic.getSkipTo());
        values.put("logicType", logic.getLogicType());
        return values;
    }


}
