package com.holyliao;

import java.util.function.Function;

/**
 * @author liaoqixing
 * @description 数据权限开启
 * @create 2018-05-02 下午7:13
 */
public class DPHelper {
    protected static final ThreadLocal<DataPermission> DATA_PERMISSION = new ThreadLocal<DataPermission>();

    /**
     * @param tables 表示需要进行权限控制的表，为null表示预设的表全部进行权限控制
     */
    public static void start(Function<String[], DataPermission> permissionHandler, String... tables) {
        //获取权限数据
        setLocalDataPermissions(permissionHandler.apply(tables));
    }

    public static DataPermission getLocalDataPermissions() {
        return DATA_PERMISSION.get();
    }

    public static void setLocalDataPermissions(DataPermission dataPermissions) {
        DATA_PERMISSION.set(dataPermissions);
    }

    public static void clearDataPermissions() {
        DATA_PERMISSION.remove();
    }
}
