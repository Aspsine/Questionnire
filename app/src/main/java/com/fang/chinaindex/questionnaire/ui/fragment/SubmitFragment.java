package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.ui.adapter.RecyclerViewAdapter;
import com.fang.chinaindex.questionnaire.ui.adapter.UnfinishedAdapter;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener<SurveyInfo>, RecyclerViewAdapter.OnItemLongClickListener<SurveyInfo>  {
    private UnfinishedAdapter mAdapter;

    private String mUserId;

    public SubmitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new UnfinishedAdapter();
        mUserId = SharedPrefUtils.getUserId(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    @Override
    public void onItemClick(View v, int position, SurveyInfo info) {

    }

    @Override
    public boolean onItemLongClick(View v, int position, SurveyInfo info) {
        return false;
    }

    private void refresh() {
        List<SurveyInfo> surveyInfos = App.getCacheRepository().getAnsweredSurveyInfos(mUserId, true);
        mAdapter.setData(surveyInfos);
    }



}
