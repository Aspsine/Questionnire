package com.fang.chinaindex.questionnaire.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.model.Option;

/**
 * Created by Aspsine on 2015/9/24.
 */
public class SubQuestionDialogFragment extends DialogFragment {
    public static final String TAG = SubQuestionDialogFragment.class.getSimpleName();

    private Option mOption;

    public SubQuestionDialogFragment() {

    }

    public void setOption(Option option) {
        mOption = option;
    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        return inflater.inflate();
//    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }
}
