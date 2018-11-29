package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/30.
 */

public class RelativesListViewBeans implements Serializable {

    /**
     * data : [{"address":null,"app_os":null,"app_os_version":null,"authority":1,"baby_head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","baby_height":123,"baby_id":613,"baby_nickname":"daniel","baby_sex":2,"baby_status":0,"baby_weight":47.8,"child_birth_date":1514736000000,"city":null,"create_time":1516326994000,"head_ico":"http://192.168.1.207/img/head/207_1802080343500106.png","id":207,"last_latitude":null,"last_login_ip":"223.112.11.102","last_login_time":1519801327000,"last_longitude":null,"mobile":"15261825221","nick_name":"daniel1","password":"e10adc3949ba59abbe56e057f20f883e","pre_child_date":1517414400000,"province":null,"recommend":"3WIM","referee":"17372205110","relationship":"爸爸","status":0,"username":"15261825221"},{"address":null,"app_os":null,"app_os_version":null,"authority":0,"baby_head_ico":null,"baby_height":null,"baby_id":613,"baby_nickname":null,"baby_sex":2,"baby_status":null,"baby_weight":null,"child_birth_date":1508986320000,"city":null,"create_time":1501145157000,"head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","id":2,"last_latitude":null,"last_login_ip":null,"last_login_time":1501205103000,"last_longitude":null,"mobile":"13770997524","nick_name":"哈哈","password":"fe1a10e4576c9db0b40e26b9ffa38ea5","pre_child_date":1508947200000,"province":null,"recommend":"05","referee":"04","relationship":"大姑","status":0,"username":"13770997524"},{"address":null,"app_os":null,"app_os_version":null,"authority":0,"baby_head_ico":null,"baby_height":null,"baby_id":613,"baby_nickname":null,"baby_sex":null,"baby_status":null,"baby_weight":null,"child_birth_date":null,"city":null,"create_time":1501203401000,"head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","id":5,"last_latitude":null,"last_login_ip":null,"last_login_time":null,"last_longitude":null,"mobile":"18951603156","nick_name":"空气","password":"96e79218965eb72c92a549dd5a330112","pre_child_date":null,"province":null,"recommend":"07","referee":"06","relationship":"爷爷","status":0,"username":"18951603156"},{"address":null,"app_os":null,"app_os_version":null,"authority":0,"baby_head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","baby_height":null,"baby_id":613,"baby_nickname":null,"baby_sex":2,"baby_status":2,"baby_weight":null,"child_birth_date":1512144000000,"city":null,"create_time":1501207617000,"head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","id":7,"last_latitude":null,"last_login_ip":null,"last_login_time":1515738639000,"last_longitude":null,"mobile":"15850530383","nick_name":null,"password":"eb4baa2ee8f612ea4c8a2ea6ba85b053","pre_child_date":null,"province":null,"recommend":"09","referee":"08","relationship":"阿姨","status":0,"username":"15850530383"},{"address":null,"app_os":null,"app_os_version":null,"authority":1,"baby_head_ico":null,"baby_height":null,"baby_id":613,"baby_nickname":null,"baby_sex":null,"baby_status":null,"baby_weight":null,"child_birth_date":null,"city":null,"create_time":1501224998000,"head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","id":8,"last_latitude":null,"last_login_ip":null,"last_login_time":null,"last_longitude":null,"mobile":"15850515668","nick_name":null,"password":"e10adc3949ba59abbe56e057f20f883e","pre_child_date":null,"province":null,"recommend":"10","referee":"09","relationship":"奶奶","status":0,"username":"15850515668"},{"address":null,"app_os":null,"app_os_version":null,"authority":1,"baby_head_ico":null,"baby_height":null,"baby_id":613,"baby_nickname":null,"baby_sex":null,"baby_status":null,"baby_weight":null,"child_birth_date":null,"city":null,"create_time":1501462140000,"head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","id":9,"last_latitude":null,"last_login_ip":null,"last_login_time":null,"last_longitude":null,"mobile":"15020675806","nick_name":null,"password":"6d26714e431a76619e09a661d157db68","pre_child_date":null,"province":null,"recommend":"11","referee":"10","relationship":"妈妈","status":0,"username":"15020675806"},{"address":null,"app_os":null,"app_os_version":null,"authority":0,"baby_head_ico":null,"baby_height":null,"baby_id":613,"baby_nickname":null,"baby_sex":null,"baby_status":null,"baby_weight":null,"child_birth_date":null,"city":null,"create_time":1501149566000,"head_ico":"http://api.env365.cn/img/head/imgdefault@2x.png","id":4,"last_latitude":null,"last_login_ip":null,"last_login_time":null,"last_longitude":null,"mobile":"15850515666","nick_name":null,"password":"e10adc3949ba59abbe56e057f20f883e","pre_child_date":1533052800000,"province":null,"recommend":"06","referee":"05","relationship":"其他","status":0,"username":"15850515666"}]
     * dynamicKey :
     * msg :
     * result : true
     * resultCode : 1
     * token :
     */

    private String dynamicKey;
    private String msg;
    private String result;
    private int resultCode;
    private String token;
    private List<DataBean> data;

    public String getDynamicKey() {
        return dynamicKey;
    }

    public void setDynamicKey(String dynamicKey) {
        this.dynamicKey = dynamicKey;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * address : null
         * app_os : null
         * app_os_version : null
         * authority : 1
         * baby_head_ico : http://api.env365.cn/img/head/imgdefault@2x.png
         * baby_height : 123
         * baby_id : 613
         * baby_nickname : daniel
         * baby_sex : 2
         * baby_status : 0
         * baby_weight : 47.8
         * child_birth_date : 1514736000000
         * city : null
         * create_time : 1516326994000
         * head_ico : http://192.168.1.207/img/head/207_1802080343500106.png
         * id : 207
         * last_latitude : null
         * last_login_ip : 223.112.11.102
         * last_login_time : 1519801327000
         * last_longitude : null
         * mobile : 15261825221
         * nick_name : daniel1
         * password : e10adc3949ba59abbe56e057f20f883e
         * pre_child_date : 1517414400000
         * province : null
         * recommend : 3WIM
         * referee : 17372205110
         * relationship : 爸爸
         * status : 0
         * username : 15261825221
         */

        private Object address;
        private Object app_os;
        private Object app_os_version;
        private int authority;
        private String baby_head_ico;
        private int baby_height;
        private int baby_id;
        private String baby_nickname;
        private int baby_sex;
        private int baby_status;
        private double baby_weight;
        private long child_birth_date;
        private Object city;
        private long create_time;
        private String head_ico;
        private int id;
        private Object last_latitude;
        private String last_login_ip;
        private long last_login_time;
        private Object last_longitude;
        private String mobile;
        private String nick_name;
        private String password;
        private long pre_child_date;
        private Object province;
        private String recommend;
        private String referee;
        private String relationship;
        private int status;
        private String username;

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public Object getApp_os() {
            return app_os;
        }

        public void setApp_os(Object app_os) {
            this.app_os = app_os;
        }

        public Object getApp_os_version() {
            return app_os_version;
        }

        public void setApp_os_version(Object app_os_version) {
            this.app_os_version = app_os_version;
        }

        public int getAuthority() {
            return authority;
        }

        public void setAuthority(int authority) {
            this.authority = authority;
        }

        public String getBaby_head_ico() {
            return baby_head_ico;
        }

        public void setBaby_head_ico(String baby_head_ico) {
            this.baby_head_ico = baby_head_ico;
        }

        public int getBaby_height() {
            return baby_height;
        }

        public void setBaby_height(int baby_height) {
            this.baby_height = baby_height;
        }

        public int getBaby_id() {
            return baby_id;
        }

        public void setBaby_id(int baby_id) {
            this.baby_id = baby_id;
        }

        public String getBaby_nickname() {
            return baby_nickname;
        }

        public void setBaby_nickname(String baby_nickname) {
            this.baby_nickname = baby_nickname;
        }

        public int getBaby_sex() {
            return baby_sex;
        }

        public void setBaby_sex(int baby_sex) {
            this.baby_sex = baby_sex;
        }

        public int getBaby_status() {
            return baby_status;
        }

        public void setBaby_status(int baby_status) {
            this.baby_status = baby_status;
        }

        public double getBaby_weight() {
            return baby_weight;
        }

        public void setBaby_weight(double baby_weight) {
            this.baby_weight = baby_weight;
        }

        public long getChild_birth_date() {
            return child_birth_date;
        }

        public void setChild_birth_date(long child_birth_date) {
            this.child_birth_date = child_birth_date;
        }

        public Object getCity() {
            return city;
        }

        public void setCity(Object city) {
            this.city = city;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getHead_ico() {
            return head_ico;
        }

        public void setHead_ico(String head_ico) {
            this.head_ico = head_ico;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLast_latitude() {
            return last_latitude;
        }

        public void setLast_latitude(Object last_latitude) {
            this.last_latitude = last_latitude;
        }

        public String getLast_login_ip() {
            return last_login_ip;
        }

        public void setLast_login_ip(String last_login_ip) {
            this.last_login_ip = last_login_ip;
        }

        public long getLast_login_time() {
            return last_login_time;
        }

        public void setLast_login_time(long last_login_time) {
            this.last_login_time = last_login_time;
        }

        public Object getLast_longitude() {
            return last_longitude;
        }

        public void setLast_longitude(Object last_longitude) {
            this.last_longitude = last_longitude;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public long getPre_child_date() {
            return pre_child_date;
        }

        public void setPre_child_date(long pre_child_date) {
            this.pre_child_date = pre_child_date;
        }

        public Object getProvince() {
            return province;
        }

        public void setProvince(Object province) {
            this.province = province;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getReferee() {
            return referee;
        }

        public void setReferee(String referee) {
            this.referee = referee;
        }

        public String getRelationship() {
            return relationship;
        }

        public void setRelationship(String relationship) {
            this.relationship = relationship;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
