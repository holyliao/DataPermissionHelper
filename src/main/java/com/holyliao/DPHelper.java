package com.holyliao;

/**
 * @author liaoqixing
 * @description 数据权限开启
 * @create 2018-05-02 下午7:13
 */
public class DPHelper {
    protected static final ThreadLocal<DataPermission> DATA_PERMISSION = new ThreadLocal<DataPermission>();

    public void start(String userId, String... tables) {

    }
}
