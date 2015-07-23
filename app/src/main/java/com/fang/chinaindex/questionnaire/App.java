package com.fang.chinaindex.questionnaire;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fang.chinaindex.questionnaire.db.DBOpenHelper;
import com.fang.chinaindex.questionnaire.db.DaoMaster;
import com.fang.chinaindex.questionnaire.db.DaoSession;
import com.fang.chinaindex.questionnaire.repository.CacheRepository;
import com.fang.chinaindex.questionnaire.repository.NetRepository;
import com.fang.chinaindex.questionnaire.repository.impl.CacheRepositoryImpl;
import com.fang.chinaindex.questionnaire.repository.impl.NetRepositoryImpl;

/**
 * Created by aspsine on 15-5-9.
 */
public class App extends Application {
    private static CacheRepository cacheRepository;
    private static NetRepository sRepository;
    private static RequestQueue sRequestQueue;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        CrashHandler.getInstance(getApplicationContext());
        initDataBase();
    }

    private void initDataBase(){
        DBOpenHelper dbOpenHelper = new DBOpenHelper(getApplicationContext());
        DaoMaster daoMaster = new DaoMaster(dbOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        cacheRepository = new CacheRepositoryImpl(daoSession);
    }

    public static CacheRepository getCacheRepository(){
        return cacheRepository;
    }

    /**
     * NOTE:there is no multiThread use simple singleton
     * get volley network api access repository
     * @return
     */
    public static final NetRepository getNetRepository() {
        if (sRepository == null) {
            sRepository = new NetRepositoryImpl();
        }
        return sRepository;
    }

    /**
     * NOTE:there is no multiThread use simple singleton
     * get volley RequestQueue
     * @return
     */
    public static final RequestQueue getRequestQueue() {
        if (sRequestQueue == null) {
            sRequestQueue = Volley.newRequestQueue(sContext);
        }
        return sRequestQueue;
    }
}
