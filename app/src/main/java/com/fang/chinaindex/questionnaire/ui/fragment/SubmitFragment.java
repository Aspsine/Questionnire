package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.model.UploadSampleResult;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.ui.activity.SurveyActivity;
import com.fang.chinaindex.questionnaire.ui.adapter.RecyclerViewAdapter;
import com.fang.chinaindex.questionnaire.ui.adapter.UnfinishedAdapter;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SubmitFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener<SurveyInfo>, RecyclerViewAdapter.OnItemLongClickListener<SurveyInfo> {
    public static final String TAG = SubmitFragment.class.getSimpleName();

    private ActionMode mActionMode;

    private String mUserId;
    private UnfinishedAdapter mAdapter;
    private boolean mEditMode;

    public SubmitFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        mUserId = SharedPrefUtils.getUserId(getActivity());
        mAdapter = new UnfinishedAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_submit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    @Override
    public void onDestroyView() {
        if (mEditMode && mActionMode != null) {
            toggleEditMode(false);
        }
        super.onDestroyView();
    }

    @Override
    public void onItemClick(View v, int position, SurveyInfo surveyInfo) {
        if (mEditMode) {
            onEditModeListItemClick(position, surveyInfo);
        } else {
            intentToSurveyActivity(v.getContext(), surveyInfo);
        }
    }

    @Override
    public boolean onItemLongClick(View v, int position, SurveyInfo surveyInfo) {
        if (!mEditMode) {
            toggleEditMode(true);
            toggleItemSelection(position, surveyInfo);
            updateActionModeTitle(mActionMode, mAdapter.getSelectedCount());
            return true;
        }
        return true;
    }

    private void onEditModeListItemClick(int position, SurveyInfo surveyInfo) {
        toggleItemSelection(position, surveyInfo);
        int selectedCount = mAdapter.getSelectedCount();
        if (selectedCount == 0) {
            mActionMode.finish();
        } else {
            updateActionModeTitle(mActionMode, selectedCount);
        }
    }

    private void intentToSurveyActivity(Context context, SurveyInfo info) {
        Intent intent = new Intent(getActivity(), SurveyActivity.class);
        intent.putExtra("EXTRA_SURVEY_ID", String.valueOf(info.getSurveyId()));
        intent.putExtra("EXTRA_SURVEY_START_TIME", info.getStartTime());
        context.startActivity(intent);
    }

    /**
     * toggle actionbar editMode
     *
     * @param editMode
     */
    private void toggleEditMode(boolean editMode) {
        mEditMode = editMode;
        if (mEditMode) {
            // show actionMode toolbar
            mActionMode = ((AppCompatActivity) getActivity()).startSupportActionMode(mActionModeCallBack);
        } else {
            // clear all selection of the recyclerView
            mAdapter.clearAllSelection();
            // finish actionMode toolBar
            if (mActionMode != null) {
                mActionMode.finish();
            }
        }
    }

    private void toggleItemSelection(int position, SurveyInfo info) {
        info.setSelected(!info.isSelected());
        mAdapter.notifyItemChanged(position);
    }

    private void updateActionModeTitle(ActionMode mode, int selectedCount) {
        mode.setTitle(String.valueOf(selectedCount));
    }

    private void refresh() {
        List<SurveyInfo> surveyInfos = App.getCacheRepository().getAnsweredSurveyInfos(mUserId, true);
        mAdapter.setData(surveyInfos);
    }

    private void checkUpload() {
        if (mAdapter.getSelectedCount() > 0) {
            List<SurveyInfo> surveyInfos = mAdapter.getSelectedSurveyInfos();
            for (SurveyInfo surveyInfo : surveyInfos) {
                upload(surveyInfo);
            }
        }
    }

    private void upload(final SurveyInfo surveyInfo) {
        List<Question> questions = App.getCacheRepository().getAnsweredQuestions(mUserId, surveyInfo.getSurveyId(), surveyInfo.getStartTime());
        App.getRepository().uploadSample(mUserId, new Survey(surveyInfo, questions), new Repository.Callback<UploadSampleResult>() {
            @Override
            public void success(UploadSampleResult uploadSampleResult) {
                mAdapter.remove(surveyInfo);
                App.getCacheRepository().deleteAnsweredSurvey(mUserId, surveyInfo.getSurveyId(), surveyInfo.getStartTime());
                Toast.makeText(getActivity(), "succeed", Toast.LENGTH_SHORT).show();

                int count = mAdapter.getSelectedCount();
                if (count <= 0) {
                    toggleEditMode(false);
                } else {
                    updateActionModeTitle(mActionMode, count);
                }
            }

            @Override
            public void failure(Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            updateActionModeTitle(mode, mAdapter.getSelectedCount());
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_action_mode_submit, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            int id = item.getItemId();
            switch (id) {
                case R.id.action_select:
                    if (mAdapter.isAllSelected()) {
                        toggleEditMode(false);
                    } else {
                        mAdapter.selectAll();
                        updateActionModeTitle(mode, mAdapter.getSelectedCount());
                    }
                    return true;
                case R.id.action_delete:
                    showDeleteDialog();
                    return true;
                case R.id.action_upload:
                    checkUpload();
                    return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            toggleEditMode(false);
        }

        private void showDeleteDialog() {
            AlertDialog dialog = new AlertDialog.Builder(getActivity())
                    .setTitle("Alert!")
                    .setMessage("Delete Selected unfinished Surveys?")
                    .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            List<SurveyInfo> surveyInfos = mAdapter.getSelectedSurveyInfos();
                            App.getCacheRepository().deleteAnsweredSurveys(mUserId, surveyInfos);
                            mAdapter.removeSelectedSurveys();
                            toggleEditMode(false);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create();
            dialog.show();
        }
    };


}
