package com.wanquan.mlmmx.mlmm.beans;

/**
 * Created by gu on 2017/06/08
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛
 * 　　　　┃　　　┃   Code is far away from bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p/>
 */
public class MessageEvent {
    private String checkId;
    private String flag;
    private boolean gone;
    private String deleteId;//删除相册、情感日志后刷新适配器
    private boolean isShow;//显示删除消息CheckBox
    private boolean isDelete;//是否删除消息
    private boolean isDeleteShow;//消息删除成功后隐藏（确认删除）按钮
    private boolean isShuaXin;//通知圈子页面刷新
    private boolean isPlay;//通知语音播放器是否播放修改后的日期的语音
    private int isFinish;//通知MainActivity跳转到相应的fragment
    private boolean isShowBank;//从积分攻略跳转过来，判断返回键是否显示
    private boolean isShebBei;//删除设备，修改设备名称刷新
    private boolean isDuiHuan;//使用优惠券后，通知界面刷新
    private boolean isTaiDong;//删除胎动后刷新
    private boolean isBabyShow;//显示折线图
    private String isBabyShowType;//折线图类型，pm0.3还是臭氧
    private String ReId;//首页RecyclerView的点击事件
    private boolean isMoreServiceShow;//工具管理界面通知适配器是否显示加减图标
    private String isVoiceTime;//情感日志语音录制的时间
    private String itFaceId;//通知圈子中动态和关注切换后加重数据
    private String QZid;//关注/取消圈子关注后刷新数据
    private boolean itFaceCollect;//收藏圈子
    private int isReceiverCode;//推送跳转

    public int getIsReceiverCode() {
        return isReceiverCode;
    }

    public void setIsReceiverCode(int isReceiverCode) {
        this.isReceiverCode = isReceiverCode;
    }

    public boolean getItFaceCollect() {
        return itFaceCollect;
    }

    public void setItFaceCollect(boolean itFaceCollect) {
        this.itFaceCollect = itFaceCollect;
    }

    public String getQZid() {
        return QZid;
    }

    public void setQZid(String QZid) {
        this.QZid = QZid;
    }

    public String getItFaceId() {
        return itFaceId;
    }

    public void setItFaceId(String itFaceId) {
        this.itFaceId = itFaceId;
    }

    public String getIsVoiceTime() {
        return isVoiceTime;
    }

    public void setIsVoiceTime(String isVoiceTime) {
        this.isVoiceTime = isVoiceTime;
    }

    public boolean getIsMoreServiceShow() {
        return isMoreServiceShow;
    }

    public void setIsMoreServiceShow(boolean isMoreServiceShow) {
        this.isMoreServiceShow = isMoreServiceShow;
    }

    public String getReId() {
        return ReId;
    }

    public void setReId(String reId) {
        ReId = reId;
    }

    public String getIsBabyShowType() {
        return isBabyShowType;
    }

    public void setIsBabyShowType(String isBabyShowType) {
        this.isBabyShowType = isBabyShowType;
    }

    public boolean isBabyShow() {
        return isBabyShow;
    }

    public void setBabyShow(boolean babyShow) {
        isBabyShow = babyShow;
    }

    public boolean isTaiDong() {
        return isTaiDong;
    }

    public void setTaiDong(boolean taiDong) {
        isTaiDong = taiDong;
    }

    public boolean isShebBei() {
        return isShebBei;
    }

    public void setShebBei(boolean shebBei) {
        isShebBei = shebBei;
    }

    public boolean isDuiHuan() {
        return isDuiHuan;
    }

    public void setDuiHuan(boolean duiHuan) {
        isDuiHuan = duiHuan;
    }

    public boolean isShowBank() {
        return isShowBank;
    }

    public void setShowBank(boolean showBank) {
        isShowBank = showBank;
    }

    public int isFinish() {
        return isFinish;
    }

    public void setFinish(int finish) {
        isFinish = finish;
    }

    public boolean isPlay() {
        return isPlay;
    }

    public void setPlay(boolean play) {
        isPlay = play;
    }

    public boolean isShuaXin() {
        return isShuaXin;
    }

    public void setShuaXin(boolean shuaXin) {
        isShuaXin = shuaXin;
    }

    public boolean isDeleteShow() {
        return isDeleteShow;
    }

    public void setDeleteShow(boolean deleteShow) {
        isDeleteShow = deleteShow;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public boolean isShow() {
        return isShow;
    }

    public void setShow(boolean show) {
        isShow = show;
    }

    public String getDeleteId() {
        return deleteId;
    }

    public void setDeleteId(String deleteId) {
        this.deleteId = deleteId;
    }

    public boolean isGone() {
        return gone;
    }

    public void setGone(boolean gone) {
        this.gone = gone;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String checkId) {
        this.checkId = checkId;
    }
}
