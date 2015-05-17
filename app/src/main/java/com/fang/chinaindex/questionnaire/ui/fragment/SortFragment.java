package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Question;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends QuestionBaseFragment {
    private Question mQuestion;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = getQuestion();
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setQuestionTitle(mQuestion);
    }
}
