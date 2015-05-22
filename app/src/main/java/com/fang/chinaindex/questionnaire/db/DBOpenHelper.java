package com.fang.chinaindex.questionnaire.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.fang.chinaindex.questionnaire.Constants;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class DBOpenHelper extends SQLiteOpenHelper {
    public static DBOpenHelper sInstance;

    public static final void init(Context context) {
        if (context != null) {
            sInstance = new DBOpenHelper(context.getApplicationContext());
        }
    }

    public static final DBOpenHelper getInstance() {
        if(sInstance != null){
            return sInstance;
        }else {
            throw new IllegalStateException("you must call init() method in your application");
        }

    }

    private DBOpenHelper(Context context) {
        super(context, Constants.DB.DATA_BASE_NAME, null, Constants.DB.DATA_BASE_VERSION);
    }

    void createTable(SQLiteDatabase db) {
        UserDao.createTable(db);
        SurveyDao.createTable(db);
        SurveyInfoDao.createTable(db);
        QuestionDao.createTable(db);
        OptionDao.createTable(db);
        LogicDao.createTable(db);
    }

    void dropTable(SQLiteDatabase db) {
        UserDao.dropTable(db);
        SurveyDao.dropTable(db);
        SurveyInfoDao.dropTable(db);
        QuestionDao.dropTable(db);
        OptionDao.dropTable(db);
        LogicDao.dropTable(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        dropTable(db);
        onCreate(db);
    }
}
