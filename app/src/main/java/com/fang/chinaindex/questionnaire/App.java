package com.fang.chinaindex.questionnaire;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.fang.chinaindex.questionnaire.db.DBOpenHelper;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.repository.RepositoryImpl;

/**
 * Created by aspsine on 15-5-9.
 */
public class App extends Application {

    private static RequestQueue sRequestQueue;
    private static Repository sRepository;
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext();
        CrashHandler.getInstance(getApplicationContext());
        DBOpenHelper.init(getApplicationContext());
    }

    /**
     * NOTE:there is no multiThread use simple singleton
     * get volley network api access repository
     * @return
     */
    public static final Repository getRepository() {
        if (sRepository == null) {
            sRepository = new RepositoryImpl(sContext);
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
