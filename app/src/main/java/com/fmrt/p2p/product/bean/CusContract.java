package com.fmrt.p2p.product.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017-09-06.
 */

public class CusContract implements Serializable
{
    /**
     * uuid : 6b7af6e2-0d71-43c1-a7fa-94be036dd8b4
     * loancontractid : 6651120170007462
     * displayid : 1708162157
     * borramt : 50000
     * residualamt : 18000
     * loanrate : 7.98
     * pubtime : 2017-08-25 17:02:33.0
     * contraeffectdate : null
     * contrenddt : 1534176000000
     * basecontrstate : 1
     * contrreleasestate : 4
     * loanid : b7fbec54-fd79-4d03-9378-76368dea5bd9
     * loantype : 1
     * usetype : 买牛
     * acttag : 0
     * investorrate : 5.7
     * mininvestment : 1000
     * insert_time : 1504493989000
     * update_time : 1504493989000
     */

    private String uuid;
    private String loancontractid;
    private String displayid;
    private int borramt;
    private int residualamt;
    private double loanrate;
    private String pubtime;
    private Object contraeffectdate;
    private long contrenddt;
    private String basecontrstate;
    private String contrreleasestate;
    private String loanid;
    private String loantype;
    private String usetype;
    private String acttag;
    private double investorrate;
    private int mininvestment;
    private long insert_time;
    private long update_time;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid)
    {
        this.uuid = uuid;
    }

    public String getLoancontractid()
    {
        return loancontractid;
    }

    public void setLoancontractid(String loancontractid)
    {
        this.loancontractid = loancontractid;
    }

    public String getDisplayid()
    {
        return displayid;
    }

    public void setDisplayid(String displayid)
    {
        this.displayid = displayid;
    }

    public int getBorramt()
    {
        return borramt;
    }

    public void setBorramt(int borramt)
    {
        this.borramt = borramt;
    }

    public int getResidualamt()
    {
        return residualamt;
    }

    public void setResidualamt(int residualamt)
    {
        this.residualamt = residualamt;
    }

    public double getLoanrate()
    {
        return loanrate;
    }

    public void setLoanrate(double loanrate)
    {
        this.loanrate = loanrate;
    }

    public String getPubtime()
    {
        return pubtime;
    }

    public void setPubtime(String pubtime)
    {
        this.pubtime = pubtime;
    }

    public Object getContraeffectdate()
    {
        return contraeffectdate;
    }

    public void setContraeffectdate(Object contraeffectdate)
    {
        this.contraeffectdate = contraeffectdate;
    }

    public long getContrenddt()
    {
        return contrenddt;
    }

    public void setContrenddt(long contrenddt)
    {
        this.contrenddt = contrenddt;
    }

    public String getBasecontrstate()
    {
        return basecontrstate;
    }

    public void setBasecontrstate(String basecontrstate)
    {
        this.basecontrstate = basecontrstate;
    }

    public String getContrreleasestate()
    {
        return contrreleasestate;
    }

    public void setContrreleasestate(String contrreleasestate)
    {
        this.contrreleasestate = contrreleasestate;
    }

    public String getLoanid()
    {
        return loanid;
    }

    public void setLoanid(String loanid)
    {
        this.loanid = loanid;
    }

    public String getLoantype()
    {
        return loantype;
    }

    public void setLoantype(String loantype)
    {
        this.loantype = loantype;
    }

    public String getUsetype()
    {
        return usetype;
    }

    public void setUsetype(String usetype)
    {
        this.usetype = usetype;
    }

    public String getActtag()
    {
        return acttag;
    }

    public void setActtag(String acttag)
    {
        this.acttag = acttag;
    }

    public double getInvestorrate()
    {
        return investorrate;
    }

    public void setInvestorrate(double investorrate)
    {
        this.investorrate = investorrate;
    }

    public int getMininvestment()
    {
        return mininvestment;
    }

    public void setMininvestment(int mininvestment)
    {
        this.mininvestment = mininvestment;
    }

    public long getInsert_time()
    {
        return insert_time;
    }

    public void setInsert_time(long insert_time)
    {
        this.insert_time = insert_time;
    }

    public long getUpdate_time()
    {
        return update_time;
    }

    public void setUpdate_time(long update_time)
    {
        this.update_time = update_time;
    }
}
