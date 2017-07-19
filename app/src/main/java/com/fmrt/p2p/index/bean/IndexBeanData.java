package com.fmrt.p2p.index.bean;

import java.util.List;

/**
 * 映射服务器返回的首页页面的结果
 */

public class IndexBeanData
{

    /**
     * code : 200
     * msg : 请求成功
     * result : {"banner_info":[{"image":"/invest01.jpg","option":1,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/invest02.jpg","option":2,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/invest03.jpg","option":3,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/invest04.jpg","option":4,"type":0,"value":{"url":"/act20161111?cyc_app=1"}}]}
     */

    private int code;
    private String msg;
    private ResultBean result;

    public int getCode()
    {
        return code;
    }

    public void setCode(int code)
    {
        this.code = code;
    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public ResultBean getResult()
    {
        return result;
    }

    public void setResult(ResultBean result)
    {
        this.result = result;
    }

    public static class ResultBean
    {
        private List<BannerInfoBean> banner_info;

        public List<BannerInfoBean> getBanner_info()
        {
            return banner_info;
        }

        public void setBanner_info(List<BannerInfoBean> banner_info)
        {
            this.banner_info = banner_info;
        }

        public static class BannerInfoBean
        {
            /**
             * image : /invest01.jpg
             * option : 1
             * type : 0
             * value : {"url":"/act20161111?cyc_app=1"}
             */

            public String image;
            private int option;
            private int type;
            private ValueBean value;

            public String getImage()
            {
                return image;
            }

            public void setImage(String image)
            {
                this.image = image;
            }

            public int getOption()
            {
                return option;
            }

            public void setOption(int option)
            {
                this.option = option;
            }

            public int getType()
            {
                return type;
            }

            public void setType(int type)
            {
                this.type = type;
            }

            public ValueBean getValue()
            {
                return value;
            }

            public void setValue(ValueBean value)
            {
                this.value = value;
            }

            public static class ValueBean
            {
                /**
                 * url : /act20161111?cyc_app=1
                 */

                private String url;

                public String getUrl()
                {
                    return url;
                }

                public void setUrl(String url)
                {
                    this.url = url;
                }
            }
        }
    }
}
