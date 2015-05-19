package com.fang.chinaindex.questionnaire.ui.fragment;


import android.os.Build;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.ui.adapter.OptionDragSortAdapter;
import com.h6ah4i.android.widget.advrecyclerview.animator.GeneralItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.animator.RefactoredDefaultItemAnimator;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class SortFragment extends QuestionBaseFragment {

    private RecyclerView recyclerView;
    private OptionDragSortAdapter mAdapter;
    RecyclerViewDragDropManager mDragDropManager;

    private RecyclerView.Adapter mWrappedAdapter;


    public SortFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int onCreateFragmentView() {
        return R.layout.fragment_sort;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDragDropManager = new RecyclerViewDragDropManager();
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        layoutManager.setDraggingItemShadowDrawable();

        final OptionDragSortAdapter adapter = new OptionDragSortAdapter();
        mAdapter = adapter;
        mWrappedAdapter = mDragDropManager.createWrappedAdapter(adapter);
        final GeneralItemAnimator animator = new RefactoredDefaultItemAnimator();

        recyclerView.setAdapter(mAdapter); // requires *wrapped* adapter
        recyclerView.setItemAnimator(animator);
        // additional decorations
        //noinspection StatementWithEmptyBody
        if (supportsViewElevation()) {
            // Lollipop or later has native drop shadow feature. ItemShadowDecorator is not required.
        }else {
//            recyclerView.addItemDecoration();
        }

//        mDragDropManager.attachRecyclerView(recyclerView);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mAdapter.setData(mQuestion.getOptions());
    }

    @Override
    public void onPause() {
        super.onPause();
        mDragDropManager.cancelDrag();
    }

    private boolean supportsViewElevation() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
    }
}
