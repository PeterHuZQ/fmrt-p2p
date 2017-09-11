package com.fmrt.p2p.product.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-09-04.
 */

public class RecommendListBeanData
{

    /**
     * total : 2
     * rows : [{"uuid":"6b7af6e2-0d71-43c1-a7fa-94be036dd8b4","loancontractid":"6651120170007462","displayid":"1708162157","borramt":50000,"residualamt":18000,"loanrate":7.98,"pubtime":"2017-08-25 17:02:33.0","contraeffectdate":null,"contrenddt":1534176000000,"basecontrstate":"1","contrreleasestate":"4","loanid":"b7fbec54-fd79-4d03-9378-76368dea5bd9","loantype":"1","usetype":"买牛","acttag":"0","investorrate":5.7,"mininvestment":1000,"insert_time":1504493989000,"update_time":1504493989000},{"uuid":"9ba4f927-b3ee-49e5-8f22-5d4d86190d99","loancontractid":"6651120170007502","displayid":"1708157840","borramt":40000,"residualamt":1000,"loanrate":7.9,"pubtime":"2017-08-25 17:02:26.0","contraeffectdate":null,"contrenddt":1534262400000,"basecontrstate":"1","contrreleasestate":"4","loanid":"5af967a5-4163-42bb-955b-15aa8ce00954","loantype":"1","usetype":"养牛","acttag":"0","investorrate":5.7,"mininvestment":1000,"insert_time":1504234399000,"update_time":1504320807000}]
     * status : 200
     */

    private int total;
    private String status;
    private List<CusContract> rows;

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public List<CusContract> getRows()
    {
        return rows;
    }

    public void setRows(List<CusContract> rows)
    {
        this.rows = rows;
    }

}
