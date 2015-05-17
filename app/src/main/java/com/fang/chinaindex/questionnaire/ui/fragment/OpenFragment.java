package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.util.Base64;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends QuestionBaseFragment {
    private Question mQuestion;

    public OpenFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mQuestion = getQuestion();
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_open;
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
