package com.fang.chinaindex.questionnaire.db.dao;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.fang.chinaindex.questionnaire.db.AbstractDao;
import com.fang.chinaindex.questionnaire.model.UserInfo;
import com.fang.chinaindex.questionnaire.util.SQLUtils;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class UserDao extends AbstractDao<UserInfo> {

    private static final String TABLE_NAME = "User";

    private static final String PARAMS =
            "userId long, " +
            "userName text, " +
            "permissionEndTime text, " +
            "realName text, " +
            "email text";

    public static final void createTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.createTable(TABLE_NAME, PARAMS));
    }

    public static final void dropTable(SQLiteDatabase db) {
        db.execSQL(SQLUtils.dropTable(TABLE_NAME));
    }

    public UserDao(SQLiteDatabase db) {
        super(db);
    }

    public void replace(UserInfo userInfo) {
        ContentValues values = new ContentValues();
        values.put("userId", userInfo.getUserId());
        values.put("userName", userInfo.getUserName());
        values.put("permissionEndTime", userInfo.getPermissionEndTime());
        values.put("realName", userInfo.getRealName());
        values.put("email", userInfo.getEmail());
        db.replace(TABLE_NAME, null, values);
    }


//    public synchronized void save(UserInfo userInfo) {
//        if (exist(String.valueOf(userInfo.getUserId()))) {
//            updata(userInfo);
//        } else {
//            insert(userInfo);
//        }
//    }
//
//    public synchronized void insert(UserInfo userInfo) {
//        ContentValues values = new ContentValues();
//        values.put("userId", userInfo.getUserId());
//        values.put("userName", userInfo.getUserName());
//        values.put("permissionEndTime", userInfo.getPermissionEndTime());
//        values.put("realName", userInfo.getRealName());
//        values.put("email", userInfo.getEmail());
//        db.insert(TABLE_NAME, null, values);
//    }
//
//    public synchronized void delete(String userId) {
//        db.delete(TABLE_NAME, "userId = ?", new String[]{userId});
//    }
//
//    public void updata(UserInfo userInfo) {
//        ContentValues values = new ContentValues();
//        values.put("userName", userInfo.getUserName());
//        values.put("permissionEndTime", userInfo.getPermissionEndTime());
//        values.put("realName", userInfo.getRealName());
//        values.put("email", userInfo.getEmail());
//        db.update(TABLE_NAME, values, "userId=?", new String[]{String.valueOf(userInfo.getUserId())});
//    }
//
//    public UserInfo getUser(String userId) {
//        Cursor cursor = db.rawQuery("Select * from " + TABLE_NAME + " where userId = ?", new String[]{userId});
//        List<UserInfo> userInfos = new ArrayList<UserInfo>();
//        while (cursor.moveToNext()) {
//            UserInfo userInfo = new UserInfo();
//            userInfo.setUserId(cursor.getString(cursor.getColumnIndex("userId")));
//            userInfo.setUserName(cursor.getString(cursor.getColumnIndex("userName")));
//            userInfo.setRealName(cursor.getString(cursor.getColumnIndex("realName")));
//            userInfo.setEmail(cursor.getString(cursor.getColumnIndex("email")));
//            userInfo.setPermissionEndTime(cursor.getString(cursor.getColumnIndex("permissionEndTime")));
//            userInfos.add(userInfo);
//        }
//        return userInfos.size() > 0 ? userInfos.get(0) : null;
//    }
//
//    public boolean exist(String userId) {
//        return getUser(userId) == null ? false : true;
//    }

}
