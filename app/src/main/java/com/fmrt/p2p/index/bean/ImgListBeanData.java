package com.fmrt.p2p.index.bean;

import java.util.List;

/**
 * Created by Administrator on 2017-09-08.
 */

public class ImgListBeanData
{

    /**
     * status : 200
     * msg : OK
     * rows : [{"id":1,"imgurl":"http://192.168.201.111:9083/ptp_loan/images/invest01.jpg"},{"id":2,"imgurl":"http://192.168.201.111:9083/ptp_loan/images/invest02.jpg"},{"id":3,"imgurl":"http://192.168.201.111:9083/ptp_loan/images/invest03.jpg"},{"id":4,"imgurl":"http://192.168.201.111:9083/ptp_loan/images/invest04.jpg"}]
     */

    private String status;
    private String msg;
    private List<BannerInfo> rows;

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

    public List<BannerInfo> getRows()
    {
        return rows;
    }

    public void setRows(List<BannerInfo> rows)
    {
        this.rows = rows;
    }

    public static class BannerInfo
    {
        /**
         * id : 1
         * imgurl : http://192.168.201.111:9083/ptp_loan/images/invest01.jpg
         */

        public int id;
        public String imgurl;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getImgurl()
        {
            return imgurl;
        }

        public void setImgurl(String imgurl)
        {
            this.imgurl = imgurl;
        }
    }
}
