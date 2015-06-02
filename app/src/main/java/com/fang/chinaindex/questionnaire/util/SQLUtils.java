package com.fang.chinaindex.questionnaire.util;

/**
 * Created by Aspsine on 2015/5/22.
 */
public class SQLUtils {
    public static final String createTable(String tableName, String params){
        StringBuilder sb = new StringBuilder();
        sb.append("create table ")
                .append(tableName)
                .append("(")
                .append("_id integer primary key autoincrement, ")
                .append(params)
                .append(")");
        return sb.toString();
    }

    public static final String createTableWithoutAutoIncrementId(String tableName, String params){
        StringBuilder sb = new StringBuilder();
        sb.append("create table ")
                .append(tableName)
                .append("(")
                .append(params)
                .append(")");
        return sb.toString();
    }

    public static final String dropTable(String tableName){
        return "drop table if exists " + tableName;
    }
}
