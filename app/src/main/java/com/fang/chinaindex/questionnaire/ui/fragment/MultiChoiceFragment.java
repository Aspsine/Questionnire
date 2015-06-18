package com.fang.chinaindex.questionnaire.ui.fragment;

import android.view.View;

import com.fang.chinaindex.questionnaire.model.Option;


public class MultiChoiceFragment extends SingleMultiOpenBaseFragment {

    @Override
    public void onItemClick(int position, Option option, View v) {
        if (!option.isOther()) {
            //not open option
            mAdapter.toggle(position);
        }
    }
}
