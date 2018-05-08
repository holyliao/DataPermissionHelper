package com.holyliao.util;

import java.util.List;

/**
 * @author liaoqixing
 * @description sql拼接工具类
 * @create 2018-05-07 下午2:53
 */
public class SqlSpliceUtils {
    public static String spliceIdAndColumn(String tableName, List<String> ids, List<String> columns) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        for (String temp : columns) {
            stringBuilder.append(temp).append(", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append("FROM ").append(tableName).append(" WHERE id IN (");
        for (String temp : ids) {
            stringBuilder.append(temp).append(", ");
        }
        stringBuilder.delete(stringBuilder.lastIndexOf(", "), stringBuilder.length()).append(")");
        return stringBuilder.toString();
    }
}
