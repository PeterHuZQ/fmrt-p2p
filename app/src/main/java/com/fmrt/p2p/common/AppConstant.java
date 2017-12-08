package com.fmrt.p2p.common;

/**
 * 常量类：存放常量
 */

public class AppConstant
{
    public static final String CONTRACT_NAME_INVEST="《资金出借服务协议》";

    //自定义FrameLayout抽取公共通用view界面类loadingPage
    public static final int PAGE_LOADING_STATE = 1;  //正在加载

    public static final int PAGE_ERROR_STATE = 2;    //网络不通，不能与服务器正在通讯，无法加载数据

    public static final int PAGE_EMPTY_STATE = 3;    //加载成功，但无数据

    //借款
    public static final String FMD_TEXT = "纯信用无抵押";

    public static final int LENGTH_BANKCARD = 15; //银行卡长度最小限制
}
