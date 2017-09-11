package com.fmrt.p2p.product.bean;

/**
 * Created by Administrator on 2017-09-06.
 */

public class CusContractBeanData
{

    /**
     * status : 200
     * msg : OK
     * data : {"uuid":"1e9a22ce-aefb-4d2b-bec8-65dfde094a45","loancontractid":"6651120170007414","displayid":"1708158573","borramt":80000,"residualamt":4000,"loanrate":7.98,"pubtime":"2017-08-25 17:03:44.0","contraeffectdate":null,"contrenddt":1534089600000,"basecontrstate":"1","contrreleasestate":"4","loanid":"9c469093-9585-4c32-b134-7bcc62c59e2e","loantype":"1","usetype":"购买农资","acttag":"0","investorrate":5.7,"mininvestment":1000,"insert_time":1503573654000,"update_time":1503573654000}
     * ok : true
     */

    private String status;
    private String msg;
    private CusContract data;
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

    public CusContract getData()
    {
        return data;
    }

    public void setData(CusContract data)
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
