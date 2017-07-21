package com.fmrt.p2p.util;

/**
 * 常量类：存放常量,配置各个页面联网地址
 */

public class AppConstants
{
    //基本的连接
    //public static final String BASE_URL="http://172.41.6.77:8080/GoodNews";    //单位内网
    public static final String BASE_URL="http://192.168.31.9:8080/P2PInvest"; //单位wifi外网
    //public static final String BASE_URL="http://192.168.1.100:8080/P2PInvest";  //家里的网

    //首页页面的连接
    public static final String INDEX_URL=BASE_URL+"/index";

    //投资频道列表的连接
    public static final String INVEST_URL=BASE_URL+"/product";

    //LoginActivity里的登录
    public static final String LOGINACTIVITY_LOGIN_URL=BASE_URL+"/login";
}
