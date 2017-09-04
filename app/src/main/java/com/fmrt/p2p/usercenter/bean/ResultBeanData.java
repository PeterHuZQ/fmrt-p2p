package com.fmrt.p2p.usercenter.bean;

/**
 * Created by Administrator on 2017-07-21.
 */

public class ResultBeanData
{

    private String status;

    // 响应消息
    private String msg;

    // 响应中的数据
    private Object data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
