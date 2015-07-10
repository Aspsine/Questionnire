package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.ui.adapter.NewSurveysAdapter;
import com.fang.chinaindex.questionnaire.util.L;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewSurveysFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = NewSurveysFragment.class.getSimpleName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewSurveysAdapter mAdapter;

    private String mUserId;

    public NewSurveysFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new NewSurveysAdapter();
        mUserId = SharedPrefUtils.getUserId(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_surveys, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.style_color_primary), getResources().getColor(R.color.style_color_primary_dark), getResources().getColor(R.color.style_color_primary_light));
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                refresh();
            }
        });
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        App.getRepository().getSurveyResults(mUserId, new Repository.Callback<List<SurveyInfo>>() {
            @Override
            public void success(List<SurveyInfo> surveyInfos) {
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.setData(surveyInfos);
                getSurveys(surveyInfos);
            }

            @Override
            public void failure(Exception e) {
                mAdapter.setData(App.getCacheRepository().getSurveyInfos(mUserId));
                dismiss();
                swipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }
        });
    }


    private void getSurveys(List<SurveyInfo> surveyInfos) {
        show("Caching...");

        List<String> cachedSurveyIds = App.getCacheRepository().getSurveyIds();
        List<String> userLinkedSurveyIds = App.getCacheRepository().getSurveyIds(mUserId);
        List<String> newSurveyIds = new ArrayList<>();

        for (SurveyInfo surveyInfo : surveyInfos) {
            boolean surveyHas = false;
            for (String cachedInfoId : cachedSurveyIds) {
                if (surveyInfo.getSurveyId().equals(cachedInfoId)) {
                    surveyHas = true;
                    break;
                }
            }

            if (surveyHas) {
                boolean linkedHas = userLinkedSurveyIds.contains(surveyInfo.getSurveyId());
                if (!linkedHas) {
                    L.i(TAG, "link :" + surveyInfo.getTitle());
                    App.getCacheRepository().linkUserAndSurveyInfo(mUserId, surveyInfo.getSurveyId());
                }
            } else {
                newSurveyIds.add(surveyInfo.getSurveyId());
            }
        }
        L.i(TAG, "newSurveyIds.size() = " + newSurveyIds.size());
        if (newSurveyIds.size() <= 0) {
            dismiss();
            return;
        }

        App.getRepository().getSurveyDetails(SharedPrefUtils.getUserId(getActivity()), newSurveyIds, new Repository.Callback<List<Survey>>() {
            @Override
            public void success(final List<Survey> surveys) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        long start = System.currentTimeMillis();
                        App.getCacheRepository().saveSurveys(mUserId, surveys);
                        L.i(TAG, "time = " + (System.currentTimeMillis() - start));
                        handler.obtainMessage(0).sendToTarget();
                    }
                }).start();
            }

            @Override
            public void failure(Exception e) {
                dismiss();
                e.printStackTrace();
            }
        });
    }

    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                dismiss();
            }
        }
    };

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
    }
}
