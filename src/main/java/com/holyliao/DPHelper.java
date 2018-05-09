package com.holyliao;

import java.util.Objects;

/**
 * @author liaoqixing
 * @description 数据权限开启
 * @create 2018-05-02 下午7:13
 */
public class DPHelper {
    protected static final ThreadLocal<DataPermission> DATA_PERMISSION = new ThreadLocal<>();

    protected static final ThreadLocal<Boolean> CHANGE_TABLE = new ThreadLocal<>();

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

    /**
     * 判断是否修改表结构
     *
     * @return
     */
    public static Boolean getChangeTable() {
        Boolean result = CHANGE_TABLE.get();
        if (Objects.isNull(result)) {
            result = false;
            setChangeTable(result);
        }
        return result;
    }

    /**是否修改表结构
     * @param isChange
     */
    public static void setChangeTable(Boolean isChange) {
        CHANGE_TABLE.set(isChange);
    }

    /**
     * 清除当前线程是否修改表结构信息
     */
    public static void clearChangeTable() {
        CHANGE_TABLE.remove();
    }
}
