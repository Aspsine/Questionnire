package com.fang.chinaindex.questionnaire.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Question;


public class MultiChoiceFragment extends QuestionBaseFragment {
    private Question mQuestion;

    public MultiChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = getQuestion();
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_multi_choice;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setQuestionTitle(mQuestion);
    }
}
