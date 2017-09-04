package com.fmrt.p2p.usercenter.bean;

/**
 * Created by Administrator on 2017-09-04.
 */

public class TokenBeanData
{

    /**
     * status : 200
     * msg : OK
     * data : e9a6bba8797da8221ca749f586247de4
     * ok : true
     */

    private String status;
    private String msg;
    private String data;
    private boolean ok;

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public String getData()
    {
        return data;
    }

    public void setData(String data)
    {
        this.data = data;
    }

    public boolean isOk()
    {
        return ok;
    }

    public void setOk(boolean ok)
    {
        this.ok = ok;
    }
}
