package com.fang.chinaindex.questionnaire.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
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
import com.fang.chinaindex.questionnaire.ui.fragment.MultiChoiceFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.OpenFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.QuestionBaseFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.SingleChoiceFragment;
import com.fang.chinaindex.questionnaire.ui.fragment.SortFragment;
import com.fang.chinaindex.questionnaire.util.DateUtils;
import com.fang.chinaindex.questionnaire.util.L;
import com.fang.chinaindex.questionnaire.util.SharedPrefUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 */
public class SurveyActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = SurveyActivity.class.getSimpleName();
    public static final String START_TIME_NONE = "start_time_none";

    private ProgressDialog pDialog;
    private Button btnUp, btnNext;

    private String mUserId;
    private String mSurveyId;
    /**
     * start time of the survey
     * <br/>
     * if (mStartTime.equals(START_TIME_NONE)){
     * 回答新问卷
     * }else{
     * 继续回答旧问卷
     * }
     */
    private String mStartTime;

    private SurveyInfo mSurveyInfo;
    private Survey mTemplateSurvey;
    private List<Question> mTemplateQuestions;
    private List<Integer> mAnsweredQuestionPositions;
    private int mCurrentPosition = 0;
    private int mLatestPosition = 0;

    public static final class TYPE {
        public static final int SINGLE = 1;   //单选题
        public static final int MULTIPLE = 2;   //多选题
        public static final int MARK = 3;   //打分题（单）
        public static final int TRUEORFALSE = 4;   //是非题（单）
        public static final int OPEN = 5;   //开放题
        public static final int TEXT = 6;   //文字描述题
        public static final int SORT = 7;   //排序题
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
        mStartTime = intent.getStringExtra("EXTRA_SURVEY_START_TIME");
        if (TextUtils.isEmpty(mStartTime)) {
            mStartTime = DateUtils.getCurrentDate();
        }

        mStartTime = "test time";

        mUserId = SharedPrefUtils.getUserId(this);

        btnNext = (Button) findViewById(R.id.btnNext);
        btnUp = (Button) findViewById(R.id.btnUp);

        btnNext.setOnClickListener(this);
        btnUp.setOnClickListener(this);

        initTemplateSurvey();
        initAnsweredSurvey();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNext:
                showNextQuestion();
                break;
            case R.id.btnUp:
                showUpQuestion();
                break;
        }
    }

    private void initTemplateSurvey() {
        mTemplateSurvey = App.getCacheRepository().getSurvey(mSurveyId);
        mSurveyInfo = mTemplateSurvey.getInfo();
        mTemplateQuestions = mTemplateSurvey.getQuestions();
        setTitle(mSurveyInfo.getTitle());
    }

    private void initAnsweredSurvey() {
        mAnsweredQuestionPositions = new ArrayList<Integer>();
        List<Question> answeredQuestions = App.getCacheRepository().getAnsweredQuestions(mUserId, mSurveyId, mStartTime);

        initQuestion(answeredQuestions);
    }

    /**
     * TODO 1: 从数据库中取出已答题目list，适配模板问卷list，将模板问卷恢复上一次答题的状态
     * TODO 2: 得到mCurrentPosition 并 利用适配好的模板问卷list 来生成 mAnsweredQuestionPositions
     * TODO 3: 适配模版问卷
     */
    private void initQuestion(List<Question> answeredQuestions) {
        if (answeredQuestions.isEmpty()) {
            //1. 如果不存在则使用未答题
            mCurrentPosition = 0;
        } else {
            //2.如果存在已答题，则展示最后一道已答题在模板题库中的下一道题
            mCurrentPosition = calculatePosition(answeredQuestions);
            buildTemplateWithAnsweredQuestions(answeredQuestions);
        }
        showQuestion(mTemplateQuestions.get(mCurrentPosition));
    }

    /**
     * container fragment of current question.
     */
    private QuestionBaseFragment mCurrentQuestionFragment = null;

    private void showQuestion(Question question) {
        changeControlButtons();
        switch (Integer.valueOf(question.getCategory())) {
            case TYPE.SINGLE:
                mCurrentQuestionFragment = new SingleChoiceFragment();
                break;
            case TYPE.MULTIPLE:
                mCurrentQuestionFragment = new MultiChoiceFragment();
                break;
            case TYPE.MARK:
                mCurrentQuestionFragment = new SingleChoiceFragment();
                break;
            case TYPE.TRUEORFALSE:
                mCurrentQuestionFragment = new SingleChoiceFragment();
                break;
            case TYPE.OPEN:
                mCurrentQuestionFragment = new OpenFragment();
                break;
            case TYPE.TEXT:
                Toast.makeText(this, "unsupported Question TYPE(6)", Toast.LENGTH_SHORT).show();
                return;
            case TYPE.SORT:
                mCurrentQuestionFragment = new SortFragment();
                break;
            default:
                Toast.makeText(this, "unsupported Question TYPE(unknown)", Toast.LENGTH_SHORT).show();
                return;
        }

        if (mCurrentQuestionFragment != null) {
            mCurrentQuestionFragment.setQuestion(question);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, mCurrentQuestionFragment)
                    .commit();
        }
    }

    /**
     * 上一题
     */
    private void showUpQuestion() {
        // there is no up question
        if (mCurrentPosition <= 0) {
            return;
        }
        //把最大的currentPosition保存起来。
        if (mCurrentPosition > mLatestPosition) {
            mLatestPosition = mCurrentPosition;
            L.i("SurveyActivity", "mLatestPosition = " + mLatestPosition);
            final Question latestQuestion = mTemplateQuestions.get(mLatestPosition);
            List<Option> selectedOptions = getQuestionSelectedOptions(latestQuestion);
            // true answered;
            // false not answered;
            boolean isCurrentQuestionBeenAnswered = selectedOptions.size() > 0;
            if (isCurrentQuestionBeenAnswered) {
                //回答了
                saveQuestion(latestQuestion, true, true);
            } else {
                saveQuestion(latestQuestion, true, false);
            }
        }

        L.i("SurveyActivity", "mCurrentPosition = " + mCurrentPosition);
        int currentPositionIndex = -1;
        for (int i = 0; i < mAnsweredQuestionPositions.size(); i++) {
            if (mCurrentPosition == mAnsweredQuestionPositions.get(i)) {
                currentPositionIndex = i;
                break;
            }
        }
        if (currentPositionIndex != -1) {
            int lastPositionIndex = --currentPositionIndex;
            mCurrentPosition = mAnsweredQuestionPositions.get(lastPositionIndex);
            showQuestion(mTemplateQuestions.get(mCurrentPosition));
        }

        //Log
        StringBuilder sb = new StringBuilder();
        for (Integer i : mAnsweredQuestionPositions) {
            sb.append(i + ", ");
        }
        L.i("mAnsweredQuestionPositions = " + sb.toString());
    }

    /**
     * 下一题/提交
     */
    public void showNextQuestion() {

        Question currentQuestion = mTemplateQuestions.get(mCurrentPosition);
        L.i("SurveyActivity", "mCurrentPosition = " + mCurrentPosition);
        List<Option> selectedOptions = getQuestionSelectedOptions(currentQuestion);

        //true answered, false not answered;
        boolean isCurrentQuestionBeenAnswered = selectedOptions.size() > 0;

        if (isCurrentQuestionBeenAnswered) {
            //回答了
            handlerAnsweredQuestion(currentQuestion);
        } else {
            //没回答
            handlerNotAnsweredQuestion(currentQuestion);
        }
    }

    /**
     * 处理已回答的问题
     *
     * @param currentQuestion
     */
    private void handlerAnsweredQuestion(final Question currentQuestion) {
        //TODO 保存问题
        saveQuestion(currentQuestion, true, true);

        final Logic logic = getJumpLogic(currentQuestion.getLogics());
        if (logic == null) {
            uploadSurveyOrGoToNextIndexQuestion();
        } else {
            if (mLatestPosition > mCurrentPosition) {

                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Alert!")
                        .setMessage("修改此题会影响整个问卷后续题目，确认修改？")
                        .setPositiveButton("确定修改", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 返回修改了一道逻辑跳转题
                                //清理这道逻辑跳转题后的所有题目
                                //1.清理 内存中的 mAnsweredQuestionPositions的 currentPosition 之后的数据
                                //2.清理 currentPosition之后的所有数据库中保存的AnsweredQuestion和对应的AnsweredOption
                                //3.mLatestPosition设为默认值
                                //4.doLogicJump(currentQuestion, logic);
                                int position = 0;
                                for (Integer i : mAnsweredQuestionPositions) {
                                    if (i == mCurrentPosition) {
                                        position = i;
                                        break;
                                    }
                                }

                                List<String> questionIds = new ArrayList<String>();
                                for (int i = mCurrentPosition + 1; i < mAnsweredQuestionPositions.size(); i++) {
                                    questionIds.add(mTemplateQuestions.get(mAnsweredQuestionPositions.get(i)).getId());
                                }

                                mAnsweredQuestionPositions = mAnsweredQuestionPositions
                                        .subList(0, position);

                                App.getCacheRepository().deleteAnsweredQuestions(mUserId, mSurveyId, mStartTime, questionIds);

                                doLogicJump(currentQuestion, logic);
                            }
                        }).setNegativeButton("取消修改", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                doLogicJump(currentQuestion, logic);
                            }
                        }).create();
                dialog.show();
            } else {
                doLogicJump(currentQuestion, logic);
            }
        }
    }

    /**
     * 处理未回答的问题
     *
     * @param currentQuestion
     */
    private void handlerNotAnsweredQuestion(Question currentQuestion) {

        if (!Boolean.valueOf(currentQuestion.getIsMust())) {
            //必答题
            Toast.makeText(this, "此题是必答题.", Toast.LENGTH_SHORT).show();
        } else {
            //非必答
            //保存问题
            saveQuestion(currentQuestion, true, true);
            uploadSurveyOrGoToNextIndexQuestion();
        }
    }

    /**
     * 上传问卷或进入下一题
     */
    private void uploadSurveyOrGoToNextIndexQuestion() {
        if (isLastQuestionInTemplate()) {
            prepareUpload();
        } else if (hasNextTemplateQuestion()) {
            goToNextIndexQuestion();
        } else {
            throw new IllegalStateException("current position is bigger than size of the template questions!");
        }
    }

    /**
     * 模版问卷中有没有下一题
     *
     * @return
     */
    private boolean hasNextTemplateQuestion() {
        return mCurrentPosition < mTemplateQuestions.size();
    }

    /**
     * 是否是模版问卷中的最后一题
     *
     * @return
     */
    private boolean isLastQuestionInTemplate() {
        return mCurrentPosition == mTemplateQuestions.size() - 1;
    }

    private boolean isFirstQuestionInTemplate() {
        return mCurrentPosition == 0;
    }

    /**
     * 非逻辑跳转，进入下一题
     */
    private void goToNextIndexQuestion() {
        showQuestion(mTemplateQuestions.get(++mCurrentPosition));
    }

    /**
     * 是逻辑跳转，处理逻辑跳转结果
     *
     * @param currentQuestion
     * @param logic
     */
    private void doLogicJump(Question currentQuestion, Logic logic) {
        L.i(TAG, "position = " + mCurrentPosition + " title = " + currentQuestion.getQuestionTitle() + " do logic");
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
                prepareUpload();
                break;
        }
    }

    private Logic getJumpLogic(List<Logic> logics) {
        Logic logic = null;
        List<Question> answeredQuestions = mTemplateQuestions.subList(0, mCurrentPosition + 1);
        for (Logic lgc : logics) {
            for (Question aQuestion : answeredQuestions) {
                if (lgc.getLogicQuestionId().equals(aQuestion.getId())) {
                    for (Option option : aQuestion.getOptions()) {
                        if (option.isChecked() && option.getId().equals(lgc.getSelectAnswer())) {
                            logic = lgc;
                            break;
                        }
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

    private void prepareUpload() {
        showUpLoadDialog();
    }

    private void changeControlButtons() {
        if (isFirstQuestionInTemplate()) {
            btnNext.setVisibility(View.VISIBLE);
            btnUp.setVisibility(View.GONE);
        } else if (isLastQuestionInTemplate()) {
            btnUp.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("提交");
        } else {
            btnUp.setVisibility(View.VISIBLE);
            btnNext.setVisibility(View.VISIBLE);
            btnNext.setText("下一题");
        }
    }

    private void showUpLoadDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Great! You have finished all the questions!")
                .setMessage("Would you like to upload the survey?")
                .setPositiveButton("upload", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SurveyActivity.this, "upload click", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(SurveyActivity.this, "cancel click", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    /**
     * db save question
     *
     * @param currentQuestion
     */
    private void saveQuestion(Question currentQuestion, boolean savePosition, boolean saveToDB) {
        //将当前题目的位置保存在内存list中
        if (savePosition) {
            if (!mAnsweredQuestionPositions.contains(mCurrentPosition)) {
                mAnsweredQuestionPositions.add(mCurrentPosition);
                Collections.sort(mAnsweredQuestionPositions);
            }
        }

        //TODO save to db
        if (saveToDB) {
            App.getCacheRepository().saveAnsweredQuestion(mUserId, mSurveyId, mStartTime, currentQuestion);

        }
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
    private int calculatePosition(List<Question> answeredQuestions) {
        Question lastAQuestion = answeredQuestions.get(answeredQuestions.size() - 1);
        int totalNum = mTemplateQuestions.size();
        for (int i = 0; i < totalNum; i++) {
            if (lastAQuestion.getId().equals(mTemplateQuestions.get(i).getId())) {
                return i;
            }
        }
        throw new IllegalStateException("Questions doesn't contains those answered questions");
    }

    private void buildTemplateWithAnsweredQuestions(List<Question> answeredQuestions) {
        for (Question templateQuestion : mTemplateQuestions) {
            //TODO TYPE.SORT
            for (Question answeredQuestion : answeredQuestions) {
                if (answeredQuestion.getId().equals(templateQuestion.getId())) {
                    for (Option templateOption : templateQuestion.getOptions()) {
                        if (answeredQuestion.getOptions() == null) {
                            break;
                        }
                        for (Option answeredOption : answeredQuestion.getOptions()) {
                            if (answeredOption.getId().equals(templateOption.getId())) {
                                templateOption.setChecked(true);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }


//    private void buildQuestionWithAnswer(Question question) {
//        Question answeredQuestion = null;
//        for (Question a : mAnsweredQuestions) {
//            if (question.getId().equals(a.getId())) {
//                answeredQuestion = a;
//                break;
//            }
//        }
//        if (answeredQuestion == null) {
//            return;
//        }
//        List<Option> aOptions = answeredQuestion.getOptions();
//        List<Option> options = question.getOptions();
//        if (aOptions == null && aOptions.isEmpty()) {
//            return;
//        }
//        if (Integer.valueOf(question.getCategory()) == TYPE.SORT) {
//            for (Option ao : aOptions) {
//                ao.setChecked(true);
//                Iterator<Option> iterator = options.iterator();
//                while (iterator.hasNext()) {
//                    Option o = iterator.next();
//                    if (o.getId().equals(ao.getId())) {
//                        iterator.remove();
//                        break;
//                    }
//                }
//            }
//            options.addAll(0, aOptions);
//        } else {
//            for (Option ao : aOptions) {
//                for (Option o : options) {
//                    if (ao.getId().equals(o.getId())) {
//                        ao.setChecked(true);
//                        o.setChecked(true);
//                    }
//                }
//            }
//        }
//    }

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
