package com.fang.chinaindex.questionnaire.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by aspsine on 15/5/17.
 */
public abstract class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static interface OnItemClickListener<T> {
        void onItemClick(View v, int position, T t);
    }

    public static interface OnItemLongClickListener<T> {
        boolean onItemLongClick(View v, int position, T t);
    }

    static interface OnItemMenuClickListener<T> {
        void onItemMenuClick(View v, int position, T t);
    }


    public View inflate(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }
}
