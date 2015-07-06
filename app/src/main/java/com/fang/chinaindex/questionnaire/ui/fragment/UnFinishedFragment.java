package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.ui.adapter.RecyclerViewAdapter;
import com.fang.chinaindex.questionnaire.ui.adapter.UnfinishedAdapter;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UnFinishedFragment extends Fragment implements RecyclerViewAdapter.OnItemClickListener<SurveyInfo>, RecyclerViewAdapter.OnItemLongClickListener<SurveyInfo> {
    public static final String TAG = UnFinishedFragment.class.getSimpleName();
    private UnfinishedAdapter mAdapter;
    private boolean mEditMode;

    public UnFinishedFragment() {
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
        mAdapter = new UnfinishedAdapter();
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_un_finished, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refresh();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        return super.onContextItemSelected(item);

    }

    @Override
    public void onItemClick(View v, int position, SurveyInfo surveyInfo) {
//        if (mEditMode) {
//            surveyInfo.setSelected(!surveyInfo.isSelected());
//            mAdapter.notifyItemChanged(position);
//        } else {
//            Intent intent = new Intent(getActivity(), SurveyActivity.class);
//            intent.putExtra("EXTRA_SURVEY_ID", String.valueOf(surveyInfo.getSurveyId()));
//            intent.putExtra("EXTRA_SURVEY_START_TIME", surveyInfo.getStartTime());
//            v.getContext().startActivity(intent);
//        }
        showEditModeToolBar();
    }

    @Override
    public boolean onItemLongClick(View v, int position, SurveyInfo surveyInfo) {
//        if (!mEditMode) {
//            showEditModeToolBar();
//            return false;
//        }
        return true;
    }


    private ActionMode.Callback mActionModeCallBack = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_login, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            if (item.getItemId() == R.id.action_settings) {
                mode.finish();
                return true;
            }
            return false;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {

        }
    };


    private void showEditModeToolBar() {
        getActivity().startActionMode(mActionModeCallBack);
    }

    private void refresh() {
        List<SurveyInfo> surveyInfos = App.getCacheRepository().getAnsweredSurveyInfos(SharedPrefUtils.getUserId(getActivity()), false);
        mAdapter.setData(surveyInfos);
    }


}
