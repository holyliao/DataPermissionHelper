package com.holyliao;

/**
 * @author： liaoqixing
 * @date： 18-5-7 下午4:16
 * @desciptions: 获取数据权限的接口
 */
public interface PermissionHandler {
    /**获得权限
     * @param tables 表
     * @return
     */
    DataPermission getPermission(String... tables);
}
