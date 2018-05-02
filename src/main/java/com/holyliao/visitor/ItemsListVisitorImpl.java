package com.holyliao.visitor;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsListVisitor;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.statement.select.SubSelect;

import java.util.List;

public class ItemsListVisitorImpl implements ItemsListVisitor {

    @Override
    public void visit(SubSelect ss) {
        ss.getSelectBody().accept(new SelectVisitorImpl());
    }

    @SuppressWarnings("unchecked")
    @Override
    public void visit(ExpressionList el) {
        List<Expression> list = el.getExpressions();
        if (list != null && list.size() > 0) {
            for (Expression expr : list) {
                expr.accept(new ExpressionVisitorImpl());
            }
        }
    }

    @Override
    public void visit(MultiExpressionList multiExprList) {

    }

}  