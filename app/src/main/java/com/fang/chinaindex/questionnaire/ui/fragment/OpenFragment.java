package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;

import com.fang.chinaindex.questionnaire.model.Option;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenFragment extends SingleMultiOpenBaseFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (Option o : mQuestion.getOptions()) {
            o.setIsOther(true);
        }
    }
}
