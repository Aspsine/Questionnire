package com.fang.chinaindex.questionnaire.ui.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;

import com.fang.chinaindex.questionnaire.R;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class BaseFragment extends Fragment {
    private ProgressDialog pDialog;
    
    protected void show() {
        Activity activity = getActivity();
        if (pDialog == null && activity != null && !activity.isFinishing()) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(getString(R.string.login_progressbar_msg));
        }
        pDialog.show();
    }

    protected void dismiss() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }
}
