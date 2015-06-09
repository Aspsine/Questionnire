package com.fang.chinaindex.questionnaire.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Section;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspsine on 15/5/10.
 */
public class DrawerAdapter extends RecyclerViewAdapter {
    private List<Section> mSections;
    private OnItemClickListener mListener;


    public DrawerAdapter() {
        mSections = new ArrayList<Section>();
    }

    public void setSections(List<Section> sections) {
        mSections.clear();
        mSections.addAll(sections);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SectionViewHolder(inflate(parent, R.layout.item_section), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SectionViewHolder) holder).bindData(mSections.get(position), mCurrentCheckedPosition == position);
    }

    @Override
    public int getItemCount() {
        return mSections.size();
    }

    public CharSequence getItem(int position) {
        return mSections.get(position).getTitle();
    }

    private int mLastCheckedPosition = -1;
    private int mCurrentCheckedPosition = 0;

    public void setItemChecked(int position, boolean b) {
        mLastCheckedPosition = mCurrentCheckedPosition;
        mCurrentCheckedPosition = position;
        if (mLastCheckedPosition != -1) {
            notifyItemChanged(mLastCheckedPosition);
        }
        notifyItemChanged(position);
    }

    private static class SectionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvTitle;
        private Section mSection;
        private OnItemClickListener<Section> mmListener;


        public SectionViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            mmListener = listener;
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            if (mmListener != null) {
                tvTitle.setOnClickListener(this);
            }
        }

        public void bindData(Section section, boolean isSelected) {
            mSection = section;
            tvTitle.setText(section.getTitle());
            if (isSelected) {
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.navigation_item_selected));
            } else {
                itemView.setBackgroundColor(Color.WHITE);
            }
        }

        @Override
        public void onClick(View v) {
            mmListener.onItemClick(getLayoutPosition(), mSection, v);
        }
    }
}
