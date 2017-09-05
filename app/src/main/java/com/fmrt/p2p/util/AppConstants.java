package com.fmrt.p2p.util;

/**
 * 常量类：存放常量,配置各个页面联网地址
 */

public class AppConstants
{
    //基本的连接
    //public static final String BASE_URL="http://172.41.6.77:8080/GoodNews";    //单位内网
    public static final String BASE_URL="http://192.168.201.111:9081"; //单位wifi外网
    //public static final String BASE_URL="http://192.168.1.104:8080/P2PInvest";  //家里的网
    public static final String PTP_LOAN_BASE_URL="http://192.168.201.111:9083"; //单位wifi外网


    //首页页面的连接
    public static final String INDEX_URL=BASE_URL+"/index";

    //投资频道列表的连接
    public static final String INVEST_URL=BASE_URL+"/product";

    //LoginActivity里的登录
    public static final String LOGINACTIVITY_LOGIN_URL=BASE_URL+"/user/doLogin";

    //RegisterActivity里的注册
    public static final String REGISTERACTIVITY_LOGIN_URL=BASE_URL+"/user/doRegister";

    //推荐频道的连接
    public static final String RECOMMENDLIST_URL=PTP_LOAN_BASE_URL+"/contract/list";
}
