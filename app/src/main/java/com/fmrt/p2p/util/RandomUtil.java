package com.fmrt.p2p.util;

/**
 * Created by Administrator on 2017-08-30.
 */

public class RandomUtil
{
    public static String getSuijiStr() {
        String str20 = "";
        java.util.Random r = new java.util.Random();
        str20 = String.valueOf(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {
            str20 += r.nextInt(10);
            if (str20.length() >= 20) {
                break;
            }
        }
        return str20;
    }
}
