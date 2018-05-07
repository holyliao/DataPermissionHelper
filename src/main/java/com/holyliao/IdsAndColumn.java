package com.holyliao;

import java.util.List;

/**
 * @author： liaoqixing
 * @date： 18-5-7 下午2:31
 * @desciptions: 有权限的id和字段，类似与二元组
 */
public class IdsAndColumn {
    private List<String> ids;

    private List<String> columns;

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }
}
