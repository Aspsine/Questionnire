package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.ui.adapter.OptionDragSortAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends QuestionBaseFragment {

    private RecyclerView recyclerView;
    private OptionDragSortAdapter mAdapter;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new OptionDragSortAdapter();
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
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
    public void onPause() {
        super.onPause();
    }

}
