package com.fang.chinaindex.questionnaire.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/19.
 */
public class OptionDragSortAdapter extends RecyclerViewAdapter {
    private static final String TAG = OptionDragSortAdapter.class.getSimpleName();

    private List<Option> mOptions;

    public OptionDragSortAdapter() {
        mOptions = new ArrayList<Option>();
    }

    public void setData(List<Option> options) {
        mOptions.clear();
        mOptions.addAll(options);
        int i = 0;
        for (Option option : mOptions) {
            option.setOptionTitle(i++ + "");
        }
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        final DragSortViewHolder holder = new DragSortViewHolder(inflate(parent, R.layout.item_option_sort));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                setChecked(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((DragSortViewHolder) viewHolder).bindData(mOptions.get(i));
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public void setChecked(int position) {
        Option option = mOptions.get(position);
        boolean isChecked = option.isChecked();
        int divPos = getDividePosition();

        option.setChecked(!isChecked);
        mOptions.remove(option);
//        notifyItemRemoved(position);
        if (isChecked) {
            divPos = divPos - 1;
        }
        mOptions.add(divPos, option);
//        notifyItemInserted(divPos);
        notifyItemMoved(position, divPos);
        notifyItemRangeChanged(position <= divPos ? position : divPos, Math.abs(divPos - position) + 1);
    }

    public interface PositionChangedCallBack {
        void changed(int divPos);
    }

    public int getDividePosition() {
        int i = 0;
        while (i < mOptions.size() && mOptions.get(i).isChecked()) {
            i++;
        }
        return i;
    }

    public static class DragSortViewHolder extends AbstractDraggableItemViewHolder {
        ViewGroup itemContainer;
        ImageView ivDragHandler;
        TextView tvTitle;
        TextView tvNum;
        private Option mOption;

        public DragSortViewHolder(View itemView) {
            super(itemView);
            itemContainer = (ViewGroup) itemView.findViewById(R.id.container);
            ivDragHandler = (ImageView) itemView.findViewById(R.id.ivDragHandle);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNum = (TextView) itemView.findViewById(R.id.tvNum);
        }

        public void bindData(Option option) {
            mOption = option;
            ivDragHandler.setVisibility(mOption.isChecked() ? View.VISIBLE : View.GONE);
            tvNum.setVisibility(mOption.isChecked() ? View.VISIBLE : View.GONE);
            tvTitle.setText(mOption.getOptionTitle());
            tvNum.setText(String.valueOf(getLayoutPosition()));
        }
    }


}
