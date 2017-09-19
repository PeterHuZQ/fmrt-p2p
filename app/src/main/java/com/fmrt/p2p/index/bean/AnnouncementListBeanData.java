package com.fmrt.p2p.index.bean;

import java.util.List;

/**
 * 映射服务器返回的首页置顶公告列表
 */

public class AnnouncementListBeanData
{


    /**
     * status : 200
     * msg : OK
     * rows : [{"id":1,"title":"新活动上线啦"},{"id":2,"title":"关于系统停机升级的通知"}]
     */

    private String status;
    private String msg;
    private List<Announcement> rows;

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

    public List<Announcement> getRows()
    {
        return rows;
    }

    public void setRows(List<Announcement> rows)
    {
        this.rows = rows;
    }

    public static class Announcement
    {
        /**
         * id : 1
         * title : 新活动上线啦
         */

        private int id;
        private String title;

        public int getId()
        {
            return id;
        }

        public void setId(int id)
        {
            this.id = id;
        }

        public String getTitle()
        {
            return title;
        }

        public void setTitle(String title)
        {
            this.title = title;
        }
    }
}
