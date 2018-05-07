package com.holyliao;

/**
 * @author liaoqixing
 * @description 数据权限开启
 * @create 2018-05-02 下午7:13
 */
public class DPHelper {
    protected static final ThreadLocal<DataPermission> DATA_PERMISSION = new ThreadLocal<>();

    /** 给当前线程设置ThreadLocal变量，设置权限
     * @param permissionHandler 提供需要进行数据控制的表及对应有权限的id和字段
     * @param tables 需要进行数据控制的表
     */
    public static void start(PermissionHandler permissionHandler, String... tables) {
        //获取权限数据
        setLocalDataPermissions(permissionHandler.getPermission(tables));
    }

    public static DataPermission getLocalDataPermissions() {
        return DATA_PERMISSION.get();
    }

    public static void setLocalDataPermissions(DataPermission dataPermissions) {
        DATA_PERMISSION.set(dataPermissions);
    }

    /**
     * 需要手动清除当前线程的权限信息
     */
    public static void clearDataPermissions() {
        DATA_PERMISSION.remove();
    }
}
