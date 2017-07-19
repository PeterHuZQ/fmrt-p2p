package com.fmrt.p2p.util;

/**
 * 常量类：存放常量,配置各个页面联网地址
 */

public class AppConstants
{
    //基本的连接
    //public static final String BASE_URL="http://172.41.6.77:8080/GoodNews";    //单位内网
    public static final String BASE_URL="http://192.168.31.9:8080/P2PInvest"; //单位wifi外网
    //public static final String BASE_URL="http://192.168.1.101:8080/P2PInvest";  //家里的网

    //首页页面的连接
    public static final String INDEX_URL=BASE_URL+"/index";

    //LoginActivity里的注册
    public static final String LOGINACTIVITY_REGISTER_URL=BASE_URL+"/user/register.action";

    //LoginActivity里的登录
    public static final String LOGINACTIVITY_LOGIN_URL=BASE_URL+"/user/login.action";




    //首页图片的基本路径
    public static final String BASE_URL_IMAGE=BASE_URL+"/img";

    //天行数据服务器的连接
    public static final String TIAN_URL="https://api.tianapi.com";
    public static final String TIAN_API_KEY = "008fb6d12e9bfe2a35d5a426b34aa7c5";



    //新闻中心的连接
    //public static final String NEWSCENTER_URL=BASE_URL+"/categories.json";


    //天气服务器连接
    public static final String TIANQI_URL="https://api.thinkpage.cn/";

    //新闻中心服务器连接(天行数据)
    public static final String NEWSCENTER_URL="https://api.tianapi.com/";



}
