package com.fang.chinaindex.questionnaire.ui.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.ui.activity.SurveyActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aspsine on 15/5/13.
 */
public class NewSurveysAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SurveyInfo> mSurveyInfos;

    public NewSurveysAdapter() {
        this.mSurveyInfos = new ArrayList<SurveyInfo>();
    }

    public void setData(List<SurveyInfo> surveyInfos) {
        this.mSurveyInfos.clear();
        mSurveyInfos.addAll(surveyInfos);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SurveyInfoViewHolder(inflate(parent, R.layout.item_survey_info));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((SurveyInfoViewHolder) holder).bindData(mSurveyInfos.get(position));
    }

    @Override
    public int getItemCount() {
        return mSurveyInfos.size();
    }

    private View inflate(ViewGroup parent, int layoutId) {
        return LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
    }

    public static class SurveyInfoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tvTitle;
        TextView tvNum;
        Button btnStart;
        SurveyInfo mInfo;

        public SurveyInfoViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvNum = (TextView) itemView.findViewById(R.id.tvNum);
            btnStart = (Button) itemView.findViewById(R.id.btnStart);
            itemView.setOnClickListener(this);
        }

        public void bindData(SurveyInfo info) {
            mInfo = info;
            tvTitle.setText(info.getTitle());
            tvNum.setText(info.getUpdateTime());
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), SurveyActivity.class);
            intent.putExtra("EXTRA_SURVEY_ID", String.valueOf(mInfo.getSurveyId()));
            v.getContext().startActivity(intent);
        }
    }


}
