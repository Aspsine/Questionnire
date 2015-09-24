package com.fang.chinaindex.questionnaire.ui.fragment;

import android.support.v4.app.FragmentManager;
import android.view.View;

import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.model.SubOption;

import java.util.List;


public class MultiChoiceFragment extends SingleMultiOpenBaseFragment {

    @Override
    public void onItemClick(View v, int position, Option option) {
        if (!option.isOther()) {
            //not open option
//            if (hasSubQuestion(option)) {
//                showSubQuestion(option);
//            } else {
//                mAdapter.toggle(position);
//            }
            mAdapter.toggle(position);
        }
    }

    protected boolean hasSubQuestion(Option option) {
        return !option.getSubOptions().isEmpty();
    }

    private void showSubQuestion(Option option) {
        FragmentManager fm = getChildFragmentManager();

        SubQuestionDialogFragment dialog = (SubQuestionDialogFragment) fm.findFragmentByTag(SubQuestionDialogFragment.TAG);
        if (dialog == null) {
            dialog = new SubQuestionDialogFragment();
        }
        dialog.setOption(option);
        dialog.show(getChildFragmentManager(), SubQuestionDialogFragment.TAG);
    }
}
