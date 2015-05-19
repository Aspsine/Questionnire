package com.fang.chinaindex.questionnaire.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fang.chinaindex.questionnaire.App;
import com.fang.chinaindex.questionnaire.R;
import com.fang.chinaindex.questionnaire.model.Logic;
import com.fang.chinaindex.questionnaire.model.Option;
import com.fang.chinaindex.questionnaire.model.Question;
import com.fang.chinaindex.questionnaire.model.Survey;
import com.fang.chinaindex.questionnaire.model.SurveyInfo;
import com.fang.chinaindex.questionnaire.repository.Repository;
import com.fang.chinaindex.questionnaire.ui.fragment.MultiChoiceFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.OpenFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.QuestionBaseFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.SingleChoiceFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.SortFragment;
import com.fang.chinaindex.questionnaire.util.L;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 */
public class SurveyActivity extends BaseActivity implements View.OnClickListener {


    private ProgressDialog pDialog;
    private Button btnUp, btnNext;

    private String mSurveyId;
    private SurveyInfo mSurveyInfo;
    private Survey mTemplateSurvey;
    private List<Question> mTemplateQuestions;
    private List<Question> mAnsweredQuestions;
    //    private Question mCurrentQuestion;
    private int mCurrentPosition = 0;


    public static final class TYPE {
        static final int SINGLE = 1;   //单选题
        static final int MULTIPLE = 2;   //多选题
        static final int MARK = 3;   //打分题（单）
        static final int TRUEORFALSE = 4;   //是非题（单）
        static final int OPEN = 5;   //开放题
        static final int TEXT = 6;   //文字描述题
        static final int SORT = 7;   //排序题
    }

    public static final class LOGIC_TYPE {
        static final int SINGLE_JUMP = 1;
        static final int EXIT_SURVEY = 3;
        static final int FINISH_SURVEY = 4;
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_survey;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        mSurveyId = intent.getStringExtra("EXTRA_SURVEY_ID");

        btnNext = (Button) findViewById(R.id.btnNext);
        btnUp = (Button) findViewById(R.id.btnUp);

        btnNext.setOnClickListener(this);
        btnUp.setOnClickListener(this);

        initAnsweredSurvey();
        initTemplateSurvey();
    }

    private void initAnsweredSurvey() {
        mAnsweredQuestions = new ArrayList<Question>();
    }

    private void initTemplateSurvey() {
        refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                showNextQuestion();
                break;
            case R.id.btnUp:

                break;
        }
    }

    private void refresh() {
        show();
        App.getRepository().getSurveyDetails(SharedPrefUtils.getUserId(this), new String[]{mSurveyId}, new Repository.Callback<List<Survey>>() {
            @Override
            public void success(List<Survey> surveys) {
                dismiss();
                mTemplateSurvey = surveys.get(0);
                mSurveyInfo = mTemplateSurvey.getInfo();
                mTemplateQuestions = mTemplateSurvey.getQuestions();

                setTitle(mSurveyInfo.getTitle());

                initQuestion();
            }

            @Override
            public void failure(Exception e) {
                dismiss();
                e.printStackTrace();
            }
        });
    }

    private void initQuestion() {

        Question question = null;
        if (mAnsweredQuestions.isEmpty()) {
            //1. 如果不存在则使用未答题
            mCurrentPosition = 0;
            question = mTemplateQuestions.get(mCurrentPosition);
        } else {
            //2.如果存在已答题，则展示最后一道已答题在模板题库中的下一道题
            mCurrentPosition = calculatePosition();
            if (mCurrentPosition < mTemplateQuestions.size() - 1) {
                question = mTemplateQuestions.get(++mCurrentPosition);
            }
        }
        showQuestion(question);
    }

    private void showQuestion(Question question) {
        changeControlButtons();
        QuestionBaseFragment questionFragment = null;
        switch (Integer.valueOf(question.getCategory())) {
            case TYPE.SINGLE:
                questionFragment = new SingleChoiceFragment();
                break;
            case TYPE.MULTIPLE:
                questionFragment = new MultiChoiceFragment();
                break;
            case TYPE.MARK:
                questionFragment = new SingleChoiceFragment();
                break;
            case TYPE.TRUEORFALSE:
                questionFragment = new SingleChoiceFragment();
                break;
            case TYPE.OPEN:
                questionFragment = new OpenFragment();
                break;
            case TYPE.TEXT:

                break;
            case TYPE.SORT:
                questionFragment = new SortFragment();
                break;
        }
        if (questionFragment == null) {
            return;
        }
        questionFragment.setQuestion(question);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, questionFragment)
                .commit();
    }

    private void showUpQuestion() {
        Question currentQuestion = mTemplateQuestions.get(mCurrentPosition);
        L.i("SurveyActivity", "mCurrentPosition = " + mCurrentPosition);
        List<Option> selectedOptions = getQuestionSelectedOptions(currentQuestion);
        // true answered;
        // false not answered;
        boolean isCurrentQuestionBeenAnswered = selectedOptions.size() > 0;
        if (isCurrentQuestionBeenAnswered) {
            //回答了
            saveQuestion(currentQuestion);
        }
    }

    public void showNextQuestion() {
        Question currentQuestion = mTemplateQuestions.get(mCurrentPosition);
        L.i("SurveyActivity", "mCurrentPosition = " + mCurrentPosition);
        List<Option> selectedOptions = getQuestionSelectedOptions(currentQuestion);
        // true answered;
        // false not answered;
        boolean isCurrentQuestionBeenAnswered = selectedOptions.size() > 0;
        if (!Boolean.valueOf(currentQuestion.getIsMust())) {
            //必答题
            if (isCurrentQuestionBeenAnswered) {
                //回答了
                handlerAnsweredQuestion(currentQuestion);
            } else {
                //没回答
                Toast.makeText(this, "此题是必答题.", Toast.LENGTH_SHORT).show();
            }
        } else {
            //非必答
            if (isCurrentQuestionBeenAnswered) {
                //回答了
                handlerAnsweredQuestion(currentQuestion);
            } else {
                //没回答
                goToNextIndexQuestion(currentQuestion);
            }
        }
    }

    /**
     * 处理已回答的问题
     *
     * @param currentQuestion
     */
    private void handlerAnsweredQuestion(Question currentQuestion) {
        Logic logic = getJumpLogic(currentQuestion.getLogics());
        if (logic == null) {
            goToNextIndexQuestion(currentQuestion);
        } else {
            doLogicJump(currentQuestion, logic);
        }
    }

    /**
     * 非逻辑跳转，进入下一题
     *
     * @param currentQuestion
     */
    private void goToNextIndexQuestion(Question currentQuestion) {
        if (mCurrentPosition == mTemplateQuestions.size() - 1) {
            saveQuestion(currentQuestion);
            prepareUpload();
        } else if (mCurrentPosition < mTemplateQuestions.size()) {
            saveQuestion(currentQuestion);
            showQuestion(mTemplateQuestions.get(++mCurrentPosition));
        } else {
            throw new IllegalStateException("current position is bigger than size of the template questions!");
        }
    }

    /**
     * 是逻辑跳转，处理逻辑跳转结果
     *
     * @param currentQuestion
     * @param logic
     */
    private void doLogicJump(Question currentQuestion, Logic logic) {
        Toast.makeText(this, currentQuestion.getQuestionTitle() + " logic", Toast.LENGTH_SHORT).show();
        switch (Integer.valueOf(logic.getLogicType())) {
            case LOGIC_TYPE.SINGLE_JUMP:
                String skipToQuestionId = logic.getSkipTo();
                int i = -1;
                for (Question q : mTemplateQuestions) {
                    if (q.getId().equals(skipToQuestionId)) {
                        i = mTemplateQuestions.indexOf(q);
                    }
                }
                if (mCurrentPosition < i) {
                    saveQuestion(currentQuestion);
                    mCurrentPosition = i;
                    showQuestion(mTemplateQuestions.get(mCurrentPosition));
                }
                break;
            case LOGIC_TYPE.EXIT_SURVEY:
                Toast.makeText(this, "you are not fit the condition of the survey!", Toast.LENGTH_SHORT).show();
                deleteSurvey();
                finish();
                break;
            case LOGIC_TYPE.FINISH_SURVEY:
                saveQuestion(currentQuestion);
                prepareUpload();
                break;
        }
    }

    private Logic getJumpLogic(List<Logic> logics) {
        Logic logic = null;
        for (Logic lgc : logics) {
            for (int i = 0; i <= mCurrentPosition; i++) {
                Question question = mTemplateQuestions.get(i);
                for (Option option : question.getOptions()) {
                    if (option.isChecked() && option.getId().equals(lgc.getSelectAnswer())) {
                        logic = lgc;
                        break;
                    }
                }
            }
        }
        return logic;
    }

    /**
     * 获取当前已经被回答的选项列表
     *
     * @param question
     * @return
     */
    private List<Option> getQuestionSelectedOptions(Question question) {
        List<Option> checkedOptions = new ArrayList<Option>();
        for (Option option : question.getOptions()) {
            if (option.isChecked()) {
                checkedOptions.add(option);
            }
        }
        return checkedOptions;
    }

    private void prepareUpload(){
        showUpLoadDialog();
    }

    private void changeControlButtons(){
        if (mCurrentPosition == 0){
            btnNext.setVisibility(View.VISIBLE);
            btnUp.setVisibility(View.GONE);
        }else if(mCurrentPosition == mTemplateQuestions.size()-1){
            btnUp.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("提交");
        }else {
            btnUp.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("下一题");
        }
    }

    private void showUpLoadDialog() {
        Toast.makeText(this, "it's time to upload!", Toast.LENGTH_SHORT).show();
    }

    /**
     * db save question
     *
     * @param currentQuestion
     */
    private void saveQuestion(Question currentQuestion) {
        mAnsweredQuestions.add(currentQuestion);
    }

    /**
     * db delete survey
     */
    private void deleteSurvey() {

    }

    /**
     * 计算最后一道已答题在模板题库中的位置
     *
     * @return
     */
    private int calculatePosition() {
        int answeredNum = mAnsweredQuestions.size();
        if (answeredNum <= 0) {
            return 0;
        }
        Question lastAQuestion = mAnsweredQuestions.get(mAnsweredQuestions.size() - 1);
        int totalNum = mTemplateQuestions.size();
        for (int i = 0; i < totalNum; i++) {
            if (lastAQuestion.getId().equals(mTemplateQuestions.get(i).getId())) {
                return i;
            }
        }
        throw new IllegalStateException("Questions doesn't contains those answered questions");
    }


    private void buildQuestionWithAnswer(Question question) {
        Question answeredQuestion = null;
        for (Question a : mAnsweredQuestions) {
            if (question.getId().equals(a.getId())) {
                answeredQuestion = a;
                break;
            }
        }
        if (answeredQuestion == null) {
            return;
        }
        List<Option> aOptions = answeredQuestion.getOptions();
        List<Option> options = question.getOptions();
        if (aOptions == null && aOptions.isEmpty()) {
            return;
        }
        if (Integer.valueOf(question.getCategory()) == TYPE.SORT) {
            for (Option ao : aOptions) {
                ao.setChecked(true);
                Iterator<Option> iterator = options.iterator();
                while (iterator.hasNext()) {
                    Option o = iterator.next();
                    if (o.getId().equals(ao.getId())) {
                        iterator.remove();
                        break;
                    }
                }
            }
            options.addAll(0, aOptions);
        } else {
            for (Option ao : aOptions) {
                for (Option o : options) {
                    if (ao.getId().equals(o.getId())) {
                        ao.setChecked(true);
                        o.setChecked(true);
                    }
                }
            }
        }
    }

    private void show() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(this);
            pDialog.setMessage("wait...");
        }
        pDialog.show();
    }

    private void dismiss() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }


}
