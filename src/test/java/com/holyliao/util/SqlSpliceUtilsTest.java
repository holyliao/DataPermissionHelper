package com.holyliao.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class SqlSpliceUtilsTest {
    @Test
    public void spliceIdAndColumn() throws Exception {
        List<String> ids = new ArrayList();
        ids.add("1");
        ids.add("2");
        ids.add("3");
        List<String> columns = new ArrayList<>();
        columns.add("id");
        columns.add("state");
        columns.add("create_time");
        String sql = SqlSpliceUtils.spliceIdAndColumn("project", ids, columns);
        System.out.println(sql);
    }

}