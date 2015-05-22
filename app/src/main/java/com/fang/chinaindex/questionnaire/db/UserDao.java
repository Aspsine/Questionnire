package com.fang.chinaindex.questionnaire.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.model.UserInfo;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class UserDao {

    private static final String TABLE_NAME = "USER";

    private static final String PARAMS = "userId long, userName text, permissionEndTime text, realName text, email text";

    private static UserDao sInstance;

    private DBOpenHelper mHelper;

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public static final UserDao getInstance() {
        if (sInstance == null) {
            sInstance = new UserDao();
        }
        return sInstance;
    }

    private UserDao() {
        mHelper = DBOpenHelper.getInstance();
    }

    public synchronized void save(UserInfo userInfo) {
        if (exist(userInfo.getUserId())) {
            updata(userInfo);
        } else {
            insert(userInfo);
        }
    }

    public synchronized void insert(UserInfo userInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userId", userInfo.getUserId());
        values.put("userName", userInfo.getUserName());
        values.put("permissionEndTime", userInfo.getPermissionEndTime());
        values.put("realName", userInfo.getRealName());
        values.put("email", userInfo.getEmail());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public synchronized void delete(long userId) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TABLE_NAME, "userId = ?", new String[]{String.valueOf(userId)});
        db.close();
    }

    public void updata(UserInfo userInfo) {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", userInfo.getUserName());
        values.put("permissionEndTime", userInfo.getPermissionEndTime());
        values.put("realName", userInfo.getRealName());
        values.put("email", userInfo.getEmail());
        db.update(TABLE_NAME, values, "userId=?", new String[]{String.valueOf(userInfo.getUserId())});
        db.close();
    }

    public UserInfo getUser(long userId) {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId = ?", new String[]{String.valueOf(userId)});
        List<UserInfo> userInfos = new ArrayList<UserInfo>();
        while (cursor.moveToNext()) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(cursor.getLong(cursor.getColumnIndex("userId")));
            userInfo.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
            userInfo.setRealName(cursor.getString(cursor.getColumnIndex("realName")));
            userInfo.setEmail(cursor.getString(cursor.getColumnIndex("email")));
            userInfo.setPermissionEndTime(cursor.getString(cursor.getColumnIndex("permissionEndTime")));
            userInfos.add(userInfo);
        }
        db.close();
        return userInfos.size() > 0 ? userInfos.get(0) : null;
    }

    public boolean exist(long userId) {
        return getUser(userId) == null ? false : true;
    }

}
