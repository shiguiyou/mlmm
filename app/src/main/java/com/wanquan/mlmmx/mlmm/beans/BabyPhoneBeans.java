package com.wanquan.mlmmx.mlmm.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/11/2.
 */

public class BabyPhoneBeans implements Serializable {

    /**
     * data : [{"albumImglist":["http://api.env365.cn/album/2_1711070443426392.png","http://api.env365.cn/album/2_1711091038402761.png","http://api.env365.cn/album/2_1711091045278846.png"],"allPictureCount":3,"createTime":"2017-11-15","distanceDateReminder":"宝宝5天","id":1,"latitude":null,"longitude":null,"place":"","status":1,"title":"haohao","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-06","distanceDateReminder":"宝宝-3天","id":2,"latitude":33,"longitude":22,"place":"南京","status":1,"title":"大姑凉","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-06","distanceDateReminder":"宝宝-3天","id":3,"latitude":44,"longitude":33,"place":"南京","status":1,"title":"大姑凉222","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-04","distanceDateReminder":"宝宝-5天","id":4,"latitude":null,"longitude":null,"place":"","status":1,"title":"大姑凉22是是是2","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-04","distanceDateReminder":"宝宝-5天","id":5,"latitude":null,"longitude":null,"place":"","status":1,"title":"大姑凉22是是是2","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-04","distanceDateReminder":"宝宝-5天","id":6,"latitude":null,"longitude":null,"place":"","status":1,"title":"大姑凉22是是是2","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-04","distanceDateReminder":"宝宝-5天","id":7,"latitude":null,"longitude":null,"place":"","status":1,"title":"大姑凉22是是是2","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-04","distanceDateReminder":"宝宝-5天","id":8,"latitude":null,"longitude":null,"place":"","status":1,"title":"大姑凉22是是是2","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-06","distanceDateReminder":"宝宝-3天","id":11,"latitude":null,"longitude":null,"place":"","status":1,"title":"极客","userId":1},{"albumImglist":[],"allPictureCount":0,"createTime":"2017-11-06","distanceDateReminder":"宝宝-3天","id":12,"latitude":null,"longitude":null,"place":"","status":1,"title":"哈哈哈","userId":1}]
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
         * albumImglist : ["http://api.env365.cn/album/2_1711070443426392.png","http://api.env365.cn/album/2_1711091038402761.png","http://api.env365.cn/album/2_1711091045278846.png"]
         * allPictureCount : 3
         * createTime : 2017-11-15
         * distanceDateReminder : 宝宝5天
         * id : 1
         * latitude : null
         * longitude : null
         * place :
         * status : 1
         * title : haohao
         * userId : 1
         */

        private int allPictureCount;
        private String createTime;
        private String distanceDateReminder;
        private int id;
        private Object latitude;
        private Object longitude;
        private String place;
        private int status;
        private String title;
        private int userId;
        private List<String> albumImglist;

        public int getAllPictureCount() {
            return allPictureCount;
        }

        public void setAllPictureCount(int allPictureCount) {
            this.allPictureCount = allPictureCount;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDistanceDateReminder() {
            return distanceDateReminder;
        }

        public void setDistanceDateReminder(String distanceDateReminder) {
            this.distanceDateReminder = distanceDateReminder;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Object getLatitude() {
            return latitude;
        }

        public void setLatitude(Object latitude) {
            this.latitude = latitude;
        }

        public Object getLongitude() {
            return longitude;
        }

        public void setLongitude(Object longitude) {
            this.longitude = longitude;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public List<String> getAlbumImglist() {
            return albumImglist;
        }

        public void setAlbumImglist(List<String> albumImglist) {
            this.albumImglist = albumImglist;
        }
    }
}
