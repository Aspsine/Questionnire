package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.ui.adapter.OptionAdapter;
import com.fang.chinaindex.questionnaire.ui.adapter.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChoiceFragment extends QuestionBaseFragment implements RecyclerViewAdapter.OnItemClickListener<Option> {
    private Question mQuestion;
    private OptionAdapter mAdapter;
    public SingleChoiceFragment() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = getQuestion();
        mAdapter = new OptionAdapter();
        mAdapter.setOnItemClickListener(this);
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_single_choice;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setQuestionTitle(mQuestion);
        mAdapter.setData(mQuestion.getOptions());
    }

    @Override
    public void onItemClick(int position, Option option, View v) {
        mAdapter.toggleAndUnCheckOthers(position);
        getSurveyActivity().showNextQuestion();
    }


}
