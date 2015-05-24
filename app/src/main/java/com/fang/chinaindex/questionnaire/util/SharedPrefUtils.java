package com.fang.chinaindex.questionnaire.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;

import com.fang.chinaindex.questionnaire.model.UserInfo;


/**
 * Created by aspsine on 15-4-28.
 */
public class SharedPrefUtils {
    /**
     * private access forbidden 'new'
     */
    private SharedPrefUtils() {
    }

    /**
     * Boolean indicating whether user learned the drawer
     * <p/>
     * Per the design guidelines, you should show the drawer on launch until the user manually
     * expands it. This shared preference tracks this.
     */
    public static final String SHARED_PREF_IS_USER_LEARNED_DRAWER = "shared_pref_is_user_learned_drawer";

    /**
     * sharedPref key of userId
     */
    private static final String SHARED_PREF_USER_ID = "shared_pref_user_id";
    /**
     * sharedPref key of userName
     */
    private static final String SHARED_PREF_USER_NAME = "shared_pref_user_name";
    /**
     * sharedPref key of PermissionEndTime
     */
    private static final String SHARED_PREF_USER_PERMISSION_END_TIME = "shared_pref_user_permission_end_time";
    /**
     * sharedPref key of UserRealName
     */
    private static final String SHARED_PREF_USER_REAL_NAME = "shared_pref_user_real_name";

    /**
     * sharedPref key of Email
     */
    private static final String SHARED_PREF_USER_EMAIL = "shared_pref_user_email";

    public static boolean isUserLearnedDrawer(final Context context) {
        SharedPreferences sp = getDefaultSharedPreferences(context);
        return sp.getBoolean(SHARED_PREF_IS_USER_LEARNED_DRAWER, false);
    }

    public static void markUserLearnedDrawer(Context context) {
        SharedPreferences sp = getDefaultSharedPreferences(context);
        sp.edit().putBoolean(SHARED_PREF_IS_USER_LEARNED_DRAWER, true).commit();
    }

    public static void saveUserInfo(Context c, UserInfo userInfo) {
        SharedPreferences sp = getDefaultSharedPreferences(c);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(SHARED_PREF_USER_ID, userInfo.getUserId());
        editor.putString(SHARED_PREF_USER_NAME, userInfo.getUserName());
        editor.putString(SHARED_PREF_USER_PERMISSION_END_TIME, userInfo.getPermissionEndTime());
        editor.putString(SHARED_PREF_USER_REAL_NAME, userInfo.getRealName());
        editor.putString(SHARED_PREF_USER_EMAIL, userInfo.getEmail());
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO) {
            editor.apply();
        } else {
            editor.commit();
        }
    }

    public static void clearUserInfo(Context c) {
        SharedPreferences.Editor editor = getDefaultSharedPreferences(c).edit().clear();
        editor.commit();
    }

    public static String getUserId(Context c) {
        return getDefaultSharedPreferences(c).getString(SHARED_PREF_USER_ID, "");
    }

    public static String getUserName(Context c) {
        return getDefaultSharedPreferences(c).getString(SHARED_PREF_USER_NAME, "");
    }

    public static String getUserPermissionEndTime(Context c) {
        return getDefaultSharedPreferences(c).getString(SHARED_PREF_USER_PERMISSION_END_TIME, "");
    }

    public static String getUserRealName(Context c) {
        return getDefaultSharedPreferences(c).getString(SHARED_PREF_USER_REAL_NAME, "");
    }

    public static String getUserEmail(Context c) {
        return getDefaultSharedPreferences(c).getString(SHARED_PREF_USER_EMAIL, "");
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
