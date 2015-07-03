package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.ui.adapter.OptionAdapter;
import com.fang.chinaindex.questionnaire.ui.adapter.RecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleMultiOpenBaseFragment extends QuestionBaseFragment implements RecyclerViewAdapter.OnItemClickListener<Option> {
    protected OptionAdapter mAdapter;

    public SingleMultiOpenBaseFragment() {
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
        return R.layout.fragment_single_multi_base;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setData(mQuestion.getOptions());
    }

    @Override
    public void onItemClick(View v, int position, Option option) {
    }


}
