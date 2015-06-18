package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Fragment;
import android.view.View;

import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.ui.activity.SurveyActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleChoiceFragment extends SingleMultiOpenBaseFragment {

    @Override
    public void onItemClick(int position, Option option, View v) {
        if (option.isOther()) {
            //open option
            mAdapter.unCheckAllExceptPosition(position);
        } else {
            mAdapter.toggleAndUnCheckOthers(position);
            getSurveyActivity().showNextQuestion();
        }
    }
}
