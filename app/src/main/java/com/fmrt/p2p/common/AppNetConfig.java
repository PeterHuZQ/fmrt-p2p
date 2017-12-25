package com.fmrt.p2p.common;

/**
 * 配置程序当中所有的接口请求地址
 */

public class AppNetConfig
{
    //基本的连接
    public static final String BASE_URL="http://192.168.1.104:8080/P2PInvest";

    //各服务的基本的连接
    //FMRT-SSO
    public static final String FMRT_SSO_BASE_URL="http://192.168.100.33:9081"; //单位wifi外网
    //public static final String FMRT_SSO_BASE_URL="http://192.168.1.103:9081"; //家里外网

    //PTP-LOAN
    public static final String PTP_LOAN_BASE_URL="http://192.168.100.33:9083"; //单位wifi外网
    //public static final String PTP_LOAN_BASE_URL="http://192.168.1.103:9083"; //家里外网

    //PTP-INVEST
    public static final String PTP_INVEST_BASE_URL="http://192.168.100.33:9084"; //单位wifi外网
    //public static final String PTP_INVEST_BASE_URL="http://192.168.1.103:9084"; //家里外网


    //PTP-USERMANAGE
    public static final String PTP_USERMANAGE_BASE_URL="http://192.168.100.33:9080"; //单位wifi外网
    //public static final String PTP_USERMANAGE_BASE_URL="http://192.168.1.103:9080"; //家里外网

    //LoginActivity里的登录
    public static final String LOGINACTIVITY_LOGIN_URL=BASE_URL+"/user/doLogin";


}
