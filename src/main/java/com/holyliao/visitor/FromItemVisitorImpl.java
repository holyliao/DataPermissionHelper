package com.holyliao.visitor;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;

public class FromItemVisitorImpl implements FromItemVisitor {

    private SubSelect subSelect;

    @Override
    public void visit(Table tableName) {
        //关键点：解析到需要进行数据权限控制的表时进行拼装，可以从当前线程获取表数据
        if (tableName.getName().equals("project")) {
            SubSelect subSelect = new SubSelect();
            //TODO：封装工具类,不使用这么暴力的方式
            String subSql = "select * from project where id in (1021, 1022, 1023)";
            try {
                subSelect.setSelectBody(((Select) (CCJSqlParserUtil.parse(subSql))).getSelectBody());
            } catch (JSQLParserException e) {
                //TODO：使用日志框架
                System.out.println("数据权限sql解析异常");
            }
            subSelect.setAlias(tableName.getAlias() != null ? tableName.getAlias() : new Alias("DP" + System.currentTimeMillis()));
            this.subSelect = subSelect;
        }
    }

    public SubSelect getSubSelect() {
        return this.subSelect;
    }

    // FROM 子查询
    @Override
    public void visit(SubSelect subSelect) {
        // 如果是子查询的话返回到select接口实现类
        subSelect.getSelectBody().accept(new SelectVisitorImpl());
    }

    // FROM subjoin
    @Override
    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(new FromItemVisitorImpl());
        subjoin.getJoin().getRightItem().accept(new FromItemVisitorImpl());
    }

    // FROM 横向子查询
    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        lateralSubSelect.getSubSelect().getSelectBody()
                .accept(new SelectVisitorImpl());
    }

    // FROM value列表
    @Override
    public void visit(ValuesList valuesList) {
    }

    // FROM tableFunction
    @Override
    public void visit(TableFunction tableFunction) {
    }
}