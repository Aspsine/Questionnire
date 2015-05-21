package com.fang.chinaindex.questionnaire.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.ui.adapter.OptionDragSortAdapter;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends QuestionBaseFragment implements OptionDragSortAdapter.OnItemMovedListener {
    public static final String TAG = SortFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private OptionDragSortAdapter mAdapter;
    private RecyclerView.Adapter mWrappedAdapter;
    private RecyclerViewDragDropManager mDragDropManager;

    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter = new OptionDragSortAdapter();
        mAdapter.setOnItemMovedListener(this);
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity());

        // drag&drop manager
        mDragDropManager = new RecyclerViewDragDropManager();

        //TODO drawable

        final OptionDragSortAdapter adapter = new OptionDragSortAdapter();
        adapter.setOnItemMovedListener(this);
        mAdapter = adapter;
        mWrappedAdapter = mDragDropManager.createWrappedAdapter(adapter);

        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mWrappedAdapter);
        recyclerView.setItemAnimator(animator);

        mDragDropManager.attachRecyclerView(recyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setData(mQuestion.getOptions());
    }

    @Override
    public void onPause() {
        mDragDropManager.cancelDrag();
        super.onPause();
    }

    @Override
    public void onItemMoved(int from, int moveTo) {
        recyclerView.scrollToPosition(moveTo);
    }
}
