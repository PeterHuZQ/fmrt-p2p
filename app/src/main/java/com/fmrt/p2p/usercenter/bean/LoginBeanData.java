package com.fmrt.p2p.usercenter.bean;

/**
 * Created by Administrator on 2017-07-21.
 */

public class LoginBeanData
{

    /**
     * data : {"UF_ACC":"zhouxingxing","UF_AVATAR_URL":"http://192.168.56.1:8080/P2PInvest/images/tx.jpg","UF_IS_CERT":"1","UF_PHONE":"18768146930"}
     * success : true
     */

    private UserBean data;
    private boolean success;

    public UserBean getData()
    {
        return data;
    }

    public void setData(UserBean data)
    {
        this.data = data;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public static class UserBean
    {
        /**
         * UF_ACC : zhouxingxing
         * UF_AVATAR_URL : http://192.168.56.1:8080/P2PInvest/images/tx.jpg
         * UF_IS_CERT : 1
         * UF_PHONE : 18768146930
         */

        private String UF_ACC;           //用户名
        private String UF_AVATAR_URL;    //用户头像
        private String UF_IS_CERT;       //是否实名认证
        private String UF_PHONE;         //用户手机号

        public String getUF_ACC()
        {
            return UF_ACC;
        }

        public void setUF_ACC(String UF_ACC)
        {
            this.UF_ACC = UF_ACC;
        }

        public String getUF_AVATAR_URL()
        {
            return UF_AVATAR_URL;
        }

        public void setUF_AVATAR_URL(String UF_AVATAR_URL)
        {
            this.UF_AVATAR_URL = UF_AVATAR_URL;
        }

        public String getUF_IS_CERT()
        {
            return UF_IS_CERT;
        }

        public void setUF_IS_CERT(String UF_IS_CERT)
        {
            this.UF_IS_CERT = UF_IS_CERT;
        }

        public String getUF_PHONE()
        {
            return UF_PHONE;
        }

        public void setUF_PHONE(String UF_PHONE)
        {
            this.UF_PHONE = UF_PHONE;
        }
    }
}
