package com.fmrt.p2p.usercenter.bean;

/**
 * Created by Administrator on 2017-09-04.
 */

public class UserBeanData
{
    /**
     * status : 200
     * msg : OK
     * data : {"uuid":"d50c0d66-73ce-4726-afd1-126b82364fe6","username":"13100000000","password":null,"name":"张三","age":30,"sex":1,"birthday":"1984-08-08","phone":"13100000000","email":"1@qq.com","insert_time":1504176806000,"update_time":1504176806000}
     * ok : true
     */

    private String status;
    private String msg;
    private DataBean data;
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

    public DataBean getData()
    {
        return data;
    }

    public void setData(DataBean data)
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

    public static class DataBean
    {
        /**
         * uuid : d50c0d66-73ce-4726-afd1-126b82364fe6
         * username : 13100000000
         * password : null
         * name : 张三
         * age : 30
         * sex : 1
         * birthday : 1984-08-08
         * phone : 13100000000
         * email : 1@qq.com
         * insert_time : 1504176806000
         * update_time : 1504176806000
         */

        private String uuid;
        private String username;
        private Object password;
        private String name;
        private int age;
        private int sex;
        private String birthday;
        private String phone;
        private String email;
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

        public String getUsername()
        {
            return username;
        }

        public void setUsername(String username)
        {
            this.username = username;
        }

        public Object getPassword()
        {
            return password;
        }

        public void setPassword(Object password)
        {
            this.password = password;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getAge()
        {
            return age;
        }

        public void setAge(int age)
        {
            this.age = age;
        }

        public int getSex()
        {
            return sex;
        }

        public void setSex(int sex)
        {
            this.sex = sex;
        }

        public String getBirthday()
        {
            return birthday;
        }

        public void setBirthday(String birthday)
        {
            this.birthday = birthday;
        }

        public String getPhone()
        {
            return phone;
        }

        public void setPhone(String phone)
        {
            this.phone = phone;
        }

        public String getEmail()
        {
            return email;
        }

        public void setEmail(String email)
        {
            this.email = email;
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
}
