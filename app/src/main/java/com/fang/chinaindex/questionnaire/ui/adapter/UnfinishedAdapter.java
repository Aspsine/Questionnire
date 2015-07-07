package com.fang.chinaindex.questionnaire.ui.adapter;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.ui.activity.SurveyActivity;
import com.fang.chinaindex.questionnaire.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aspsine on 2015/7/2.
 */
public class UnfinishedAdapter extends RecyclerViewAdapter {
    private List<SurveyInfo> mSurveyInfos;

    private OnItemClickListener mOnItemClickListener;

    private OnItemLongClickListener mOnItemLongClickListener;

    public UnfinishedAdapter() {
        this.mSurveyInfos = new ArrayList<SurveyInfo>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.mOnItemLongClickListener = onItemLongClickListener;
    }

    public void setData(List<SurveyInfo> surveyInfos) {
        this.mSurveyInfos.clear();
        mSurveyInfos.addAll(surveyInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SurveyInfoViewHolder(UIUtils.inflate(R.layout.item_survey_info_unfinished, parent));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        bindData(((SurveyInfoViewHolder) holder), position);
    }

    @Override
    public int getItemCount() {
        return mSurveyInfos.size();
    }


    public void bindData(final SurveyInfoViewHolder holder, final int position) {
        final SurveyInfo info = mSurveyInfos.get(position);
        holder.tvTitle.setText(info.getTitle());
        holder.tvNum.setText(info.getStartTime());

        Resources res = holder.itemView.getResources();
        if (info.isSelected()) {
            holder.cardView.setCardBackgroundColor(res.getColor(R.color.style_color_primary_light));
        } else {
            holder.cardView.setCardBackgroundColor(res.getColor(R.color.white));
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, position, info);
                }
            }
        });

        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemLongClickListener != null) {
                    return mOnItemLongClickListener.onItemLongClick(v, position, info);
                }
                return true;
            }
        });
    }

    public void selectAll() {
        for (SurveyInfo info : mSurveyInfos) {
            info.setSelected(true);
        }
        this.notifyDataSetChanged();
    }

    public void clearAllSelection() {
        for (int i = 0, size = mSurveyInfos.size(); i < size; i++) {
            SurveyInfo info = mSurveyInfos.get(i);
            if (info.isSelected()) {
                info.setSelected(false);
                this.notifyItemChanged(i);
            }
        }
    }

    public int getSelectedCount() {
        int selectedCount = 0;
        for (SurveyInfo info : mSurveyInfos) {
            if (info.isSelected()) {
                selectedCount++;
            }
        }
        return selectedCount;
    }

    public static class SurveyInfoViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle;
        TextView tvNum;
        Button btnStart;

        public SurveyInfoViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNum = (TextView) itemView.findViewById(R.id.tvNum);
            btnStart = (Button) itemView.findViewById(R.id.btnStart);
        }
    }

}
