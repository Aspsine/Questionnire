package com.fang.chinaindex.questionnaire.ui.adapter;

import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.util.L;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Aspsine on 2015/5/19.
 * //FIXME maybe have some bugs
 */
public class OptionDragSortAdapter extends RecyclerView.Adapter<OptionDragSortAdapter.DragSortViewHolder> implements DraggableItemAdapter<OptionDragSortAdapter.DragSortViewHolder> {
    private static final String TAG = OptionDragSortAdapter.class.getSimpleName();

    private List<Option> mOptions;

    private OnItemMovedListener mItemMovedListener;

    public interface OnItemMovedListener {
        void onItemMoved(int fromPos, int moveToPos);
    }

    public OptionDragSortAdapter() {
        mOptions = new LinkedList<Option>();
        setHasStableIds(true);
    }

    public void setData(List<Option> options) {
        mOptions.clear();
        mOptions.addAll(options);
        notifyDataSetChanged();
    }

    public void setOnItemMovedListener(OnItemMovedListener listener) {
        mItemMovedListener = listener;
    }

    @Override
    public OptionDragSortAdapter.DragSortViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final DragSortViewHolder holder = new DragSortViewHolder(inflater.inflate(R.layout.item_option_sort, parent, false));
        holder.itemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getLayoutPosition();
                setChecked(position);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(OptionDragSortAdapter.DragSortViewHolder viewHolder, int i) {
        viewHolder.bindData(mOptions.get(i));
    }

    @Override
    public int getItemCount() {
        return mOptions.size();
    }

    @Override
    public long getItemId(int position) {
        return Long.valueOf(mOptions.get(position).getId());
    }

    public void setChecked(int position) {
        Option option = mOptions.get(position);
        boolean isChecked = option.isChecked();
        mOptions.remove(position);
        int firstUncheckedPos = getFirstUnCheckedPosition();
        mOptions.add(firstUncheckedPos, option);
        option.setChecked(!isChecked);
        L.i(TAG, "position = " + position + " firstUncheckedPos = " + firstUncheckedPos + " isChecked=" + isChecked);
        notifyItemMoved(position, firstUncheckedPos);
        notifyItemRangeChanged(position <= firstUncheckedPos ? position : firstUncheckedPos,
                Math.abs(position - firstUncheckedPos) + 1);
        mItemMovedListener.onItemMoved(position, firstUncheckedPos);
    }


    public int getFirstUnCheckedPosition() {
        int size = mOptions.size();
        for (int i = 0; i < size; i++) {
            if (!mOptions.get(i).isChecked()) {
                return i;
            }
        }
        return size;
    }


    @Override
    public boolean onCheckCanStartDrag(OptionDragSortAdapter.DragSortViewHolder viewHolder, int position, int x, int y) {
        // x, y --- relative from the itemView's top-left
        final View containerView = viewHolder.itemContainer;
        final View dragHandleView = viewHolder.ivDragHandler;

        final int offsetX = containerView.getLeft() + (int) (ViewCompat.getTranslationX(containerView) + 0.5f);
        final int offsetY = containerView.getTop() + (int) (ViewCompat.getTranslationY(containerView) + 0.5f);

        if (getFirstUnCheckedPosition() > 0) {
            return hitTest(dragHandleView, x - offsetX, y - offsetY);
        } else {
            return false;
        }
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
    public ItemDraggableRange onGetItemDraggableRange(OptionDragSortAdapter.DragSortViewHolder viewHolder, int position) {
        final int start = 0;
        final int end = getFirstUnCheckedPosition() - 1;

        return new ItemDraggableRange(start, end < 0 ? 0 : end);
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

            ivDragHandler.setBackgroundResource(R.mipmap.drag);
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
