package com.avst.equipmentcontrol.web.req;

public class LoginParam {

    /**
     * 登陆账号
     */
    private String loginaccount;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户名称
     */
    private String username="测试用户";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginaccount() {
        return loginaccount;
    }

    public void setLoginaccount(String loginaccount) {
        this.loginaccount = loginaccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}