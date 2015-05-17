package com.fang.chinaindex.questionnaire.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.ui.activity.SurveyActivity;

/**
 * Created by aspsine on 15/5/13.
 */
public abstract class QuestionBaseFragment extends Fragment {
    private Question mQuestion;
    private TextView tvQuestionTitle;

    public void setQuestion(Question q){
        mQuestion = q;
    }

    public Question getQuestion(){
        return mQuestion;
    }

    protected abstract int onCreateFragmentView();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(onCreateFragmentView(), container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        tvQuestionTitle = (TextView) view.findViewById(R.id.tvQuestion);
    }

    public void setQuestionTitle(Question mQuestion) {
        StringBuilder sb = new StringBuilder();
        sb.append("<strong>")
                .append("<font color=\"#F5B961\">")
                .append(Boolean.valueOf(mQuestion.getIsMust())? "":"*")
                .append("</font>")
                .append("Q").append(mQuestion.getqNum()).append(".")
                .append(mQuestion.getQuestionTitle())
                .append("</strong>")
                .append("<font color=\"#F5B961\">")
                .append("[").append(mQuestion.getCategoryText()).append("]")
                .append("</font>");
        tvQuestionTitle.setText(Html.fromHtml(sb.toString()));
    }

    public SurveyActivity getSurveyActivity(){
        return (SurveyActivity) getActivity();
    }




}
