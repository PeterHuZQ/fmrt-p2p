package com.fmrt.p2p.usercenter.bean;

/**
 * Created by Administrator on 2017-09-08.
 */

public class UserAcctMoyBeanData
{


    /**
     * status : 200
     * msg : OK
     * data : {"uuid":"648a8e54-60c4-4871-a18f-f5769e85d529","userid":"65843ccc-d7dc-45d2-9c4a-37bdb0ab69bf","acctbal":100000,"invebal":33000,"imcmamt":36048,"freezeabal":1000,"tmpenchamt":1,"isdelete":"1","insert_time":1504685066000,"update_time":1504685066000,"total":134000}
     * ok : true
     */

    private String status;
    private String msg;
    private UserAcctMoy data;
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

    public UserAcctMoy getData()
    {
        return data;
    }

    public void setData(UserAcctMoy data)
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

    public static class UserAcctMoy
    {
        /**
         * uuid : 648a8e54-60c4-4871-a18f-f5769e85d529
         * userid : 65843ccc-d7dc-45d2-9c4a-37bdb0ab69bf
         * acctbal : 100000
         * invebal : 33000
         * imcmamt : 36048
         * freezeabal : 1000
         * tmpenchamt : 1
         * isdelete : 1
         * insert_time : 1504685066000
         * update_time : 1504685066000
         * total : 134000
         */

        private String uuid;
        private String userid;
        private int acctbal;
        private int invebal;
        private int imcmamt;
        private int freezeabal;
        private int tmpenchamt;
        private String isdelete;
        private long insert_time;
        private long update_time;
        private int total;

        public String getUuid()
        {
            return uuid;
        }

        public void setUuid(String uuid)
        {
            this.uuid = uuid;
        }

        public String getUserid()
        {
            return userid;
        }

        public void setUserid(String userid)
        {
            this.userid = userid;
        }

        public int getAcctbal()
        {
            return acctbal;
        }

        public void setAcctbal(int acctbal)
        {
            this.acctbal = acctbal;
        }

        public int getInvebal()
        {
            return invebal;
        }

        public void setInvebal(int invebal)
        {
            this.invebal = invebal;
        }

        public int getImcmamt()
        {
            return imcmamt;
        }

        public void setImcmamt(int imcmamt)
        {
            this.imcmamt = imcmamt;
        }

        public int getFreezeabal()
        {
            return freezeabal;
        }

        public void setFreezeabal(int freezeabal)
        {
            this.freezeabal = freezeabal;
        }

        public int getTmpenchamt()
        {
            return tmpenchamt;
        }

        public void setTmpenchamt(int tmpenchamt)
        {
            this.tmpenchamt = tmpenchamt;
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

        public int getTotal()
        {
            return total;
        }

        public void setTotal(int total)
        {
            this.total = total;
        }
    }
}
