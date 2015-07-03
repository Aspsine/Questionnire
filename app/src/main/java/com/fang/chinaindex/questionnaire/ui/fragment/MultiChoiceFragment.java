package com.fang.chinaindex.questionnaire.ui.fragment;

import android.view.View;

import com.fang.chinaindex.questionnaire.model.Option;


public class MultiChoiceFragment extends SingleMultiOpenBaseFragment {

    @Override
    public void onItemClick(View v, int position, Option option) {
        if (!option.isOther()) {
            //not open option
            mAdapter.toggle(position);
        }
    }
}
