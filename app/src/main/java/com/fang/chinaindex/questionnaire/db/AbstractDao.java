package com.fang.chinaindex.questionnaire.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aspsine on 2015/5/26.
 */
public class AbstractDao<T> {
    protected final SQLiteDatabase db;

    public AbstractDao(SQLiteDatabase db) {
        this.db = db;
    }


}
