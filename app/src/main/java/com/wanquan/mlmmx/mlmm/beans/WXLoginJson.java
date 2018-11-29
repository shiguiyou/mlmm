package com.wanquan.mlmmx.mlmm.beans;

/**
 * Created by Administrator on 2017/3/27.
 */

public class WXLoginJson {

    /**
     * faceimg : sample string 1
     * nickname : sample string 2
     * openid : sample string 3
     * sex : 女
     */

    private String faceimg;
    private String nickname;
    private String openid;
    private String sex;
    private String littleimg;

    public String getFaceimg() {
        return faceimg;
    }

    public void setFaceimg(String faceimg) {
        this.faceimg = faceimg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLittleimg() {
        return littleimg;
    }

    public void setLittleimg(String littleimg) {
        this.littleimg = littleimg;
    }
}
