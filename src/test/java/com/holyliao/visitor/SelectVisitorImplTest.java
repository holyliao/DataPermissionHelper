package com.holyliao.visitor;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.holyliao.DPHelper;
import com.holyliao.DataPermission;
import com.holyliao.IdsAndColumn;

/**
 * @author： liaoqixing
 * @date： 18-5-7 下午3:37
 * @desciptions: 测试sql解析
 */
public class SelectVisitorImplTest {
    @Before
    public void setDataPermissions() {
        String[] tables = new String[]{"project", "shop"};
        DPHelper.start(temp -> {
            DataPermission dataPermission = new DataPermission();
            IdsAndColumn idsAndColumn = new IdsAndColumn();
            idsAndColumn.setIds(new ArrayList<>(Arrays.asList("1", "3", "5")));
            idsAndColumn.setColumns(new ArrayList<>(Arrays.asList("id", "state", "create_time")));
            IdsAndColumn idsAndColumn1 = new IdsAndColumn();
            idsAndColumn1.setIds(new ArrayList<>(Arrays.asList("2", "4", "6")));
            idsAndColumn1.setColumns(new ArrayList<>(Arrays.asList("id1", "state1", "create_time1")));
            dataPermission.setTables(new HashMap<>());
            dataPermission.getTables().put(temp[0], idsAndColumn);
            dataPermission.getTables().put(temp[1], idsAndColumn1);
            return dataPermission;
        }, tables);
    }

    @Test
    public void testSelectVisitor() {
        String sql = "select * from project t1, shop t2 where t1.id = shop.project_id";
        Select select = null;
        try {
            select = (Select) CCJSqlParserUtil.parse(sql);
        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
        select.getSelectBody().accept(new SelectVisitorImpl());
        System.out.println(select.toString());
    }

}