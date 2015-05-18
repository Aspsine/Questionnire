package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.SurveyResults;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.ui.adapter.NewSurveysAdapter;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewSurveysFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout swipeRefreshLayout;
    private NewSurveysAdapter mAdapter;

    public NewSurveysFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new NewSurveysAdapter();
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
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

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
        App.getRepository().getSurveyResults(SharedPrefUtils.getUserId(getActivity()), new Repository.Callback<List<SurveyInfo>>() {
            @Override
            public void success(List<SurveyInfo> surveyInfos) {
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.setData(surveyInfos);
            }

            @Override
            public void failure(Exception e) {
                swipeRefreshLayout.setRefreshing(false);
                e.printStackTrace();
            }
        });
    }



}