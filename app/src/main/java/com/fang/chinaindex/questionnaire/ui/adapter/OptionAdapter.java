package com.fang.chinaindex.questionnaire.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.ui.widget.CheckableLinearLayout;
import com.fang.chinaindex.questionnaire.util.L;

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

    public static final class TYPE {
        public static final int NORMAL = 0;
        public static final int OPEN = 1;
    }

    @Override
    public int getItemViewType(int position) {
        return mOptions.get(position).isOther() ? TYPE.OPEN : TYPE.NORMAL;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        switch (viewType) {
            case TYPE.NORMAL:
                holder = new OptionTextViewHolder(inflate(parent, R.layout.item_option_text), mListener);
                break;
            case TYPE.OPEN:
                holder = new OpenOptionViewHolder(inflate(parent, R.layout.item_option_editext), mListener);
                break;
            default:
                throw new IllegalArgumentException("wrong type");
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        switch (type) {
            case TYPE.NORMAL:
                ((OptionTextViewHolder) holder).bindData(mOptions.get(position));
                break;
            case TYPE.OPEN:
                ((OpenOptionViewHolder) holder).bindData(mOptions.get(position));
                break;
        }

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
    public void unCheckAllExceptPosition(int position) {
        Option option = null;
        for (int i = 0, size = mOptions.size(); i < size; i++) {
            if (i != position) {
                option = mOptions.get(i);
                if (option.isChecked()) {
                    option.setChecked(false);
                    notifyItemChanged(i);
                }
            }
        }
    }

    public static class OpenOptionViewHolder extends RecyclerView.ViewHolder implements TextWatcher {
        CheckableLinearLayout cllOpen;
        TextView tvOpen;
        EditText etOpen;
        private Option mOption;
        private OnItemClickListener<Option> mmListener;

        public OpenOptionViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.mmListener = listener;
            cllOpen = (CheckableLinearLayout) itemView.findViewById(R.id.llOpen);
            tvOpen = (TextView) itemView.findViewById(R.id.tvOpen);
            etOpen = (EditText) itemView.findViewById(R.id.etOpen);
            etOpen.addTextChangedListener(this);
        }

        public void bindData(Option option) {
            mOption = option;
            if (!mOption.isChecked()) {
                mOption.setOpenAnswer("");
            }
            cllOpen.setChecked(mOption.isChecked());
            tvOpen.setText(mOption.getOptionTitle());
            etOpen.setText(mOption.getOpenAnswer());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            boolean isEmpty = TextUtils.isEmpty(s);
            if (mOption.isChecked() == isEmpty) {
                mOption.setChecked(!isEmpty);
                if (mOption.isChecked() && mmListener != null) {
                    mmListener.onItemClick(cllOpen, getLayoutPosition(), mOption);
                }
            }
            mOption.setOpenAnswer(String.valueOf(s));
            cllOpen.setChecked(mOption.isChecked());
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
                mmListener.onItemClick(v, getLayoutPosition(), mOption);
            }
        }
    }
}
