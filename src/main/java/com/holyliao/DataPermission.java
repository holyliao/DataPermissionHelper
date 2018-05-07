package com.holyliao;

import java.util.Map;

/**
 * @author liaoqixing
 * @description 数据权限bean
 * @create 2018-05-02 下午7:18
 */
public class DataPermission {
    /**
     * key为需要进行权限控制的表名，value表示用户可以查询的记录和对应的字段
     */
    private Map<String, IdsAndColumn> tables;

    /**
     * 是否是管理员，如果是管理员，不进行数据过滤(数据权限的白名单用户)
     */
    private Boolean isAdmin = false;

    public Map<String, IdsAndColumn> getTables() {
        return tables;
    }

    public void setTables(Map<String, IdsAndColumn> tables) {
        this.tables = tables;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }
}
