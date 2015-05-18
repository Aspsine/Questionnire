package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Fragment;
import android.view.View;

import com.fang.chinaindex.questionnaire.model.Option;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChoiceFragment extends SingleMultiOpenBaseFragment {

    @Override
    public void onItemClick(int position, Option option, View v) {
        mAdapter.toggleAndUnCheckOthers(position);
        getSurveyActivity().showNextQuestion();
    }
}
