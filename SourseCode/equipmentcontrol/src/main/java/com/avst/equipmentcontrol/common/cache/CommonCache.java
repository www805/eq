package com.avst.equipmentcontrol.common.cache;


import com.avst.equipmentcontrol.common.cache.param.AdminManage_session;

/**
 * 一些常用的公共的缓存
 */
public class CommonCache {




    /**
     * 用户在session中的缓存
     */
    private static AdminManage_session adminManage_session;

    public static AdminManage_session getAdminManage_session(){

        return adminManage_session;
    }

    public static void setAdminManage_session(AdminManage_session admin){

        adminManage_session=admin;

    }


}
