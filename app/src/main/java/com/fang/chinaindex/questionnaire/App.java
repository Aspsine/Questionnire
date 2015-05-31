package com.fang.chinaindex.questionnaire;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fang.chinaindex.questionnaire.db.DBOpenHelper;
import com.fang.chinaindex.questionnaire.db.DaoMaster;
import com.fang.chinaindex.questionnaire.db.DaoSession;
import com.fang.chinaindex.questionnaire.repository.CacheRepository;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.repository.impl.CacheRepositoryImpl;
import com.fang.chinaindex.questionnaire.repository.impl.RepositoryImpl;

/**
 * Created by aspsine on 15-5-9.
 */
public class App extends Application {
    private static CacheRepository cacheRepository;
    private static RequestQueue sRequestQueue;
    private static Repository sRepository;
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
    public static final Repository getRepository() {
        if (sRepository == null) {
            sRepository = new RepositoryImpl();
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
