package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class OptionDao extends AbstractDao<Option> {

    private static final String TABLE_NAME = "Option";

    private static final String PARAMS = "optionId long, " +
            "surveyId long, " +
            "questionId long, " +
            "optionTitle text, " +
            "sort text, " +
            "isOther text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public OptionDao(SQLiteDatabase db) {
        super(db);
    }

    public void save(String surveyId, String questionId, List<Option> options) {
        updateIfFailsInsert(surveyId, questionId, options);
    }

    public void updateIfFailsInsert(String surveyId, String questionId, List<Option> options) {
        ContentValues contentValues = new ContentValues();
        for (Option option : options) {
            ContentValues values = getContentValues(surveyId, questionId, option, contentValues, true);
            if (db.update(TABLE_NAME, values,
                    "surveyId = ? and questionId = ? and optionId = ?",
                    new String[]{surveyId, questionId, option.getId()}) == 0) {
                db.insert(TABLE_NAME, null, values);
            }
            contentValues.clear();
        }
    }

//    public void insert(String surveyId, String questionId, List<Option> options) {
//        ContentValues contentValues = new ContentValues();
//        for (Option option : options) {
//            db.insert(TABLE_NAME, null, getContentValues(surveyId, questionId, option, contentValues, true));
//            contentValues.clear();
//        }
//    }
//
//    public void delete(String surveyId) {
//        db.delete(TABLE_NAME, "surveyId = ?", new String[]{surveyId});
//    }

//    public void replace(String surveyId, String questionId, List<Option> options) {
//        ContentValues contentValues = new ContentValues();
//        for (Option option : options) {
//            db.replace(TABLE_NAME, null, getContentValues(surveyId, questionId, option, contentValues, true));
//            contentValues.clear();
//        }
//    }

    public List<Option> getOptions(String questionId) {
        List<Option> options = new ArrayList<Option>();
        Cursor cursor = db.rawQuery("select * from Option where questionId = ?", new String[]{questionId});
        try {
            while (cursor.moveToNext()) {
                Option option = new Option();
                option.setId(cursor.getString(cursor.getColumnIndex("optionId")));
                option.setOptionTitle(cursor.getString(cursor.getColumnIndex("optionTitle")));
                option.setSort(cursor.getString(cursor.getColumnIndex("sort")));
                option.setIsOther(Boolean.valueOf(cursor.getString(cursor.getColumnIndex("isOther"))));
                options.add(option);
            }
        } finally {
            cursor.close();
        }
        return options;
    }

    public ContentValues getContentValues(String surveyId, String questionId, Option option, ContentValues contentValues, boolean useCache) {
        ContentValues values = useCache ? contentValues : new ContentValues();
        values.put("surveyId", surveyId);
        values.put("questionId", questionId);
        values.put("optionId", option.getId());
        values.put("optionTitle", option.getOptionTitle());
        values.put("sort", option.getSort());
        values.put("isOther", option.isOther());
        return values;
    }


}
