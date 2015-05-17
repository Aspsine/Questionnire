package com.fang.chinaindex.questionnaire.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspsine on 15/5/16.
 */
public class OptionAdapter extends RecyclerViewAdapter {
    private List<Option> mOptions;
    private OnItemClickListener<Option> mListener;

    public OptionAdapter() {
        mOptions = new ArrayList<Option>();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setData(List<Option> options) {
        this.mOptions.clear();
        this.mOptions.addAll(options);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OptionTextViewHolder(inflate(parent, R.layout.item_option_text), mListener);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((OptionTextViewHolder) holder).bindData(mOptions.get(position));
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    /**
     * get the data object of the given position
     *
     * @param position
     * @return
     */
    public Option getItem(int position) {
        return mOptions.get(position);
    }

    /**
     * toggle checked status of the given position
     * and unchecked the others at the same time
     *
     * @param position
     */
    public void toggleAndUnCheckOthers(int position) {
        unCheckAllExceptPosition(position);
        toggle(position);
    }

    /**
     * toggle checked status of the given position
     *
     * @param position
     */
    public void toggle(int position) {
        Option option = mOptions.get(position);
        option.setChecked(!option.isChecked());
        notifyItemChanged(position);
    }

    /**
     * make all item unchecked except the given position
     *
     * @param position
     */
    private void unCheckAllExceptPosition(int position) {
        Option option = null;
        for (int i = 0, size = mOptions.size(); i < size; i++) {
            if (i != position) {
                option = mOptions.get(position);
                if (option.isChecked()) {
                    option.setChecked(false);
                    notifyItemChanged(i);
                }
            }
        }
    }

    public static class OptionTextViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CheckedTextView ctvOption;
        LinearLayout llOption;
        private Option mOption;
        private OnItemClickListener<Option> mmListener;

        public OptionTextViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.mmListener = listener;
            llOption = (LinearLayout) itemView.findViewById(R.id.llOption);
            ctvOption = (CheckedTextView) itemView.findViewById(R.id.ctvOption);
            llOption.setOnClickListener(this);
        }

        public void bindData(Option option) {
            this.mOption = option;
            ctvOption.setChecked(option.isChecked());
            ctvOption.setText(option.getOptionTitle());
        }

        @Override
        public void onClick(View v) {
            if (mmListener != null) {
                mmListener.onItemClick(getPosition(), mOption, v);
            }
        }
    }
}
