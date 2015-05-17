package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.util.L;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnFinishedFragment extends Fragment {
    public static final String TAG = UnFinishedFragment.class.getSimpleName();

    public UnFinishedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        L.e(TAG,"onCreate");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.e(TAG, "onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        L.e(TAG, "onCreateView");
        return inflater.inflate(R.layout.fragment_un_finished, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        L.e(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        L.e(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        L.e(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        L.e(TAG, "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        L.e(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        L.e(TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        L.e(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        L.e(TAG, "onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        L.e(TAG, "onDetach");
        super.onDetach();
    }
}
