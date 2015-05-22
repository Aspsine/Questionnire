package com.fang.chinaindex.questionnaire.db;

import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.util.SQLUtils;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class UserDao {

    private static final String TABLE_NAME = "USER";

    private static final String PARAMS = "userId long, userName text, permissionEndTime text, realName text, email text";

    private static UserDao sInstance;

    private DBOpenHelper mHelper;

    public static final void createTable(SQLiteDatabase db){
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db){
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public static final UserDao getInstance(){
        if(sInstance == null){
            sInstance = new UserDao();
        }
        return sInstance;
    }

    private UserDao(){
        mHelper = DBOpenHelper.getInstance();
    }



}
