package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.ui.activity.SurveyActivity;
import com.fang.chinaindex.questionnaire.ui.adapter.RecyclerViewAdapter;
import com.fang.chinaindex.questionnaire.ui.adapter.UnfinishedAdapter;
import com.fang.chinaindex.questionnaire.util.L;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnFinishedFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener<SurveyInfo>, RecyclerViewAdapter.OnItemLongClickListener<SurveyInfo> {
    public static final String TAG = UnFinishedFragment.class.getSimpleName();
    private UnfinishedAdapter mAdapter;
    private boolean mEditMode;

    public UnFinishedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new UnfinishedAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_un_finished, container, false);
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
    public void onItemClick(View v, int position, SurveyInfo surveyInfo) {
        if (mEditMode) {
            surveyInfo.setSelected(!surveyInfo.isSelected());
            mAdapter.notifyItemChanged(position);
        } else {
            Intent intent = new Intent(getActivity(), SurveyActivity.class);
            intent.putExtra("EXTRA_SURVEY_ID", String.valueOf(surveyInfo.getSurveyId()));
            intent.putExtra("EXTRA_SURVEY_START_TIME", surveyInfo.getStartTime());
            v.getContext().startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(View v, int position, SurveyInfo surveyInfo) {
        if (!mEditMode) {
            mEditMode = true;
            showEditModeToolBar();
            mAdapter.setEditMode(true);
            onItemClick(v, position, surveyInfo);
            return false;
        }
        return true;
    }

    private void showEditModeToolBar() {

    }

    private void refresh() {
        List<SurveyInfo> surveyInfos = App.getCacheRepository().getAnsweredSurveyInfos(SharedPrefUtils.getUserId(getActivity()), false);
        mAdapter.setData(surveyInfos);
    }


}
