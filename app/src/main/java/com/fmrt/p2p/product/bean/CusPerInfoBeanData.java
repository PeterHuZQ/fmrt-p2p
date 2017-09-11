package com.fmrt.p2p.product.bean;

/**
 * Created by Administrator on 2017-09-07.
 */

public class CusPerInfoBeanData
{

    /**
     * status : 200
     * msg : OK
     * data : {"uuid":"9c469093-9585-4c32-b134-7bcc62c59e2e","custname":"宋军","sex":1,"certno":"E9CABA00EA82D8941DD4494D4791DF653D11C70E69023DC9FB500A36063F09CC","mobile":"0663331AAAA6E3791056FA6C883DDBE8","residstat":"自置","marriage":"已婚","isdelete":"1","insert_time":1504691883000,"update_time":1504691883000}
     * ok : true
     */

    private String status;
    private String msg;
    private CusPerInfo data;
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

    public CusPerInfo getData()
    {
        return data;
    }

    public void setData(CusPerInfo data)
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
