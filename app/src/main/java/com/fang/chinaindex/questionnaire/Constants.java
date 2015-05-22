package com.fang.chinaindex.questionnaire;

/**
 * Created by Aspsine on 2015/4/28.
 */
public class Constants {

    public static final class CONFIG {
        public static final String ENCRYPT_KEY = "csis_app";
        public static final String ENCODING = "utf-8";
        /**
         * 发布正式app时设为false
         */
        public static final boolean DEBUG = true;
    }

    public static final class DB{
        public static final String DATA_BASE_NAME = "Question";
        public static final int DATA_BASE_VERSION = 1;
    }

    public static final String EXTRA_INFO = "Extra_Info";
    public static final String EXTRA_BEGIN_TIME = "Extra_BeginTime";
    public static final String SURVEY_STATUS = "Survey_Status";

    /**
     * url
     */
    public static final class URL {

        /**
         * 测试接口
         */
        private static final String TEST_URL = "http://csis.fdc.test.fang.com";

        /**
         * 正式地址
         */
        private static final String OFFICIAL_URL = "http://csis.fdc.fang.com";

        /**
         * 登陆
         */
        public static final String APP_CHECK_USER = BaseUrl() + "/SurveyInterface/App_CheckUser.ashx";

        /**
         * 问卷列表获取
         */
        public static final String SURVEYS = BaseUrl() + "/SurveyInterface/App_GetSurveys.ashx";

        /**
         * 问卷详情获取
         */
        public static final String SURVEYS_DETAIL = BaseUrl() + "/SurveyInterface/App_GetSurveysDetail.ashx";

        /**
         * 上传问卷结果
         */
        public static final String UPLOAD_SAMPLE = BaseUrl() + "/SurveyInterface/App_UploadSample.ashx";

        private static final String BaseUrl() {
            if (CONFIG.DEBUG) {
                return TEST_URL;
            }
            return OFFICIAL_URL;
        }
    }
}
