package com.fmrt.p2p.product.bean;

/**
 * Created by Administrator on 2017-09-07.
 */

public class CusPerInfo
{
    /**
     * uuid : 9c469093-9585-4c32-b134-7bcc62c59e2e
     * custname : 宋军
     * sex : 1
     * certno : E9CABA00EA82D8941DD4494D4791DF653D11C70E69023DC9FB500A36063F09CC
     * mobile : 0663331AAAA6E3791056FA6C883DDBE8
     * residstat : 自置
     * marriage : 已婚
     * isdelete : 1
     * insert_time : 1504691883000
     * update_time : 1504691883000
     */

    private String uuid;
    private String custname;
    private int sex;
    private String certno;
    private String mobile;
    private String residstat;
    private String marriage;
    private String isdelete;
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

    public String getCustname()
    {
        return custname;
    }

    public void setCustname(String custname)
    {
        this.custname = custname;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getCertno()
    {
        return certno;
    }

    public void setCertno(String certno)
    {
        this.certno = certno;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public String getResidstat()
    {
        return residstat;
    }

    public void setResidstat(String residstat)
    {
        this.residstat = residstat;
    }

    public String getMarriage()
    {
        return marriage;
    }

    public void setMarriage(String marriage)
    {
        this.marriage = marriage;
    }

    public String getIsdelete()
    {
        return isdelete;
    }

    public void setIsdelete(String isdelete)
    {
        this.isdelete = isdelete;
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
