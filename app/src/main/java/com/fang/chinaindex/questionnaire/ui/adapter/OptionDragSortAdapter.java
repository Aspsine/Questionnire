package com.fang.chinaindex.questionnaire.ui.adapter;

import android.media.Image;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.draggable.RecyclerViewDragDropManager;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/19.
 */
public class OptionDragSortAdapter extends RecyclerViewAdapter implements DraggableItemAdapter<RecyclerView.ViewHolder> {
    private static final String TAG = OptionDragSortAdapter.class.getSimpleName();

    private List<Option> mOptions;

    public OptionDragSortAdapter() {
        mOptions = new ArrayList<Option>();
        // DraggableItemAdapter requires stable ID, and also
        // have to implement the getItemId() method appropriately.
        setHasStableIds(true);
    }

    public void setData(List<Option> options) {
        mOptions.clear();
        mOptions.addAll(options);
        notifyDataSetChanged();
    }

    public static boolean hitTest(View v, int x, int y) {
        final int tx = (int) (ViewCompat.getTranslationX(v) + 0.5f);
        final int ty = (int) (ViewCompat.getTranslationY(v) + 0.5f);
        final int left = v.getLeft() + tx;
        final int right = v.getRight() + tx;
        final int top = v.getTop() + ty;
        final int bottom = v.getBottom() + ty;

        return (x >= left) && (x <= right) && (y >= top) && (y <= bottom);
    }

    @Override
    public boolean onCheckCanStartDrag(RecyclerView.ViewHolder viewHolder, int position, int x, int y) {
        // x, y --- relative from the itemView's top-left
        DragSortViewHolder holder = (DragSortViewHolder) viewHolder;
        final View containerView = holder.itemContainer;
        final View dragHandleView = holder.ivDragHandler;

        final int offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        final int offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);

        return hitTest(dragHandleView, offsetX, offsetY);
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(RecyclerView.ViewHolder viewHolder, int i) {
        final int start = 0;
        final int end = getDividePosition();

        return new ItemDraggableRange(start, end);
    }

    public int getDividePosition() {
        int i = 0;
        while (i < mOptions.size() && mOptions.get(i).isChecked()) {
            i++;
        }
        return i;
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }
        final Option option = mOptions.remove(fromPosition);
        mOptions.add(toPosition, option);
        notifyItemMoved(fromPosition, toPosition);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new DragSortViewHolder(inflate(parent, R.layout.item_option_sort));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ((DragSortViewHolder) viewHolder).bindData(mOptions.get(i));
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    public void setChecked(int position, boolean isChecked) {
        Option option = mOptions.get(position);
        int divPos = getDividePosition();
        option.setChecked(isChecked);
        mOptions.remove(option);
        if (isChecked) {
            mOptions.add(divPos, option);
        } else {
            mOptions.add(divPos - 1, option);
        }
        notifyDataSetChanged();
    }

    public static class DragSortViewHolder extends AbstractDraggableItemViewHolder implements View.OnClickListener {
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
            itemView.setOnClickListener(this);
        }

        public void bindData(Option option) {
            mOption = option;
            ivDragHandler.setVisibility(mOption.isChecked() ? View.VISIBLE : View.GONE);
            tvNum.setVisibility(mOption.isChecked() ? View.VISIBLE : View.GONE);
            tvTitle.setText(mOption.getOptionTitle());
            tvNum.setText(String.valueOf(getLayoutPosition()));

            final int dragState = getDragStateFlags();

            if ((dragState & RecyclerViewDragDropManager.STATE_FLAG_IS_UPDATED) != 0) {
                Integer bgResId = 0;
                if ((dragState & RecyclerViewDragDropManager.STATE_FLAG_IS_ACTIVE) != 0) {
                    //dragging
                    bgResId = R.drawable.bg_option_item_selector;
                } else if (((dragState & RecyclerViewDragDropManager.STATE_FLAG_DRAGGING) != 0) &&
                        ((dragState & RecyclerViewDragDropManager.STATE_FLAG_IS_IN_RANGE) != 0)) {
                    //dragging others' state
                    bgResId = R.drawable.bg_option_item_selector;
                } else {
                    //normal
                    bgResId = R.drawable.bg_option_item_selector;
                }
                itemContainer.setBackgroundResource(bgResId);
            }

        }

        @Override
        public void onClick(View v) {
            mOption.setChecked(!mOption.isChecked());
            tvNum.setVisibility(mOption.isChecked() ? View.VISIBLE : View.GONE);
            ivDragHandler.setVisibility(mOption.isChecked() ? View.VISIBLE : View.GONE);
        }
    }


}
